package issuesRepositorios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;

import lombok.Data;

public @Data class Repositorio {
	
	private String userName;
	private String repositoryName;
	private IRepositoryIdProvider repoId;
	private int openIssue = 0;
	private int closedIssue = 0;
	private int openIssueBug = 0;
	private int closedIssueBug = 0;
	private int contadorIssuesCorrigidosCommits = 0;
	private int contadorIssuesBugCorrigidosCommits = 0;
	private double porcentualIssuesFechadosCommit = 0.0;
	private double porcentualIssuesBugFechadosCommit = 0.0;
	private double porcentualIssuesBugFechadosCommitTotal = 0.0;
	private ArrayList<MarcacaoIssue> marcacaoIssue;
	
	public Repositorio(String usuario, String repositorio){
		this.userName = usuario;
		this.repositoryName = repositorio;
		this.repoId = new RepositoryId(usuario, repositorio);
		this.marcacaoIssue = new ArrayList<MarcacaoIssue>();
	}
	
	public void calculaQuantidadesIssues(IssueService issueService) throws InterruptedException, RequestException{
		boolean encerrado = false;
		
		while(!encerrado){
			try{
				Map<String, String> params = new HashMap<String, String>();
			    params.put(IssueService.FILTER_STATE, "all");
			    PageIterator<Issue> iterator = issueService.pageIssues(this.getRepoId(), params, 100);
				
			    int openIssue1 = 0;
			    int openIssueBug1 = 0;
			    int closedIssue1 = 0;
			    int closedIssueBug1 = 0;
			    
			    
			    //Conta Issues
			    //Conta Issues com Label de Bug
			    while(iterator.hasNext()){
			    	Collection<Issue> page = iterator.next();
			    	java.util.Iterator<Issue> itr = page.iterator(); 
			    	while(itr.hasNext()){
			    		Issue issue = itr.next();
			    		this.insereMarcacoes(issue);
			    		List<Label> labels = issue.getLabels();
				    	if(issue.getState().equalsIgnoreCase("open")){
							openIssue1++;
							if(MetodosAuxiliares.eBug(labels)){
								openIssueBug1++;
							}
				    	}
						if(issue.getState().equalsIgnoreCase("closed")){
								closedIssue1++;
							if(MetodosAuxiliares.eBug(labels)){
									closedIssueBug1++;   
							}
						}
			    	}
				}
			    
			    this.openIssue = openIssue1;
			    this.openIssueBug = openIssueBug1;
			    this.closedIssue = closedIssue1;
			    this.closedIssueBug = closedIssueBug1;
			    encerrado = true;
			} catch (NoSuchPageException e ){
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Calcula Issues!! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;			
			}
		}
	}
	
	public void defeitosCorrigidosCommit(CommitService commitService, IssueService issueService) throws IOException, InterruptedException{
		boolean encerrado = false;
		Issue issue = null;
		
		while(!encerrado){
			try{
				int contadorIssuesCorrigidosCommits1 = 0;	
				int contadorIssuesBugCorrigidosCommits1 = 0;
				for(RepositoryCommit c : commitService.getCommits(this.getRepoId())){
					if(MetodosAuxiliares.contemPalavraChave(c.getCommit().getMessage())){
						String[] palavras = c.getCommit().getMessage().split(" ");
						for(int i = 0 ; i < palavras.length ; i++){
							if(MetodosAuxiliares.ePalavraChave(palavras[i])){
								if((i + 1) < palavras.length ){
									if(palavras[i + 1].startsWith("#")){
										contadorIssuesCorrigidosCommits1++;
										String numeroIssue = palavras[i + 1].substring(1).replaceAll("[^0-9]", "");
										if(!numeroIssue.isEmpty()){
											try{
												issue = issueService.getIssue(this.repoId, numeroIssue);
											}catch (IOException e) {
												System.out.println("Não achou Issue: " + numeroIssue);
												issue = null;
											}
											if(issue != null){
												if(MetodosAuxiliares.eBug(issue.getLabels())){
													contadorIssuesBugCorrigidosCommits1++;
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				this.contadorIssuesCorrigidosCommits = contadorIssuesCorrigidosCommits1;
				this.contadorIssuesBugCorrigidosCommits = contadorIssuesBugCorrigidosCommits1;
				encerrado = true;
			} catch (NoSuchPageException n){
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Defeitos Corrigidos! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;		
			
			}catch(RequestException r){
				System.out.println("Defeitos Corrigidos! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
			}
		}
	}
	
	private void insereMarcacoes(Issue issue) {
		
		//Analisa Contador de Label
		if(!issue.getLabels().isEmpty())
		MetodosAuxiliares.insereMarcacaoLabel(this.marcacaoIssue,issue);	
		
		//Analisa Contador de Milestones
		if(issue.getMilestone() != null)
		MetodosAuxiliares.insereMarcacaoMilestone(this.marcacaoIssue, issue);
		
	}


	private boolean existeLabel(String name) {
		for(MarcacaoIssue m : marcacaoIssue){
			if(m.getTipo().equals(TipoMarcacao.LABEL)){
				if(m.getNome().equals(name))
					return true;
			}
		}
		return false;
	}

	public void calculaIssuesFechadosCommit(){
		if(this.getContadorIssuesCorrigidosCommits() > 0){
			this.porcentualIssuesFechadosCommit = (double) (this.getContadorIssuesCorrigidosCommits() * 100) / this.getClosedIssue();
			this.porcentualIssuesBugFechadosCommit = (double) (this.getContadorIssuesBugCorrigidosCommits() * 100) / this.getClosedIssueBug();
			this.porcentualIssuesBugFechadosCommitTotal = (double) (this.getContadorIssuesBugCorrigidosCommits() * 100) / this.getClosedIssue();
			
		}
	}
	
	public void addMarcacaoIssue(MarcacaoIssue marcacao){
		this.marcacaoIssue.add(marcacao);
	}
	
	
	
}
