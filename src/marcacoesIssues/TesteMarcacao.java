package marcacoesIssues;

import static org.junit.Assert.*;
import issuesRepositorios.Repositorio;

import java.io.IOException;
import java.util.ArrayList;

import leituraEscrita.Reader;
import leituraEscrita.Writer;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.IssueService;
import org.junit.Test;

public class TesteMarcacao {

	static String nomeArquivoAnalise =  "C:/Users/Casimiro/git/Territorialidade/arquivos de saida/Teste/saida_Analise_Marcacao.txt";	

	
	@Test
	public void test() throws InterruptedException, IOException {
		
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token("69c7bb0ea0ff1a09c9e0a37522b562f0217eb1d1");
		IssueService issueService = new IssueService(client);
		ArrayList<LabelConsolidado> consolidadoLabel= Reader.geraListaConsolidadaLabels();
		
		Repositorio repositorio = new Repositorio("CasimiroConde", "Pesquisa_Defeitos_Territorialidade");
		
		repositorio.calculaQuantidadesIssues(issueService,consolidadoLabel);
		
		Writer.printAnaliseMarcacaoIssue(nomeArquivoAnalise, repositorio, 1);	
}

}
