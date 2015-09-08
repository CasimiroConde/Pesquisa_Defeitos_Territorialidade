package issuesRepositorios;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Language;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.TeamService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.egit.github.core.service.WatcherService;
import org.joda.time.DateTime;
import org.joda.time.Days;

import leituraEscrita.Reader;
import leituraEscrita.Writer;
import lombok.Data;
import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.MarcacaoIssue;
import marcacoesIssues.TipoMarcacao;
import marcacoesIssues.MetodosAuxiliaresLabel;

public @Data class Repositorio {
	
	private String userName;
	private int numeroFollowersOwner;
	private int numeroFollowingOwner;
	private String repositoryName;
	private int numeroForks;
	private int numeroWatchers;
	private int tamanhoRepositorio;
	private IRepositoryIdProvider repoId;
	private ArrayList<Issue> issues;
	private List<Contributor> contributors;
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
	
	/*public Repositorio(String userName, String repositoryName, IssueService issueService, RepositoryService repositoryService, GitHubClient client) throws InterruptedException, IOException{
		boolean finished = false;
		WatcherService watcherService = new WatcherService(client);
		while(!finished){		
			try {	
				this.userName = repositorio.getOwner().getLogin();
				this.repositoryName = repositorio.getName();
				this.repoId = new RepositoryId(this.userName, this.repositoryName);
				Repository repo = repositoryService.getRepository(repoId);
				this.numeroForks = repositoryService.getForks(this.repoId).size();repositorio.getForks();
				this.tamanhoRepositorio = repositorio.getSize();
				this.numeroFollowersOwner = repositorio.getOwner().getFollowers();
				this.numeroFollowingOwner = repositorio.getOwner().getFollowing();
				this.numeroWatchers = watcherService.getWatchers(this.repoId).size(); repositorio.getWatchers();
				this.marcacaoIssue = new ArrayList<MarcacaoIssue>();
				this.issues = new ArrayList<Issue>();
				this.contributors = repositoryService.getContributors(this.repoId, true);
				this.coletaIssues(issueService);
				finished = true;
			} catch (NoSuchPageException e) {
				System.out.println("Carregando Repositório!! Repositório: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);
			} catch (RequestException e) {
				System.out.println("Repositório não acessado!! Repositório: " + this.getRepositoryName());
				this.setRepositoryName("Vazio");
				finished = true;
			}
		}
	}*/
	
	public Repositorio(String userName, String repositoryName, GitHubClient client) throws InterruptedException, IOException{
		RepositoryService repositoryService = new RepositoryService(client);
		IssueService issueService = new IssueService(client);
		UserService userService = new UserService(client);
		
		boolean finished = false;
		WatcherService watcherService = new WatcherService(client);
		while(!finished){		
			try {	
				this.userName = userName;
				this.repositoryName = repositoryName;
				this.repoId = new RepositoryId(this.userName, this.repositoryName);
				Repository repositorio = repositoryService.getRepository(repoId);
				User usuario = userService.getUser(repositorio.getOwner().getLogin());
				this.numeroForks = /*repositoryService.getForks(this.repoId).size();*/repositorio.getForks();
				this.tamanhoRepositorio = repositorio.getSize();
				this.numeroFollowersOwner = usuario.getFollowers();
				this.numeroFollowingOwner = usuario.getFollowing();
				this.numeroWatchers = /*watcherService.getWatchers(this.repoId).size();*/ repositorio.getWatchers();
				this.marcacaoIssue = new ArrayList<MarcacaoIssue>();
				this.issues = new ArrayList<Issue>();
				this.contributors = repositoryService.getContributors(this.repoId, true);
				this.coletaIssues(issueService);
				finished = true;
			} catch (NoSuchPageException e) {
				System.out.println("Carregando Repositório!! Repositório: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);
			} catch (RequestException e) {
				if(e.getStatus() == 403){
					System.out.println("Carregando Repositório!! Repositório: " + this.getRepositoryName());
					Thread.sleep(600 * 1000);
				} else {
					this.repositoryName = "Vazio";
					System.out.println("Repositório não acessado!! Repositório: " + this.getRepositoryName());
					finished = true;
				}
			}
		}
	}
	
	
	public void coletaIssues(IssueService issueService) throws InterruptedException, RequestException{
		boolean encerrado = false;

		Map<String, String> params = new HashMap<String, String>();
		params.put(IssueService.FILTER_STATE, "all");
		PageIterator<Issue> iterator = issueService.pageIssues(this.getRepoId(), params, 100);

		while(!encerrado){
			try{
			    //Coleta Issue
			    while(iterator.hasNext()){
			    	Collection<Issue> page = iterator.next();
			    	java.util.Iterator<Issue> itr = page.iterator(); 
			    	while(itr.hasNext()){
			    		Issue issue = itr.next();
						this.issues.add(issue);
			    	}
		    	}		    
			    encerrado = true;
			} catch (NoSuchPageException e ){
				System.out.println("Coleta Issue!! Repositório: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);			
			}
		}
	}
	
	public int calculaTimeToFixIssue(Issue issue){
		int tempo;
		tempo = Days.daysBetween(new DateTime(issue.getCreatedAt()), new DateTime(issue.getClosedAt())).getDays();
		return tempo;
		
	}

	private void contaLinhasCodigo(CommitService commitService)
			throws InterruptedException {
		boolean completo = false;
		while(!completo){
			try {
				List<RepositoryCommit> commitsRepositorio = commitService.getCommits(this.repoId);
				int indiceUltimoCommit = commitsRepositorio.lastIndexOf(commitsRepositorio);

			} catch (IOException e) {
				System.out.println("Coletando Colaboradores! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
			}
		}
	}
	
	public void calculaQuantidadesIssues(ArrayList<LabelConsolidado> labelConsolidado) throws InterruptedException, RequestException{			    
		//Conta Issues
		//Conta Issues com Label de Bug
		if(!this.getIssues().isEmpty()){
			java.util.Iterator<Issue> itr = this.getIssues().iterator();	
			while(itr.hasNext()){
				Issue issue = itr.next();
				this.insereMarcacoes(issue,labelConsolidado);
				List<Label> labels = issue.getLabels();
				if(issue.getState().equalsIgnoreCase("open")){
					this.openIssue++;
					if(MetodosAuxiliares.eBug(labels)){
						this.openIssueBug++;
					}
				}
				if(issue.getState().equalsIgnoreCase("closed")){
						this.closedIssue++;
					if(MetodosAuxiliares.eBug(labels)){
							this.closedIssueBug++;   
					}
				}
			}
		}
	}
	
	public void defeitosCorrigidosCommitOrigemWeb(CommitService commitService, IssueService issueService) throws IOException, InterruptedException{
		boolean encerrado = false;
		
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
										String numeroIssue = palavras[i + 1].substring(1).replaceAll("[^0-9]", "");
										if(verificaIssueCommitExiste(numeroIssue)){
											Issue issue = this.getIssueNumero(numeroIssue);
											if(verificaIssueCommitEstaFechado(issue)){
												contadorIssuesCorrigidosCommits1++;
												if(verificaIssueCommitEBug(issue)){
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
	
	private boolean verificaIssueCommitEstaFechado(Issue issue) {
		if(!issue.equals(null)){
			if(issue.getState().equals("Closed"))
				return true;
		}
		return false;
	}

	public void defeitosCorrigidosCommitOrigemCSV() throws IOException, InterruptedException{
		boolean encerrado = false;
		
		String nomePasta = this.userName+"-"+this.repositoryName;
		
		String caminhoArmazenamento = "C://Users//Casimiro//Documents//Casimiro Conde//Aulas//Mestrado//Seminário de Acompanhamento Discente 2//Territorialidade//Commits//"+nomePasta;
		
	
		File pasta = new File (caminhoArmazenamento);
		File [] files = pasta.listFiles();
		
		while(!encerrado){
			try{
				int contadorIssuesCorrigidosCommits1 = 0;	
				int contadorIssuesBugCorrigidosCommits1 = 0;
				for(File f : files){
					if(f.isFile() && f.getName().endsWith(".txt")){
						String texto = Reader.retornaConteudo(f);
						if(MetodosAuxiliares.contemPalavraChave(texto)){
							String[] palavras = texto.split(" ");
							for(int i = 0 ; i < palavras.length ; i++){
								if(MetodosAuxiliares.ePalavraChave(palavras[i])){
									if((i + 1) < palavras.length ){
										if(palavras[i + 1].startsWith("#")){
											String numeroIssue = palavras[i + 1].substring(1).replaceAll("[^0-9]", "");
											if(verificaIssueCommitExiste(numeroIssue)){
												Issue issue = this.getIssueNumero(numeroIssue);
												if(verificaIssueCommitEstaFechado(issue)){
													contadorIssuesCorrigidosCommits1++;
													if(verificaIssueCommitEBug(issue)){
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
				}
				
				this.contadorIssuesCorrigidosCommits = contadorIssuesCorrigidosCommits1;
				this.contadorIssuesBugCorrigidosCommits = contadorIssuesBugCorrigidosCommits1;
				encerrado = true;
			} catch (NoSuchPageException n){
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Defeitos Corrigidos! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;		
			
			}
		}
	}
	
	private Issue getIssueNumero(String numeroIssue) {
		for(Issue i: this.getIssues()){
			if(i.getNumber() == Integer.parseInt(numeroIssue))
				return i;
		}
		return null;
	}

	private boolean verificaIssueCommitExiste(String numeroIssue) {
		try{
			if(!numeroIssue.isEmpty()){
					for(Issue i : this.getIssues()){
						if(i.getNumber() == Integer.parseInt(numeroIssue))
							return true;
					}
					System.out.println("Não achou Issue: " + numeroIssue);
					return false;
	
			}
			return false;
		} catch (NumberFormatException n){
			System.out.println("Erro ao buscar o número: " + numeroIssue);
			return false;
		}
		
	}

	private boolean verificaIssueCommitEBug(Issue issue) {
		if(!issue.equals(null)){
			if(MetodosAuxiliares.eBug(issue.getLabels()))
				return true;
		}
		return false;
	}
	
	public void downloadCommits(CommitService commitService) throws IOException, InterruptedException{
		boolean encerrado = false;

		while(!encerrado){
			try{
				if(!commitService.getCommits(this.repoId).isEmpty()){
					for(RepositoryCommit c : commitService.getCommits(this.getRepoId())){
						Writer.armazenaCommits(this.getUserName(), this.getRepositoryName(), c.getSha(), c.getCommit().getMessage());
					}	
				} else{
						Writer.armazenaCommits(this.getUserName(), this.getRepositoryName(), "0","Não Existe Commit");
				}
				encerrado = true;
			} catch (NoSuchPageException n){
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Download de Commit! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;		
			
			}catch(RequestException r){
				Repository repositorio = new RepositoryService().getRepository(repoId);
				if(repositorio.isPrivate()){
					System.out.println("Download de Commit!! Repositório: " + this.getRepositoryName());
					encerrado = true;
				}else{
					System.out.println("Downloado de Commit!! Repositório: " + this.getRepositoryName());
					Thread.sleep(600 * 1000);
				}
				
			}
		}
	}
	
	private void insereMarcacoes(Issue issue, ArrayList<LabelConsolidado> labelConsolidado) {
		
		//Analisa Contador de Label
		if(!issue.getLabels().isEmpty())
		MetodosAuxiliaresLabel.insereMarcacaoLabelRepositorio(this.marcacaoIssue,issue, labelConsolidado);	
		
		//Analisa Contador de Milestones
		if(issue.getMilestone() != null)
		MetodosAuxiliaresLabel.insereMarcacaoMilestoneRepositorio(this.marcacaoIssue, issue);
		
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
	
	public void getInformacaoColaboradores(RepositoryService repositoryService) throws InterruptedException{
		List<Contributor> colaboradores = new ArrayList<Contributor>();
		try {
			colaboradores = repositoryService.getContributors(repoId, true);
			for(Contributor c : colaboradores){
				System.out.println(c.getName());
			}
		} catch (IOException e) {
			System.out.println("Coletando Colaboradores! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
			Thread.sleep(600 * 1000);
		}
		
	}
	
	
	
}
