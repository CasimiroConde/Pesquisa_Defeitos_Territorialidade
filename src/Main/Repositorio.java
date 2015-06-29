package Main;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.PageIterator;
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
	
	public Repositorio(String usuario, String repositorio){
		this.userName = usuario;
		this.repositoryName = repositorio;
		this.repoId = new RepositoryId(usuario, repositorio);
	}
	
	public void calculaQuantidadesIssues(IssueService issueService){
		Map<String, String> params = new HashMap<String, String>();
	    params.put(IssueService.FILTER_STATE, "all");
	    PageIterator<Issue> iterator = issueService.pageIssues(this.getRepoId(), params, 10);
			    
	    //Conta Issues
	    //Conta Issues com Label de Bug
	    while(iterator.hasNext()){
	    	Collection<Issue> page = iterator.next();
	    	java.util.Iterator<Issue> itr = page.iterator(); 
	    	while(itr.hasNext()){
	    		Issue issue = itr.next();
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
	
	public void defeitosCorrigidosCommit(CommitService commitService, IssueService issueService) throws IOException{
		for(RepositoryCommit c : commitService.getCommits(this.getRepoId())){
			if(MetodosAuxiliares.contemPalavraChave(c.getCommit().getMessage())){
				String[] palavras = c.getCommit().getMessage().split(" ");
				for(int i = 0 ; i < palavras.length ; i++){
					if(MetodosAuxiliares.ePalavraChave(palavras[i])){
						if((i + 1) < palavras.length ){
							if(palavras[i + 1].startsWith("#")){
								this.contadorIssuesCorrigidosCommits++;
								String numeroIssue = palavras[i + 1].substring(1).replaceAll("[^0-9]", "");	
								Issue issue = issueService.getIssue(this.repoId, numeroIssue);
								if(issue != null){
									if(MetodosAuxiliares.eBug(issue.getLabels())){
										this.contadorIssuesBugCorrigidosCommits++;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void calculaIssuesFechadosCommit(){
		if(this.getContadorIssuesCorrigidosCommits() > 0){
			this.porcentualIssuesFechadosCommit = (double) (this.getContadorIssuesCorrigidosCommits() * 100) / this.getClosedIssue();
			this.porcentualIssuesBugFechadosCommit = (double) (this.getContadorIssuesBugCorrigidosCommits() * 100) / this.getClosedIssueBug();
			this.porcentualIssuesBugFechadosCommitTotal = (double) (this.getContadorIssuesBugCorrigidosCommits() * 100) / this.getClosedIssue();
			
		}
	}
}
