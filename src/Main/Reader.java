package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;


public class Reader {

	final static String ARQUIVO = "arquivos de saida//saidaRepositorios1000.txt";
	final static String ARQUIVOCOMPLETO = "arquivos de saida//saida_10-07-2015_01-25.txt";

	/**
	 * Executa a leitura do arquivo, testando cada uma das tags para identificar 
	 * qual o tipo de informação que erá lida.
	 */
	public static ArrayList<Repositorio> executeListaSimples() throws FileNotFoundException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(ARQUIVO)));
		ArrayList<Repositorio> repositorios = new ArrayList<Repositorio>();
		
		while (file.hasNext()) {
			String linha = file.nextLine();
			if(!linha.isEmpty()){
				String[] linhaDivida = linha.split(" ");
				Repositorio r = new Repositorio(linhaDivida[2], linhaDivida[9]); 
				repositorios.add(r);
			}
		}
		return repositorios;
	}
	
	public static ArrayList<Repositorio> executeListaCompleta() throws FileNotFoundException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(ARQUIVOCOMPLETO)));
		ArrayList<Repositorio> repositorios = new ArrayList<Repositorio>();
		
		while (file.hasNext()) {
			if(file.nextLine().startsWith("_")){
				String[][] linhaDividida = new String[7][10];
				for(int i = 0 ; i < 7 ; i++){
					linhaDividida[i] = file.nextLine().split(" ");
				}	
				Repositorio r = new Repositorio(linhaDividida[0][1], linhaDividida[0][3]); 
				r.setClosedIssue(Integer.parseInt(linhaDividida[2][3]));
				r.setOpenIssue(Integer.parseInt(linhaDividida[2][1]));
				r.setClosedIssueBug(Integer.parseInt(linhaDividida[4][3]));
				r.setOpenIssueBug(Integer.parseInt(linhaDividida[4][1]));
				r.setContadorIssuesCorrigidosCommits(Integer.parseInt(linhaDividida[5][5].replaceAll("[^0-9]", "")));
				r.setContadorIssuesBugCorrigidosCommits(Integer.parseInt(linhaDividida[6][6].replaceAll("[^0-9]", "")));
				repositorios.add(r);
			}
		}
		return repositorios;
	}
	
	private static ArrayList<Repositorio> inicializaListaRepositórios(GitHubClient client) throws IOException, RequestException, InterruptedException {
		ArrayList<Repositorio> repositorios = new ArrayList<Repositorio>();
		StringBuilder buffer = new StringBuilder();
		int cont = 0;
		
		RepositoryService repositoryService = new RepositoryService(client);
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put(RepositoryService.FILTER_TYPE, "public");

		Map<String, String> paramsIssues = new HashMap<String, String>();
	    paramsIssues.put(IssueService.FILTER_STATE, "all");
		    
		PageIterator<Repository> iterator = repositoryService.pageAllRepositories();
		
		while(cont < 1000){
			try{	
				while(iterator.hasNext()){
					Collection<Repository> page = iterator.next();
			    	java.util.Iterator<Repository> itr = page.iterator(); 
		    		while(itr.hasNext()){
				    	Repository repository = itr.next();
				    	Repositorio repo = new Repositorio(repository.getOwner().getLogin(), repository.getName());
						if(validaRepositorio(repository, repo, issueService, paramsIssues, commitService)){
							incluiRepositorio(buffer, repositorios, repo);
							cont++;
				    		System.out.println(cont);
				    	if(cont == 200 || cont == 400 || cont == 600 || cont == 800 || cont == 1000){
				    			Writer.criaArquivo(criaNome(cont), buffer);
				    			System.out.println("Repositórios Inicializados! Lista de " + cont);
				    		}
				    	}						
		    		}
				}									
			}catch (NoSuchPageException e ){
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Repositórios Inicializados! Máximo de Requisições Alcançada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;		
					
			}
		}
		return repositorios;
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
	
		int sizeCommit;
		try {
			sizeCommit = commitService.getCommits(repo.getRepoId()).size();
			if(sizeCommit < 100)
				return false;
		} catch (IOException e) {
			return false;
		}	
		return true;
		
	}

	private static void incluiRepositorio(StringBuilder buffer, ArrayList<Repositorio> repositorios, Repositorio repo)
			throws IOException, RequestException {
		repositorios.add(repo);
		Writer.printRepositórios(buffer, repo);
	}
	
	private static String criaNome(int cont){
		String nomeArquivoRepositorios =  "C:/Users/Casimiro/git/Territorialidade/arquivos de saida/saidaRepositorios" + cont + ".txt";
		return nomeArquivoRepositorios;
	}

}
