package leituraEscrita;

import issuesRepositorios.Repositorio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import marcacoesIssues.MarcacaoIssue;
import marcacoesIssues.TipoMarcacao;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.UserService;

import Contributors.Contributors;




public class Writer {
	
	static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
	static Calendar cal = Calendar.getInstance();
	
	static String nomeArquivoAnalise =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseRepo/saida_" + dateFormat.format(cal.getTime())+ ".txt";	
	static String nomeArquivoAnaliseCSV =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseRepo/saida_" + dateFormat.format(cal.getTime())+ ".csv";	
	static String nomeArquivoAnaliseCSVConsolidado =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseRepoConsolidado/saida_" + dateFormat.format(cal.getTime())+ "_Consolidado"+ ".csv";	
	static String nomeArquivoMarcacaoAnalitico =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseMarcacao/Analise_Marcacao_" + dateFormat.format(cal.getTime())+ ".txt";	
	static String nomeArquivoMarcacaoConsolidado =  "C:/Users/Casimiro/git/Territorialidade/arquivos/analiseMarcacao/Consolidado/Analise_Marcacao_Consolidado_"+ dateFormat.format(cal.getTime()) +".txt";	
	static String nomeArquivoListaIssue =  "C:/Users/Casimiro/git/Territorialidade/arquivos/listaIssue/listaIssue"+ dateFormat.format(cal.getTime()) +".txt";	
	static String nomeArquivoContributors =  "C:/Users/Casimiro/git/Territorialidade/arquivos/listaContributors/listaContributors"+ dateFormat.format(cal.getTime()) +".txt";	

	
	
	
	public static void escreveArquivo(String nome, StringBuilder buffer) throws IOException{
		File arquivo = new File(nome);
		arquivo.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(arquivo, true);
		BufferedWriter bw = new BufferedWriter( fw );
		bw.newLine();
		bw.write(buffer.toString());
		bw.close();
	}
	
	public static void sobreescreveArquivo(String nome, StringBuilder buffer) throws IOException{
		File arquivo = new File(nome);
		arquivo.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(arquivo);
		BufferedWriter bw = new BufferedWriter( fw );
		bw.newLine();
		bw.write(buffer.toString());
		bw.close();
	}
	
	
	public static void printConteudo(String nome, Repositorio repositorio, int cont) throws IOException{
		
		StringBuilder buffer = new StringBuilder();
		//Informa formato de impressão de Double
		DecimalFormat format = new DecimalFormat("0");
			    
		
		buffer.append("________________||||Repositório: " + repositorio.getRepositoryName() + " Cont: " + cont+ "||||_________________________" + System.getProperty("line.separator"));
		buffer.append("Usuario: " + repositorio.getUserName() + " Repositório: " + repositorio.getRepositoryName() + System.getProperty("line.separator"));
		buffer.append("Seguidores do Owner: " + repositorio.getNumeroFollowersOwner() + "Seguidos pelo Owner:" + repositorio.getNumeroFollowingOwner() + System.getProperty("line.separator"));
		buffer.append("Forks:" + repositorio.getNumeroForks() + "Watchers" + repositorio.getNumeroWatchers() + System.getProperty("line.separator"));
		buffer.append("Tamanho (KiloBytes)" + repositorio.getTamanhoRepositorio() + System.getProperty("line.separator"));
		buffer.append("Listas de Issues dentro do Repositório" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + repositorio.getOpenIssue() + " Fechados: " + repositorio.getClosedIssue() + System.getProperty("line.separator"));
		buffer.append("Listas de Issues dentro do Repositório (Marcados como Bug)" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + repositorio.getOpenIssueBug() + " Fechados: " + repositorio.getClosedIssueBug()+ System.getProperty("line.separator"));
		buffer.append("Issues Encerrados em um Commit: " + repositorio.getContadorIssuesCorrigidosCommits()+ System.getProperty("line.separator"));
		buffer.append("Issues Bug Encerrados em um Commit: " + repositorio.getContadorIssuesBugCorrigidosCommits()+ System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues em um Commit: " + format.format(repositorio.getPorcentualIssuesFechadosCommit()) + "%" + System.getProperty("line.separator"));		
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Relação aos Bugs): " + format.format(repositorio.getPorcentualIssuesBugFechadosCommit()) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Relação a todos os Issues): " + format.format(repositorio.getPorcentualIssuesBugFechadosCommitTotal()) + "%" + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));
		
		escreveArquivo(nome, buffer);
		
	}
	
