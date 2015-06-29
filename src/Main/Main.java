package Main;

import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;


public class Main {
	
	static Repositorio[] r = new Repositorio[1];
	static String nomeArquivo =  "C:/Users/Casimiro/git/Territorialidade/arquivos de saida/saida.txt";
	

	public static void main(String[] args) throws IOException {
		
		int totalOpenIssue = 0;
		int totalClosedIssue = 0;
		int totalOpenIssueBug = 0;
		int totalClosedIssueBug = 0;
		int totalContadorIssuesCorrigidosCommits = 0;
		int totalContadorIssuesBugCorrigidosCommits = 0;
		double totalPorcentualIssuesFechadosCommit = 0.0;
		double totalPorcentualIssuesBugFechadosCommit = 0.0;
		
		//Autentica Cliente e inicializa serviços
		GitHubClient client = new GitHubClient();
		client.setCredentials("CasimiroConde", "mestrado15");
		IssueService issueService = new IssueService(client);
		CommitService commitService = new CommitService(client);
		
		//String de Gravação
		StringBuilder buffer = new StringBuilder();
		
		//Inicializa Repositórios
		ArrayList<Repositorio> repositorios = inicializaListaRepositórios();
		
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
		}
		
		Writer.printConteudoTodosRepositorios(buffer, totalOpenIssue, totalClosedIssue, totalOpenIssueBug, totalClosedIssueBug, totalContadorIssuesCorrigidosCommits, totalPorcentualIssuesFechadosCommit, totalPorcentualIssuesBugFechadosCommit);
		
		Writer.criaArquivo(nomeArquivo, buffer);
		System.out.println("Arquivo Gravado!");
	}


	private static ArrayList<Repositorio> inicializaListaRepositórios() {
		r[0] = new Repositorio("CasimiroConde", "Pesquisa_Defeitos_Territorialidade");
		/*r[1] = new Repositorio("purifycss", "purifycss");
		r[2] = new Repositorio("brython-dev", "brython");
		r[3] = new Repositorio("facebook", "infer");
		r[4] = new Repositorio("sindresorhus", "awesome");
		r[5] = new Repositorio("donnemartin", "data-science-ipython-notebooks");
		r[6] = new Repositorio("facebook", "nuclide");
		r[7] = new Repositorio("JakeWharton", "butterknife");
		r[8] = new Repositorio("thoughtbot", "Tropos");
		r[9] = new Repositorio("equinusocio", "material-theme");
		r[10] = new Repositorio("WebAssembly", "design");
		r[11] = new Repositorio("bpampuch", "pdfmake");
		r[12] = new Repositorio("RocketChat", "Rocket.Chat");
		r[13] = new Repositorio("mattermost", "platform");
		r[14] = new Repositorio("getify", "You-Dont-Know-JS");
		r[15] = new Repositorio("jspahrsummers", "libextobjc");
		r[16] = new Repositorio("AliSoftware", "OHHTTPStubs");
		r[17] = new Repositorio("github", "Rebel");
		r[18] = new Repositorio("tcurdt", "feedbackreporter");
		r[19] = new Repositorio("karelia", "KSFileUtilities");*/

		ArrayList<Repositorio> repositorios = new ArrayList<Repositorio>();
		
		for(Repositorio rep : r){
		repositorios.add(rep);
		}
		return repositorios;
	}
	
	
}
