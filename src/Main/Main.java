package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;


public class Main {
	
	static Repositorio[] r = new Repositorio[20];
	static String nomeArquivoAnalise =  "C:/Users/Casimiro/git/Territorialidade/arquivos de saida/saida.txt";
	static String nomeArquivoRepositorios =  "C:/Users/Casimiro/git/Territorialidade/arquivos de saida/saidaRepositorios.txt";
	

	public static void main(String[] args) throws IOException, RequestException, NoSuchPageException {
		
		int totalOpenIssue = 0;
		int totalClosedIssue = 0;
		int totalOpenIssueBug = 0;
		int totalClosedIssueBug = 0;
		int totalContadorIssuesCorrigidosCommits = 0;
		int totalContadorIssuesBugCorrigidosCommits = 0;
		double totalPorcentualIssuesFechadosCommit = 0.0;
		double totalPorcentualIssuesBugFechadosCommit = 0.0;
		double totalPorcentualIssuesBugFechadosCommitTotal = 0.0;
		
		//Autentica Cliente e inicializa serviços
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token("9727fe050adb275d94ae6303a2134d1ad421c0d2");
		
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		
		//String de Gravação
		StringBuilder buffer = new StringBuilder();
		
		//Inicializa Repositórios
		ArrayList<Repositorio> repositorios = inicializaListaRepositórios(client);
		
		//Calculo das Quantidades de Issues
		for(Repositorio r : repositorios){
			r.calculaQuantidadesIssues(issueService);
			totalOpenIssue += r.getOpenIssue();
			totalClosedIssue += r.getClosedIssue();
			totalOpenIssueBug += r.getOpenIssueBug();
			totalClosedIssueBug += r.getClosedIssueBug();
			
		//Encontro os commits feitos que fecharam um issue
			r.defeitosCorrigidosCommit(commitService, issueService);			
			totalContadorIssuesCorrigidosCommits += r.getContadorIssuesCorrigidosCommits();
			totalContadorIssuesBugCorrigidosCommits += r.getContadorIssuesBugCorrigidosCommits();
			
		//Calcula % de 	issues encerrados atraves de commit em um repositorio
			r.calculaIssuesFechadosCommit();	
			Writer.printConteudo(buffer, r);
			System.out.println("Encerrado Repositório: " + r.getUserName() +"/" + r.getRepositoryName());
		}
		
		if(totalContadorIssuesCorrigidosCommits > 0){
			totalPorcentualIssuesFechadosCommit = (double) (totalContadorIssuesCorrigidosCommits * 100) / totalClosedIssue;
			totalPorcentualIssuesBugFechadosCommit = (double) (totalContadorIssuesBugCorrigidosCommits * 100) / totalClosedIssueBug;
			totalPorcentualIssuesBugFechadosCommitTotal = (double) (totalContadorIssuesBugCorrigidosCommits * 100) / totalClosedIssue;
		}
		
		Writer.printConteudoTodosRepositorios(buffer, totalOpenIssue, totalClosedIssue, totalOpenIssueBug, totalClosedIssueBug, totalContadorIssuesCorrigidosCommits, totalContadorIssuesBugCorrigidosCommits, totalPorcentualIssuesFechadosCommit, totalPorcentualIssuesBugFechadosCommit, totalPorcentualIssuesBugFechadosCommitTotal);
		
		Writer.criaArquivo(nomeArquivoAnalise, buffer);
		System.out.println("Arquivo Gravado!");
	}


	private static ArrayList<Repositorio> inicializaListaRepositórios(GitHubClient client) throws IOException, RequestException {
		ArrayList<Repositorio> repositorios = new ArrayList<Repositorio>();
		StringBuilder buffer = new StringBuilder();
		try{
			
			RepositoryService repositoryService = new RepositoryService(client);
			IssueService issueService = new IssueService(client);
			CommitService commitService = new CommitService(client);
			
			int cont = 0;
			
			Map<String, String> params = new HashMap<String, String>();
		    params.put(RepositoryService.FILTER_TYPE, "public");
	
			Map<String, String> paramsIssues = new HashMap<String, String>();
		    paramsIssues.put(IssueService.FILTER_STATE, "all");
			    
			PageIterator<Repository> iterator = repositoryService.pageAllRepositories();
			
			while(iterator.hasNext() && cont < 800){
				Collection<Repository> page = iterator.next();
		    	java.util.Iterator<Repository> itr = page.iterator(); 
	    		while(itr.hasNext()){
			    	Repository repository = itr.next();
			    	Repositorio repo = new Repositorio(repository.getOwner().getLogin(), repository.getName());
					if(validaRepositorio(repository, repo, issueService, paramsIssues, commitService)){
						incluiRepositorio(buffer, repositorios, repo);
						cont++;
			    		System.out.println(cont);
			    		}						
	    		}
			}							
			Writer.criaArquivo(nomeArquivoRepositorios, buffer);
			System.out.println("Repositórios Inicializados!");
			return repositorios;		
		}catch (NoSuchPageException e ){
			Writer.criaArquivo(nomeArquivoRepositorios, buffer);
			System.out.println("Repositórios Inicializados!");
			return repositorios;		
				
		}
	}
	
	private static boolean validaRepositorio(Repository repository, Repositorio repo, 
			IssueService issueService, Map<String, String> paramsIssues, CommitService commitService){
		if(repository.isPrivate())
			return false;
		
		boolean issuesEmpty;
		try {
			issuesEmpty = issueService.getIssues(repo.getRepoId(), paramsIssues).isEmpty();
			if(issuesEmpty)
				return false;
		} catch (IOException e1) {
			return false;
		}

		return true;
	}

	private static void incluiRepositorio(StringBuilder buffer, ArrayList<Repositorio> repositorios, Repositorio repo)
			throws IOException, RequestException {
		repositorios.add(repo);
		Writer.printRepositórios(buffer, repo);
	}
	
}
