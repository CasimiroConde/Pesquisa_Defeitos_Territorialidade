package Main;	

import issuesRepositorios.Repositorio;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import leituraEscrita.ConsolidadorDeArquivos;
import leituraEscrita.Reader;
import leituraEscrita.Writer;
import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.MarcacaoIssue;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.UserService;


public class Main {

	static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
	static Calendar cal = Calendar.getInstance();

	static Repositorio[] r = new Repositorio[20];
	static String nomeArquivoAnalise =  "//arquivos//analiseRepo//saida_" + dateFormat.format(cal.getTime())+ ".txt";
	static String nomeArquivoAnaliseCSV =  "//arquivos//analiseRepo//saida_" + dateFormat.format(cal.getTime())+ ".csv";
	static String nomeArquivoAnaliseCSVConsolidado =  "//arquivos//analiseRepoConsolidado//saida_" + dateFormat.format(cal.getTime())+ "_Consolidado"+ ".csv";
	static String nomeArquivoMarcacaoAnalitico =  "//arquivos//analiseMarcacao//Analise_Marcacao_" + dateFormat.format(cal.getTime())+ ".txt";
	static String nomeArquivoMarcacaoConsolidado =  "//arquivos//analiseMarcacao//Consolidado//Analise_Marcacao_Consolidado_"+ dateFormat.format(cal.getTime()) +".txt";
	static String nomeArquivoListaIssue =  "//arquivos//listaIssue//listaIssue"+ dateFormat.format(cal.getTime()) +".txt";
	static String nomeArquivoContributors =  "//arquivos//listaContributors//listaContributors"+ dateFormat.format(cal.getTime()) +".txt";
	static String pastaLoc = "C:/Users/MARCIO.BARROS/Documents/Saidas";


	static String arquivoOrigemInclusaoLoc = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseRepo/saida_20-05-2016_01-09.csv";
	static String arquivoConsolidadoInclusaoLoc = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseRepo/ComLoc/saida_20-05-2016_01-09_ComLoc.csv";
	static String arquivoConsolidadoAnaliseCommit = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/ConsolidadoPosAnalise/SaidaAnaliseCommit_1.csv";

	static int TAMANHO_AMOSTRA = 300000;
	static int INICIO = 110460;
 //68832
	public static void main(String[] args) throws IOException, RequestException, NoSuchPageException, InterruptedException {

		int totalOpenIssue = 0;
		int totalClosedIssue = 0;
		int totalOpenIssueBug = 0;
		int totalClosedIssueBug = 0;
		int totalContadorIssuesCorrigidosCommits = 0;
		int totalContadorIssuesBugCorrigidosCommits = 0;
		double totalPorcentualIssuesFechadosCommit = 0.0;
		double totalPorcentualIssuesBugFechadosCommit = 0.0;
		double totalPorcentualIssuesBugFechadosCommitTotal = 0.0;
		int cont = 0;

		//Autentica Cliente e inicializa servi�os
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token("b7dd6cda01fdf3d51841473e96ff9aa9d4a62924");
		CommitService commitService = new CommitService(client);
		IssueService issueService = new IssueService(client);
		UserService userService = new UserService(client);
				
		ArrayList<LabelConsolidado> consolidadoLabel= Reader.geraListaConsolidadaLabels();
		System.out.println("Consolidado Inicializado!!!");
		//Inicializa Reposit�rios
		//Reader.inicializaListaRepositoriosModoRapido(client, INICIO);
		//ArrayList<Repositorio> repositorios = Reader.inicializaListaRepositorios(client, TAMANHO_AMOSTRA);
		//ArrayList<Repositorio> repositorios = Reader.executeListaSimples(client, TAMANHO_AMOSTRA);
		ArrayList<MarcacaoIssue> marcacoes = new ArrayList<MarcacaoIssue>();

	Reader.executeListaComAnalise(client, TAMANHO_AMOSTRA, INICIO, consolidadoLabel, marcacoes);
		//Reader.executeListaComAnaliseHasIssue(client, TAMANHO_AMOSTRA, INICIO, consolidadoLabel, marcacoes);

		//Reader.executeGeradorScriptCommitLoc(TAMANHO_AMOSTRA, INICIO);


		//ConsolidadorDeArquivos.consolidador();
	//ConsolidadorDeArquivos.consolidaLocDeArquivoOrigem(pastaLoc, arquivoOrigemInclusaoLoc, arquivoConsolidadoInclusaoLoc);
	//ConsolidadorDeArquivos.consolidaInfoCommitsDeArquivoOrigem(pastaLoc, arquivoConsolidadoInclusaoLoc, arquivoConsolidadoAnaliseCommit, INICIO, TAMANHO_AMOSTRA);



		//Calculo das Quantidades de Issues
		//for(int cont = 427; cont < 1000 ; cont++){
		//for(Repositorio r: repositorios){
		//	Repositorio r = repositorios.get(cont);
			//r.downloadCommits(commitService, userService);


			//r.calculaQuantidadesIssues(consolidadoLabel);
			/*totalOpenIssue += r.getOpenIssue();
			totalClosedIssue += r.getClosedIssue();
			totalOpenIssueBug += r.getOpenIssueBug();
			totalClosedIssueBug += r.getClosedIssueBug();
			*/

			//analiseMarcacao(consolidadoLabel, marcacoes, r);

		//Encontro os commits feitos que fecharam um issue
			//r.defeitosCorrigidosCommit(commitService, issueService);
			//r.defeitosCorrigidosCommitOrigemCSV();
			/*totalContadorIssuesCorrigidosCommits += r.getContadorIssuesCorrigidosCommits();
			totalContadorIssuesBugCorrigidosCommits += r.getContadorIssuesBugCorrigidosCommits();
			*/

		// Armazena commits em arquivos

		//Calcula % de 	issues encerrados atraves de commit em um repositorio
			//r.calculaIssuesFechadosCommit();
			//Writer.printConteudoCSV(r, cont);
			//Writer.printConteudoRepositorioIssuesCSV(r, cont, client);
			//Writer.printContributors(r);
			//System.out.println("Encerrado Reposit�rio: " + r.getUserName() +"/" + r.getRepositoryName());

			//Writer.printAnaliseMarcacaoIssue(nomeArquivoMarcacaoAnalitico, r, cont);
			//cont++;

				//Writer.printAnaliseMarcacaoCompleta(nomeArquivoMarcacaoConsolidado, marcacoes);
	//	}
		/*if(totalContadorIssuesCorrigidosCommits > 0){
			totalPorcentualIssuesFechadosCommit = (double) (totalContadorIssuesCorrigidosCommits * 100) / totalClosedIssue;
			totalPorcentualIssuesBugFechadosCommit = (double) (totalContadorIssuesBugCorrigidosCommits * 100) / totalClosedIssueBug;
			totalPorcentualIssuesBugFechadosCommitTotal = (double) (totalContadorIssuesBugCorrigidosCommits * 100) / totalClosedIssue;
		}*/

		//Writer.printConteudoTodosRepositoriosCSV(nomeArquivoAnaliseCSVConsolidado , totalOpenIssue, totalClosedIssue, totalOpenIssueBug, totalClosedIssueBug, totalContadorIssuesCorrigidosCommits, totalContadorIssuesBugCorrigidosCommits, totalPorcentualIssuesFechadosCommit, totalPorcentualIssuesBugFechadosCommit, totalPorcentualIssuesBugFechadosCommitTotal);

		System.out.println("Arquivo Gravado!");
	}

}




