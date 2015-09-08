package leituraEscrita;

import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.AgrupadorMarcacao;
import issuesRepositorios.Repositorio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;
import com.sun.xml.internal.ws.org.objectweb.asm.Label;


public class Reader {

	final static String ARQUIVO = "arquivos//listaRepositorios//saidaRepositorios1000.txt";
	final static String ARQUIVOLABELS = "arquivos//Marcacoes Consolidadas//consolidado.csv";
	

	/**
	 * Executa a leitura do arquivo, testando cada uma das tags para identificar 
	 * qual o tipo de informação que erá lida.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static ArrayList<Repositorio> executeListaSimples(GitHubClient client, int TAMANHO_AMOSTRA) throws InterruptedException, IOException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(ARQUIVO)));
		ArrayList<Repositorio> repositorios = new ArrayList<Repositorio>();
		RepositoryService repositoryService = new RepositoryService(client);
		IssueService issueService = new IssueService(client);
		int cont = 0;         	
		
		while ((file.hasNext()) && (cont < TAMANHO_AMOSTRA)) {
			String linha = file.nextLine();
			if(!linha.isEmpty()){
				String[] linhaDivida = linha.split(" ");
				Repositorio r = new Repositorio(linhaDivida[2], linhaDivida[9], client); 
				repositorios.add(r);
				cont++;
				System.out.println("Carregado Repositorio: " + r.getUserName() + "/" + r.getRepositoryName());
			}
		}
		return repositorios;
	}
	
	/*public static ArrayList<Repositorio> executeListaCompleta() throws FileNotFoundException {
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
	}*/
	
	public static ArrayList<Repositorio> inicializaListaRepositórios(GitHubClient client, int TAMANAHO_LISTA) throws IOException, RequestException, InterruptedException {
		ArrayList<Repositorio> repositorios = new ArrayList<Repositorio>();
		StringBuilder buffer = new StringBuilder();
		RepositoryService repositoryService = new RepositoryService(client);
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		int cont = 0;
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put(RepositoryService.FILTER_TYPE, "public");

		Map<String, String> paramsIssues = new HashMap<String, String>();
	    paramsIssues.put(IssueService.FILTER_STATE, "all");
		    
		PageIterator<Repository> iterator = repositoryService.pageAllRepositories();
		
		while(cont < TAMANAHO_LISTA){
			try{	
					Collection<Repository> page = iterator.next();
			    	java.util.Iterator<Repository> itr = page.iterator(); 
		    		while(itr.hasNext()){
				    	Repository repository = itr.next();
				    	Repositorio repo = new Repositorio(repository.getOwner().getLogin(), repository.getName(), client);
				    	if(!repo.getRepositoryName().equals("Vazio")){
					    	if(validaRepositorio(repository, repo, issueService, paramsIssues, commitService)){
					    		incluiRepositorio(buffer, repositorios, repo);
								System.out.println(cont);
								cont++;
					    	}
						}
				    	if((cont % 100) == 0){
				    			Writer.escreveArquivo(criaNome(cont), buffer);
				    			System.out.println("Repositórios Inicializados! Lista de " + cont);
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
			if(sizeCommit < 2)
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
		String nomeArquivoRepositorios =  "C:/Users/Casimiro/git/Territorialidade/arquivos/listaRepositorios/saidaRepositorios" + cont + ".txt";
		return nomeArquivoRepositorios;
	}
	
	public static String retornaConteudo(File f) throws FileNotFoundException{
		Scanner content = new Scanner(new BufferedReader(new FileReader(f)));
		String contentString = "";
		while(content.hasNext()){
			contentString = contentString + " " +content.nextLine();	
		}
		
		return contentString;
	}
	
	public static ArrayList<LabelConsolidado> geraListaConsolidadaLabels() throws FileNotFoundException{
		Scanner file = new Scanner(new BufferedReader(new FileReader(ARQUIVOLABELS)));
		ArrayList<LabelConsolidado> consolidado = new ArrayList<LabelConsolidado>();
		
		while(file.hasNext()){
			String linha = file.nextLine();
			LabelConsolidado label;
			AgrupadorMarcacao tipo;
			String[] listaPalavras = linha.split(";");
			ArrayList<String> variacoes = new ArrayList<String>();			
			for(int i = 2; i < listaPalavras.length ; i++){
				variacoes.add(listaPalavras[i]);
			}
			tipo = AgrupadorMarcacao.get(listaPalavras[1]);
			label = new LabelConsolidado(listaPalavras[0], tipo, variacoes);
			consolidado.add(label);	
		}
		return consolidado;
	}
	



}
