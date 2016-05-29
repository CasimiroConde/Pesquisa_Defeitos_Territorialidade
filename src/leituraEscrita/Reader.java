package leituraEscrita;

import issuesRepositorios.MetodosAuxiliares;
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

import marcacoesIssues.AgrupadorMarcacao;
import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.MarcacaoIssue;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;


public class Reader {

	final static String ARQUIVO = "arquivos//listaRepositorios//saidaRepositorios//Sorteado//repositorio.txt";
	final static String ARQUIVOLABELS = "arquivos//Marcacoes Consolidadas//consolidado.csv";
	final static String LISTA_REPO_MODO_RAPIDO = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaRepositorios/saidaRepositorios/lista_repo22.txt";


	/**Gera arquivo de download de commits e loc
	 * Executa a leitura do arquivo, testando cada uma das tags para identificar
	 * qual o tipo de informaï¿½ï¿½o que erï¿½ lida.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void executeGeradorScriptCommitLoc(int TAMANHO_AMOSTRA, int INICIO) throws InterruptedException, IOException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(ARQUIVO)));
		int cont = 0;

		while ((file.hasNext()) && (cont < TAMANHO_AMOSTRA)) {
			String linha = file.nextLine();
			if(!linha.isEmpty()){
				if(cont > INICIO){
					String[] linhaDivida = linha.split(";");
					Repositorio r = new Repositorio(linhaDivida[2], linhaDivida[3]);
					if(!r.getRepositoryName().equals("Vazio")){
						Writer.printArquivoDownloadCommitLoc(r.getUserName(), r.getRepositoryName());
						System.out.println(cont + " ; Gerado Script Repositorio: " + r.getUserName() + "/" + r.getRepositoryName());
					}
				}
				cont++;
			}
		}
	}






	/**
	 * Executa a leitura do arquivo, testando cada uma das tags para identificar
	 * qual o tipo de informaï¿½ï¿½o que erï¿½ lida.
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
				Repositorio r = new Repositorio(linhaDivida[2], linhaDivida[9]);
				if(!r.getRepositoryName().equals("Vazio")){
					repositorios.add(r);
				}
				cont++;
				System.out.println("Carregado Repositorio: " + r.getUserName() + "/" + r.getRepositoryName());
			}
		}
		return repositorios;
	}

	public static void executeListaComAnalise(GitHubClient client, int TAMANHO_AMOSTRA, int INICIO, ArrayList<LabelConsolidado> consolidadoLabel, ArrayList<MarcacaoIssue> marcacoes) throws InterruptedException, IOException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(ARQUIVO)));
		RepositoryService repositoryService = new RepositoryService(client);
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		UserService userService = new UserService(client);
		int cont = 0;

		while ((file.hasNext()) && (cont < TAMANHO_AMOSTRA)) {
			String linha = file.nextLine();
			if(!linha.isEmpty()){
				if(cont > INICIO){
					String[] linhaDivida = linha.split(";");
					try{
						Repositorio r = new Repositorio(linhaDivida[2], linhaDivida[3], client, consolidadoLabel);
						if(!r.getRepositoryName().equals("Vazio")){
							//r.downloadCommits(commitService, userService);
							//r.defeitosCorrigidosCommitOrigemCSV();
							//r.calculaIssuesFechadosCommit();
							MetodosAuxiliares.analiseMarcacao(consolidadoLabel, marcacoes, r);
							Writer.printConteudoCSV(r, cont);
							Writer.printConteudoRepositorioIssuesCSV(r, cont, client);
							Writer.printAnaliseMarcacaoIssuePorProjeto(r, cont);
							Writer.printContributors(r);
							Writer.printAnaliseMarcacaoCompleta(marcacoes);
							System.out.println(cont + " ; Carregado Repositorio: " + r.getUserName() + "/" + r.getRepositoryName());
	
						}
					}catch (IOException i){
						System.out.println("Não foi possível achar esse repositório");
					}
				}				
				cont++;
			}
		}

		Writer.printAnaliseMarcacaoCompleta(marcacoes);
	}

	public static void executeListaComAnaliseHasIssue(GitHubClient client, int TAMANHO_AMOSTRA, int INICIO, ArrayList<LabelConsolidado> consolidadoLabel, ArrayList<MarcacaoIssue> marcacoes) throws InterruptedException, IOException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(ARQUIVO)));
		RepositoryService repositoryService = new RepositoryService(client);

		int cont = 0;

		while ((file.hasNext()) && (cont < TAMANHO_AMOSTRA)) {
			String linha = file.nextLine();
			if(!linha.isEmpty()){
				if(cont > INICIO){
					String[] linhaDivida = linha.split(" ");
					Repositorio r = new Repositorio(linhaDivida[2], linhaDivida[9], client, "Simples");
					if(!r.getRepositoryName().equals("Vazio")){
						Writer.printHasIssueCSV(r, cont);
						System.out.println(cont + " ; Carregado Repositorio: " + r.getUserName() + "/" + r.getRepositoryName());

					}
				}
				cont++;
			}
		}

		Writer.printAnaliseMarcacaoCompleta(marcacoes);
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

	
	public static void inicializaListaRepositoriosModoRapido(GitHubClient client, int inicio) throws IOException, RequestException, InterruptedException {
		StringBuilder buffer = new StringBuilder();
		RepositoryService repositoryService = new RepositoryService(client);
		int cont = 34233819;
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put(RepositoryService.FILTER_TYPE, "public");

		Map<String, String> paramsIssues = new HashMap<String, String>();
	    paramsIssues.put(IssueService.FILTER_STATE, "all");

	    // 28675605
		PageIterator<Repository> iterator = repositoryService.pageAllRepositories(inicio);

		while(iterator.hasNext()) 
		{
			Collection<Repository> page = iterator.next();
			java.util.Iterator<Repository> itr = page.iterator();

			try{
					while(itr.hasNext()) {
						Repository repository = itr.next();
						
				    	if(!repository.isPrivate()) {
					    	Writer.geraBufferRepositorio(buffer, repository.getId(), repository.getOwner().getLogin(), repository.getName() , cont );
							System.out.println("Armazenando o número: " +cont);
							Writer.escreveArquivo(LISTA_REPO_MODO_RAPIDO, buffer);
					    	buffer = new StringBuilder();
					    	cont++;
				    	}
					}	
			}
			catch (NoSuchPageException e )
			{
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Repositï¿½rios Inicializados! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;
			}
			catch (RequestException e)
			{
				if(e.getStatus() == 403){
					if(e.getMessage().equals("Repository access blocked (403)")){
						System.out.println("Acesso bloqueado ao Repositï¿½rio");
					} else {
					System.out.println("Repositï¿½rios Inicializados! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min" + " Erro: " + e.getStatus() + "-" + e.getMessage());
					Thread.sleep(600 * 1000);
					}
				}
			}
		}
	}
		
	
	public static ArrayList<Repositorio> inicializaListaRepositorios(GitHubClient client, int TAMANAHO_LISTA) throws IOException, RequestException, InterruptedException {
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
				    	if(!repository.isPrivate()){
					    	Repositorio repo = new Repositorio(client, repository.getOwner().getLogin(), repository.getName());
					    	if(!repo.getRepositoryName().equals("Vazio")){
						    	//if(validaRepositorio(repository, repo, issueService, paramsIssues, commitService)){
						    		incluiRepositorio(buffer, repositorios, repo, cont );
									System.out.println(cont);
									cont++;
						    	//}
							}
					    	if((cont % 2000) == 0){
					    			Writer.escreveArquivo(criaNome(cont), buffer);
					    			System.out.println("Repositï¿½rios Inicializados! Lista de " + cont);
					    	}
				    	}
		    		}

			}catch (NoSuchPageException e ){
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Repositï¿½rios Inicializados! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;

			} catch (RequestException e){
				if(e.getStatus() == 403){
					if(e.getMessage().equals("Repository access blocked (403)")){
						System.out.println("Acesso bloqueado ao Repositï¿½rio");
					} else {
					System.out.println("Repositï¿½rios Inicializados! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min" + " Erro: " + e.getStatus() + "-" + e.getMessage());
					Thread.sleep(600 * 1000);
					}
				}
			}
		}
		return repositorios;
	}

	private static boolean validaRepositorio(Repository repository, Repositorio repo,
			IssueService issueService, Map<String, String> paramsIssues, CommitService commitService){
		if(repository.isPrivate())
			return false;

		/*boolean issuesEmpty;
		try {
			issuesEmpty = issueService.getIssues(repo.getRepoId(), paramsIssues).isEmpty();
			if(issuesEmpty)
				return false;
		} catch (IOException e1) {
			return false;
		}*/

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

	private static void incluiRepositorio(StringBuilder buffer, ArrayList<Repositorio> repositorios, Repositorio repo, int cont)
			throws IOException, RequestException {
		repositorios.add(repo);
		Writer.printRepositorios(buffer, repo, cont);
	}

	private static String criaNome(int cont){
		String nomeArquivoRepositorios =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaRepositorios/saidaRepositorios" + cont + ".txt";
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
