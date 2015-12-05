package Main;

import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.MarcacaoIssue;
import marcacoesIssues.MetodosAuxiliaresLabel;
import issuesRepositorios.MetodosAuxiliares;
import issuesRepositorios.Repositorio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import leituraEscrita.ConsolidadorDeArquivos;
import leituraEscrita.Reader;
import leituraEscrita.Writer;
import marcacoesIssues.TipoMarcacao;

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
	
	static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
	static Calendar cal = Calendar.getInstance();
	
	static Repositorio[] r = new Repositorio[20];
	static String nomeArquivoAnalise =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseRepo/saida_" + dateFormat.format(cal.getTime())+ ".txt";	
	static String nomeArquivoAnaliseCSV =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseRepo/saida_" + dateFormat.format(cal.getTime())+ ".csv";	
	static String nomeArquivoAnaliseCSVConsolidado =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseRepoConsolidado/saida_" + dateFormat.format(cal.getTime())+ "_Consolidado"+ ".csv";	
	static String nomeArquivoMarcacaoAnalitico =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseMarcacao/Analise_Marcacao_" + dateFormat.format(cal.getTime())+ ".txt";	
	static String nomeArquivoMarcacaoConsolidado =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseMarcacao/Consolidado/Analise_Marcacao_Consolidado_"+ dateFormat.format(cal.getTime()) +".txt";	
	static String nomeArquivoListaIssue =  "C:/Users/Casimiro/git/Territorialidade/arquivos/listaIssue/listaIssue"+ dateFormat.format(cal.getTime()) +".txt";	
	static String nomeArquivoContributors =  "C:/Users/Casimiro/git/Territorialidade/arquivos/listaContributors/listaContributors"+ dateFormat.format(cal.getTime()) +".txt";	
	static String pastaLoc = "F:/Saidas";
	
	static int TAMANHO_AMOSTRA = 9999;
	static int INICIO = 5518;
	
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
		
		//Autentica Cliente e inicializa serviços
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token("668e5ea312fb17429e51f12809451ec8882f7ef1");
		CommitService commitService = new CommitService(client);
		IssueService issueService = new IssueService(client);
		UserService userService = new UserService(client);
		 	
	
		ArrayList<LabelConsolidado> consolidadoLabel= Reader.geraListaConsolidadaLabels();
		System.out.println("Consolidado Inicializado!!!");
		//Inicializa Repositórios
		//ArrayList<Repositorio> repositorios = Reader.inicializaListaRepositórios(client, TAMANHO_AMOSTRA);
		//ArrayList<Repositorio> repositorios = Reader.executeListaSimples(client, TAMANHO_AMOSTRA);
		//ArrayList<MarcacaoIssue> marcacoes = new ArrayList<MarcacaoIssue>();
				
		//***********Reader.executeListaComAnalise(client, TAMANHO_AMOSTRA, INICIO, consolidadoLabel, marcacoes);
		
		//ConsolidadorDeArquivos.consolidador();
		ConsolidadorDeArquivos.consolidaLoc(pastaLoc);
		
		
		
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
			//System.out.println("Encerrado Repositório: " + r.getUserName() +"/" + r.getRepositoryName());
			
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


	