public static void printConteudoCSV(Repositorio repositorio, int cont) throws IOException{
		
		StringBuilder buffer = new StringBuilder();
		//Informa formato de impressão de Double
		DecimalFormat format = new DecimalFormat("0");
			    
		// User name; Repository Name; Issues Abertos; Issues Fechados; Issues Bug Abertos; Issues Bug Fechados; Issues Encerrados em Commit;
		//Issues Bug encerrados em commit; % Issues fechados em commit; % issues bug fechados em commit (X bug); % issues bug fechados em commit (X Total) 
		
		buffer.append(cont + ";");
		buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";" + repositorio.getUser() + ";");
		buffer.append(repositorio.getCreate() + ";" + repositorio.getIdade() + ";" + repositorio.getNumeroContributors() + ";" + repositorio.getLanguage() + ";");
		buffer.append(repositorio.getNumeroIssues()+ ";" + repositorio.getOpenIssue() + ";" + repositorio.getClosedIssue() + ";");
		buffer.append(repositorio.getOpenIssueBug() + ";" + repositorio.getClosedIssueBug() + ";");
		buffer.append(repositorio.getNumeroFollowersOwner() + ";" + repositorio.getNumeroFollowingOwner() + ";");
		buffer.append(repositorio.getNumeroForks() + ";" + repositorio.getNumeroWatchers() +";");
		buffer.append(repositorio.getTamanhoRepositorio() + ";");
		buffer.append(repositorio.getContadorIssuesCorrigidosCommits()+ ";");
		buffer.append(repositorio.getContadorIssuesBugCorrigidosCommits() + ";");
		buffer.append(format.format(repositorio.getPorcentualIssuesFechadosCommit()) + "%" +";");		
		buffer.append(format.format(repositorio.getPorcentualIssuesBugFechadosCommit()) + "%" + ";");
		buffer.append(format.format(repositorio.getPorcentualIssuesBugFechadosCommitTotal()) + "%");
		buffer.append(System.getProperty("line.separator"));
		
		escreveArquivo(nomeArquivoAnaliseCSV, buffer);
		
	}

public static void printConteudoRepositorioIssuesCSV(Repositorio repositorio, int cont, GitHubClient client) throws IOException, InterruptedException{
	
	StringBuilder buffer = new StringBuilder();
	//Informa formato de impressão de Double
	// User name; Repository Name; State; Owner name; Create at; Closed at; Time-to-fix
	
	
			for(Issue i : repositorio.getIssues()){
				boolean finished = false;
				while(!finished){	
					try{
						buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";");
						buffer.append(i.getState() + ";" );
						UserService userService = new UserService(client);
						User user = userService.getUser(i.getUser().getLogin());
						buffer.append(i.getId() + ";");
						buffer.append(user.getName() + ";");
						buffer.append(i.getCreatedAt()+ ";");
						buffer.append(i.getClosedAt()+ ";");
						buffer.append(repositorio.calculaTimeToFixIssue(i) + ";");
						buffer.append(System.getProperty("line.separator"));
						finished = true;
					}catch(RequestException e) {
						if(e.getStatus() == 403){
							System.out.println("Imprimindo issue!! Repositório: " + repositorio.getRepositoryName());
							Thread.sleep(600 * 1000);					
						}else{
							System.out.println("Excessão imprimindo issue Encerrado!! Repositório: " + repositorio.getRepositoryName());
							finished= true;
						}
					}		
				}
			
		
	}		
	escreveArquivo(nomeArquivoListaIssue, buffer);
	
}

