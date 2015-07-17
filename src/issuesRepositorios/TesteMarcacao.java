package issuesRepositorios;

import static org.junit.Assert.*;

import java.io.IOException;

import leituraEscrita.Writer;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.IssueService;
import org.junit.Test;

public class TesteMarcacao {

	static String nomeArquivoAnalise =  "C:/Users/Casimiro/git/Territorialidade/arquivos de saida/saida_Analise_Marcacao.txt";	

	
	@Test
	public void test() throws InterruptedException, IOException {
		
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token("514038d55145229aeda6c9b35cebd4f01337d0f6");
		IssueService issueService = new IssueService(client);
		
		Repositorio repositorio = new Repositorio("CasimiroConde", "Pesquisa_Defeitos_Territorialidade");
		
		repositorio.calculaQuantidadesIssues(issueService);
		
		Writer.printAnaliseMarcacaoIssue(nomeArquivoAnalise, repositorio, 1);	
}

}
