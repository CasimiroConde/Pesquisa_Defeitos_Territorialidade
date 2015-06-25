package Main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.directory.SearchControls;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.SearchIssue;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.sun.org.apache.bcel.internal.generic.ISUB;


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
		client.setCredentials("CasimiroConde", "mestrado15");
		
		int openIssue = 0;
		int closedIssue = 0;
		int contadorDefeitosCorrigidosCommits = 0;
		
		RepositoryService repositoryService = new RepositoryService(client);
		IRepositoryIdProvider repoId = new RepositoryId("purifycss","purifycss");
	    
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		
		
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put(IssueService.FILTER_STATE, "all");
	    PageIterator<Issue> iterator = issueService.pageIssues(repoId,
		            params, 10);
		
	    
	    
	    while(iterator.hasNext()){
	    	Collection<Issue> page = iterator.next();
	    	System.out.println(page.size());
	    	java.util.Iterator<Issue> itr = page.iterator(); 
	    	while(itr.hasNext()){
	    		Issue issue = itr.next();
	    		System.out.println(issue.getTitle());

	    		if(issue.getState().equalsIgnoreCase("open"))
					openIssue++;
				
				if(issue.getState().equalsIgnoreCase("closed"))
					closedIssue++;   		
	    	}
	    
		}
		
		
		
		System.out.println(openIssue + " " + closedIssue);
		
		
		//Encontro os commits feitos que fecharam um issue
		
		for(RepositoryCommit c : commitService.getCommits(repoId)){
			if(contemPalavraChave(c.getCommit().getMessage())){
				String[] palavras = c.getCommit().getMessage().split(" ");
				for(String palavra : palavras){
					if(ePalavraChave(palavra)){
						contadorDefeitosCorrigidosCommits++;
						System.out.println(palavra);
					}
				}
			}
		}
		
		System.out.println(contadorDefeitosCorrigidosCommits);
	}
	
	public static boolean ePalavraChave(String mensagem){
		mensagem = mensagem.toLowerCase();
		
		if(mensagem.equals("close") || 
			mensagem.equals("closes") || 
			mensagem.equals("closed") || 
			mensagem.equals("fix") || 
			mensagem.equals("fixes") || 
			mensagem.equals("fixed") || 
			mensagem.equals("resole") || 
			mensagem.equals("resolves") || 
			mensagem.equals("resolved")){
			return true;
		}
		
		return false;
		
	}
	
	public static boolean contemPalavraChave(String mensagem){
		mensagem = mensagem.toLowerCase();
		
		if(mensagem.contains("close") || 
			mensagem.contains("closes") || 
			mensagem.contains("closed") || 
			mensagem.contains("fix") || 
			mensagem.contains("fixes") || 
			mensagem.contains("fixed") || 
			mensagem.contains("resole") || 
			mensagem.contains("resolves") || 
			mensagem.contains("resolved")){
			return true;
		}
		
		return false;
		
	}
	
	
	
	
	
}