public static void printConteudoTodosRepositoriosCSV(int totalOpenIssue, int totalClosedIssue, int totalOpenIssueBug, int totalClosedIssueBug, int totalContadorIssuesCorrigidosCommits, int totalContadorIssuesBugCorrigidosCommits, double totalPorcentualIssuesFechadosCommit, double totalPorcentualIssuesBugFechadosCommit, double totalPorcentualIssuesBugFechadosCommitTotal) throws IOException{
	StringBuilder buffer = new StringBuilder();
	
	//Informa formato de impressão de Double
	DecimalFormat format = new DecimalFormat("0");
	
	// Issues Bug Abertos; Issues Bug Fechados; Issues Encerrados em Commit;
	//Issues Bug encerrados em commit; % Issues fechados em commit; % issues bug fechados em commit (X bug); % issues bug fechados em commit (X Total) 
	buffer.append(totalOpenIssue + ";" + totalClosedIssue+ ";");
	buffer.append(totalOpenIssueBug + ";" + totalClosedIssueBug + ";");
	buffer.append(totalContadorIssuesCorrigidosCommits+ ";");
	buffer.append(totalContadorIssuesBugCorrigidosCommits + ";");
	buffer.append(format.format(totalPorcentualIssuesFechadosCommit) + "%" +";");		
	buffer.append(format.format(totalPorcentualIssuesBugFechadosCommit) + "%" + ";");
	buffer.append(format.format(totalPorcentualIssuesBugFechadosCommitTotal) + "%");
	buffer.append(System.getProperty("line.separator"));
			
	 
	escreveArquivo(nomeArquivoAnaliseCSVConsolidado, buffer); 
	
}
	
	public static void printConteudoTodosRepositorios(String nome,  int totalOpenIssue, int totalClosedIssue, int totalOpenIssueBug, int totalClosedIssueBug, int totalContadorIssuesCorrigidosCommits, int totalContadorIssuesBugCorrigidosCommits, double totalPorcentualIssuesFechadosCommit, double totalPorcentualIssuesBugFechadosCommit, double totalPorcentualIssuesBugFechadosCommitTotal) throws IOException{
		StringBuilder buffer = new StringBuilder();
		
		//Informa formato de impressão de Double
		DecimalFormat format = new DecimalFormat("0");
		
		buffer.append("____________________||||(Todos os Repositórios)||||______________________" + System.getProperty("line.separator"));
		buffer.append("Listas de Issues" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + totalOpenIssue + " Fechados: " + totalClosedIssue + System.getProperty("line.separator"));
		buffer.append("Listas de Issues (Marcados como Bug)" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + totalOpenIssueBug + " Fechados: " + totalClosedIssueBug + System.getProperty("line.separator"));
		buffer.append("Issues Encerrados em um Commit: " + totalContadorIssuesCorrigidosCommits + System.getProperty("line.separator"));
		buffer.append("Issues Bug Encerrados em um Commit: " + totalContadorIssuesBugCorrigidosCommits + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues em um Commit : " + format.format(totalPorcentualIssuesFechadosCommit) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Relação aos Bugs): " + format.format(totalPorcentualIssuesBugFechadosCommit) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Relação a todos os Issues): " + format.format(totalPorcentualIssuesBugFechadosCommitTotal) + "%" + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));
		 
		escreveArquivo(nome, buffer); 
		
	}
	
	public static void printAnaliseMarcacaoIssue(Repositorio repositorio, int cont) throws IOException{
		StringBuilder buffer = new StringBuilder();

		buffer.append("________________||||Repositório: " + repositorio.getRepositoryName() + " Cont: " + cont+ "||||_________________________" + System.getProperty("line.separator"));
		
		if(repositorio.getMarcacaoIssue() != null){
			
			buffer.append("Listas de Labels:" + System.getProperty("line.separator"));
			for(MarcacaoIssue m : repositorio.getMarcacaoIssue()){
				if(m.getTipo().equals(TipoMarcacao.LABEL)){
					buffer.append("Tipo Marcação" + m.getTipo() + "Nome Label: " + m.getNome() + " ; Quantidade: " + m.getQuantidade() + System.getProperty("line.separator"));
				}
			}
			
			buffer.append("Listas de Milestones:" + System.getProperty("line.separator"));
			for(MarcacaoIssue m : repositorio.getMarcacaoIssue()){
				if(m.getTipo().equals(TipoMarcacao.MILESTONE)){
					buffer.append("Nome Milestone: " + m.getNome() + " ; Quantidade: " + m.getQuantidade() + System.getProperty("line.separator"));
				}
			}	
		} else { 
			buffer.append("Não existe nenhuma marcação nesse repositório" + System.getProperty("line.separator"));
		}
		
		escreveArquivo(nomeArquivoMarcacaoAnalitico, buffer); 
		
	}
	
	public static void printAnaliseMarcacaoCompleta(ArrayList<MarcacaoIssue> marcacao) throws IOException{
		StringBuilder buffer = new StringBuilder();
			
		for(MarcacaoIssue m : marcacao){
			if(m.getTipo().equals(TipoMarcacao.LABEL)){
				buffer.append(m.getTipo() + ";" + m.getNome() + ";" + m.getQuantidade() + System.getProperty("line.separator"));
			}
		}
		
		buffer.append("Listas de Milestones:" + System.getProperty("line.separator"));
		for(MarcacaoIssue m : marcacao){
			if(m.getTipo().equals(TipoMarcacao.MILESTONE)){
				buffer.append(m.getNome() + ";" + m.getQuantidade() + System.getProperty("line.separator"));
			}
		}
		sobreescreveArquivo(nomeArquivoMarcacaoConsolidado, buffer); 
		
	}
	
public static void printRepositórios(StringBuilder buffer, Repositorio repositorio){
		
		//Informa Dados De Repositório
		buffer.append("USER NAME: " + repositorio.getUserName() + "     REPOSITORY NAME: " + repositorio.getRepositoryName() + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));
		
	}

public static void armazenaCommits(String userName, String repositoryName,
		String sha, String conteudo) throws IOException {
	StringBuilder buffer = new StringBuilder();
	String nomePasta = userName+"-"+repositoryName;
	
	String caminhoArmazenamento = "C://Users//Casimiro//Documents//Casimiro Conde//Aulas//Mestrado//Seminário de Acompanhamento Discente 2//Territorialidade//Commits//"+nomePasta+"//"+"Commit-"+sha+".txt";
	
	buffer.append(conteudo);
	sobreescreveArquivo(caminhoArmazenamento, buffer);
	
}

public static void printContributors(Repositorio repositorio) throws IOException{
	StringBuilder buffer = new StringBuilder();
	for(Contributors c : repositorio.getContributorsAjustado()){
		buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";");
		buffer.append(c.getLogin() + ";");
		buffer.append(c.getNome() + ";");
		buffer.append(c.getType() + ";");
		buffer.append(c.getTipoAjustado() + ";");
		buffer.append(c.isDeveloper() + ";");
		buffer.append(c.isReporter() + ";");
		buffer.append(c.getDataPrimeiraInteração() + System.getProperty("line.separator"));
	}
	
	escreveArquivo(nomeArquivoContributors, buffer);
	
}

}
