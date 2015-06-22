package Main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.SearchIssue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;


public class Main {
	
	private String[] PALAVRAS_CHAVE = {	"close" , 
										"closes" , 
										"closed" , 
										"fix" ,
										"fixes" ,
										"fixed" ,
										"resolve" ,
										"resolves" , 
										"resolved"};

	public static void main(String[] args) throws IOException {
		GitHubClient client = new GitHubClient();
		client.setCredentials("CasimiroConde", "m4m9s6vi");
		
		int openIssue = 0;
		int closedIssue = 0;
		int contadorDefeitosCorrigidosCommits = 0;
		
		RepositoryService repositoryService = new RepositoryService(client);
		IRepositoryIdProvider repoId = new RepositoryId("purifycss","purifycss");
	    
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		

		for(Collection<Issue> issue : issueService.pageIssues(repoId)){
			for(Issue i : issue){
				System.out.println(i.getTitle() + " " + i.getState() + " " + i.getNumber());
				
				if(i.getState().equalsIgnoreCase("open"))
					openIssue++;
				
				if(i.getState().equalsIgnoreCase("closed"))
					closedIssue++;
			}
		}
		
		/*for(SearchIssue search : issueService.searchIssues(repoId, "all", " ")){
			System.out.println(search.getTitle() + " " + search.getState() + " " + search.getNumber());
			
			if(search.getState().equalsIgnoreCase("open"))
				openIssue++;
			
			if(search.getState().equalsIgnoreCase("closed"))
				closedIssue++;
			
		}*/
		
		System.out.println(openIssue + " " + closedIssue);
		/*
		for(RepositoryCommit c : commitService.getCommits(repoId)){
			if(contemPalavraChave(c.getCommit().getMessage())){
				String[] palavras = c.getCommit().getMessage().split(" ");
				for(String palavra : palavras){
					if(contemPalavraChave(palavra)){
						contadorDefeitosCorrigidos++;
					}
				}

			}
		*/
		
}
	
	public static boolean contemPalavraChave(String mensagem){
		mensagem = mensagem.toLowerCase();
		
		if(mensagem.contains(" close ") || 
			mensagem.contains(" closes ") || 
			mensagem.contains(" closed ") || 
			mensagem.contains(" fix ") || 
			mensagem.contains(" fixes ") || 
			mensagem.contains(" fixed ") || 
			mensagem.contains(" resole ") || 
			mensagem.contains(" resolves ") || 
			mensagem.contains(" resolved ")){
			return true;
		}
		
		return false;
		
	};
	
	
	
	
	
}
