package Main;

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

import leituraEscrita.Reader;
import leituraEscrita.Writer;

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
	static String nomeArquivoAnalise =  "C:/Users/Casimiro/git/Territorialidade/arquivos de saida/saida_10-07-2015_01-25.txt";	

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
		
		//Autentica Cliente e inicializa serviços
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token("039d4e625a156bac96770cab94acf34cd57722a6");
		
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		int cont = 0;
	
		//Inicializa Repositórios
		ArrayList<Repositorio> repositorios = Reader.executeListaCompleta();
				//Reader.executeListaSimples(); 
				//inicializaListaRepositórios(client);
		
		//Calculo das Quantidades de Issues
		for(Repositorio r: repositorios){
		//for(Repositorio r : repositorios){
			//Repositorio r = repositorios.get(i);
			//r.calculaQuantidadesIssues(issueService);
			totalOpenIssue += r.getOpenIssue();
			totalClosedIssue += r.getClosedIssue();
			totalOpenIssueBug += r.getOpenIssueBug();
			totalClosedIssueBug += r.getClosedIssueBug();
			
		//Encontro os commits feitos que fecharam um issue
			//r.defeitosCorrigidosCommit(commitService, issueService);			
			totalContadorIssuesCorrigidosCommits += r.getContadorIssuesCorrigidosCommits();
			totalContadorIssuesBugCorrigidosCommits += r.getContadorIssuesBugCorrigidosCommits();
			
		//Calcula % de 	issues encerrados atraves de commit em um repositorio
			//r.calculaIssuesFechadosCommit();	
			//cont = i;
			//Writer.printConteudo(nomeArquivoAnalise ,r, cont);
			//System.out.println("Encerrado Repositório: " + r.getUserName() +"/" + r.getRepositoryName());
			
		}
		
		if(totalContadorIssuesCorrigidosCommits > 0){
			totalPorcentualIssuesFechadosCommit = (double) (totalContadorIssuesCorrigidosCommits * 100) / totalClosedIssue;
			totalPorcentualIssuesBugFechadosCommit = (double) (totalContadorIssuesBugCorrigidosCommits * 100) / totalClosedIssueBug;
			totalPorcentualIssuesBugFechadosCommitTotal = (double) (totalContadorIssuesBugCorrigidosCommits * 100) / totalClosedIssue;
		}
		
		Writer.printConteudoTodosRepositorios(nomeArquivoAnalise , totalOpenIssue, totalClosedIssue, totalOpenIssueBug, totalClosedIssueBug, totalContadorIssuesCorrigidosCommits, totalContadorIssuesBugCorrigidosCommits, totalPorcentualIssuesFechadosCommit, totalPorcentualIssuesBugFechadosCommit, totalPorcentualIssuesBugFechadosCommitTotal);

		System.out.println("Arquivo Gravado!");
	}



	
}
