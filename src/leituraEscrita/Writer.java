package leituraEscrita;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.UserService;

import Consolidado.ColaboratorConsolidado;
import Contributors.Contributors;
import issuesRepositorios.Repositorio;
import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.MarcacaoIssue;
import marcacoesIssues.TipoMarcacao;




public class Writer {

	
	static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
	static Calendar cal = Calendar.getInstance();

	static String nomeArquivoAnalise =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseRepo/saida_" + dateFormat.format(cal.getTime())+ ".txt";
	static String nomeArquivoAnaliseCSV =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseRepo/saida_" + dateFormat.format(cal.getTime())+ ".csv";
	static String nomeArquivoAnaliseCSVConsolidado =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseRepoConsolidado/saida_" + dateFormat.format(cal.getTime())+ "_Consolidado"+ ".csv";
	static String nomeArquivoMarcacaoAnalitico =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseMarcacao/Analise_Marcacao_" + dateFormat.format(cal.getTime())+ ".txt";
	static String nomeArquivoMarcacaoConsolidado =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseMarcacao/Consolidado/Analise_Marcacao_Consolidado_"+ dateFormat.format(cal.getTime()) +".txt";
	static String nomeArquivoListaIssue =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaIssue/listaIssue"+ dateFormat.format(cal.getTime()) +".txt";
	static String nomeArquivoContributors =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaContributors/listaContributors"+ dateFormat.format(cal.getTime()) +".txt";
	static String nomeArquivoMarcacaoPorIssueLabel =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseMarcacao/PorIssue/Analise_Marcacao_Por_Issue_Label"+ dateFormat.format(cal.getTime()) +".txt";
	static String nomeArquivoMarcacaoPorIssueMilestone =  "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseMarcacao/PorIssue/Analise_Marcacao_Por_Issue_Milestone"+ dateFormat.format(cal.getTime()) +".txt";
	static String nomeArquivoDownloadCommitLoc = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/downloadCommitIssue/scriptDownload.txt";
	
	static  String LABELSFINAL = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/ConsolidadoPosAnalise/Labels/Agrupadores/listaLabelsFinal_teste.txt";
	static  String COLABORATORSFINAL = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/ConsolidadoPosAnalise/Contributors/Consolidado/listaContributosConsolidado_teste.txt";
	
	
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
		//Informa formato de impress�o de Double
		DecimalFormat format = new DecimalFormat("0");


		buffer.append("________________||||Reposit�rio: " + repositorio.getRepositoryName() + " Cont: " + cont+ "||||_________________________" + System.getProperty("line.separator"));
		buffer.append("Usuario: " + repositorio.getUserName() + " Reposit�rio: " + repositorio.getRepositoryName() + System.getProperty("line.separator"));
		buffer.append("Seguidores do Owner: " + repositorio.getNumeroFollowersOwner() + "Seguidos pelo Owner:" + repositorio.getNumeroFollowingOwner() + System.getProperty("line.separator"));
		buffer.append("Forks:" + repositorio.getNumeroForks() + "Watchers" + repositorio.getNumeroWatchers() + System.getProperty("line.separator"));
		buffer.append("Tamanho (KiloBytes)" + repositorio.getTamanhoRepositorio() + System.getProperty("line.separator"));
		buffer.append("Listas de Issues dentro do Reposit�rio" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + repositorio.getOpenIssue() + " Fechados: " + repositorio.getClosedIssue() + System.getProperty("line.separator"));
		buffer.append("Listas de Issues dentro do Reposit�rio (Marcados como Bug)" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + repositorio.getOpenIssueBug() + " Fechados: " + repositorio.getClosedIssueBug()+ System.getProperty("line.separator"));
		buffer.append("Issues Encerrados em um Commit: " + repositorio.getContadorIssuesCorrigidosCommits()+ System.getProperty("line.separator"));
		buffer.append("Issues Bug Encerrados em um Commit: " + repositorio.getContadorIssuesBugCorrigidosCommits()+ System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues em um Commit: " + format.format(repositorio.getPorcentualIssuesFechadosCommit()) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Rela��o aos Bugs): " + format.format(repositorio.getPorcentualIssuesBugFechadosCommit()) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Rela��o a todos os Issues): " + format.format(repositorio.getPorcentualIssuesBugFechadosCommitTotal()) + "%" + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));

		escreveArquivo(nome, buffer);

	}

	public static void printConteudoCSV(Repositorio repositorio, int cont) throws IOException{

		StringBuilder buffer = new StringBuilder();
		//Informa formato de impress�o de Double
		DecimalFormat format = new DecimalFormat("0");

		// User name; Repository Name; Issues Abertos; Issues Fechados; Issues Bug Abertos; Issues Bug Fechados; Issues Encerrados em Commit;
		//Issues Bug encerrados em commit; % Issues fechados em commit; % issues bug fechados em commit (X bug); % issues bug fechados em commit (X Total)

		buffer.append(cont + ";");
		buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";" + repositorio.getUser() + ";" + repositorio.getIdUnico() + ";");
		buffer.append(repositorio.getCreate() + ";" + repositorio.getIdadeDia() + ";" + repositorio.getIdadeDiaAjustado() + ";");
		buffer.append(repositorio.getIdadeSemana() + ";"+ repositorio.getIdadeSemanaAjustado() + ";"+ repositorio.getNumeroContributors() + ";"+ repositorio.getNumeroContributorsAjustado() + ";");
		buffer.append(repositorio.getLanguage() + ";" + repositorio.isHasIssue() + ";" + repositorio.getContemIssue() + ";");
		buffer.append(repositorio.getNumeroIssues()+ ";" + repositorio.getNumeroIssuesAjustado() +";");
		buffer.append(repositorio.getOpenIssue() + ";" + repositorio.getOpenIssueAjustado() + ";" + repositorio.getClosedIssue() + ";" + repositorio.getClosedIssueAjustado() + ";");
		buffer.append(repositorio.getOpenIssueBug() + ";" + repositorio.getOpenIssueBugAjustado() + ";" + repositorio.getClosedIssueBug() + ";" + repositorio.getClosedIssueBugAjustado() +";");
		buffer.append(repositorio.getNumeroFollowersOwner() + ";" + repositorio.getNumeroFollowersOwnerAjustado() + ";" + repositorio.getNumeroFollowingOwner() + ";" + repositorio.getNumeroFollowingOwnerAjustado() + ";");
		buffer.append(repositorio.getNumeroForks() + ";" + repositorio.getNumeroForksAjustado() + ";" + repositorio.getNumeroWatchers() +";" + repositorio.getNumeroWatchersAjustado() + ";");
		buffer.append(repositorio.getTamanhoRepositorio() + ";" + repositorio.getTamanhoRepositorioAjustado() + ";");
		buffer.append(repositorio.getContadorIssuesCorrigidosCommits()+ ";" + repositorio.getContadorIssuesCorrigidosCommitsAjustado() + ";");
		buffer.append(repositorio.getContadorIssuesBugCorrigidosCommits() + ";" + repositorio.getContadorIssuesBugCorrigidosCommitsAjustado() + ";");
		buffer.append(format.format(repositorio.getPorcentualIssuesFechadosCommit()) + "%" +";");
		buffer.append(format.format(repositorio.getPorcentualIssuesBugFechadosCommit()) + "%" + ";");
		buffer.append(format.format(repositorio.getPorcentualIssuesBugFechadosCommitTotal()) + "%" + ";");
		buffer.append(repositorio.getNumeroDevTrueRepTrue() + ";" + repositorio.getNumeroDevTrueRepTrueAjustado() + ";");
		buffer.append(repositorio.getNumeroDevTrueRepFalse() + ";" + repositorio.getNumeroDevTrueRepFalseAjustado() + ";");
		buffer.append(repositorio.getNumeroDevFalseRepTrue() + ";" + repositorio.getNumeroDevFalseRepTrueAjustado() + ";");
		buffer.append(repositorio.getNumeroDevTrue() + ";" + repositorio.getNumeroDevTrueAjustado() + ";");
		buffer.append(repositorio.getNumeroRepTrue() + ";" + repositorio.getNumeroRepTrueAjustado() + ";");
		buffer.append(repositorio.getSomaTimeToFix() + ";" + repositorio.getSomaTimeToFixAjustado() + ";");
		buffer.append(repositorio.getMediaTimeToFix() + ";" + repositorio.getMediaTimeToFixAjustado() + ";" + repositorio.isFork() + ";" + repositorio.getRepositorioPai() + ";");
		buffer.append(System.getProperty("line.separator"));

		escreveArquivo(nomeArquivoAnaliseCSV, buffer);

	}

	public static void printArquivoDownloadCommitLoc(String userName, String repoName) throws IOException{
		StringBuilder buffer = new StringBuilder();

		buffer.append("git clone git://github.com/" + userName + "/" + repoName + ".git");
		buffer.append(" C:\\Users\\MARCIO.BARROS\\Documents\\Clone\\" + userName + "-" + repoName + System.getProperty("line.separator"));
		buffer.append("cd C:\\Users\\MARCIO.BARROS\\Documents\\Clone\\" + userName + "-" + repoName + System.getProperty("line.separator"));
		buffer.append("mkdir C:\\Users\\MARCIO.BARROS\\Documents\\Saidas\\" + userName + "-" + repoName + System.getProperty("line.separator"));
		buffer.append("git log --pretty=fuller > C:\\Users\\MARCIO.BARROS\\Documents\\Saidas\\" + userName + "-" + repoName +"\\commits.txt" + System.getProperty("line.separator"));
		buffer.append("C:\\Users\\MARCIO.BARROS\\Documents\\cloc C:\\Users\\MARCIO.BARROS\\Documents\\Clone\\" + userName + "-" + repoName);
		buffer.append(" --by-file-by-lang > C:\\Users\\MARCIO.BARROS\\Documents\\Saidas\\" + userName + "-" + repoName + "\\loc.txt" + System.getProperty("line.separator"));
		buffer.append("cd.." + System.getProperty("line.separator"));
		buffer.append("rmdir /S /Q C:\\Users\\MARCIO.BARROS\\Documents\\Clone\\" + userName + "-" + repoName + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));

		escreveArquivo(nomeArquivoDownloadCommitLoc, buffer);

	}

	public static void printHasIssueCSV(Repositorio repositorio, int cont) throws IOException{

		StringBuilder buffer = new StringBuilder();
		//Informa formato de impress�o de Double
		DecimalFormat format = new DecimalFormat("0");

		// User name; Repository Name; Issues Abertos; Issues Fechados; Issues Bug Abertos; Issues Bug Fechados; Issues Encerrados em Commit;
		//Issues Bug encerrados em commit; % Issues fechados em commit; % issues bug fechados em commit (X bug); % issues bug fechados em commit (X Total)

		buffer.append(cont + ";");
		buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";" + repositorio.isHasIssue() + ";");
		buffer.append(System.getProperty("line.separator"));

		escreveArquivo(nomeArquivoAnaliseCSV, buffer);

	}

	public static void printConteudoRepositorioIssuesCSV(Repositorio repositorio, int cont, GitHubClient client) throws IOException, InterruptedException{

		StringBuilder buffer = new StringBuilder();
		//Informa formato de impress�o de Double
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
							buffer.append(i.getNumber() + ";");
							buffer.append(user.getName() + ";");
							buffer.append(i.getCreatedAt()+ ";");
							buffer.append(i.getClosedAt()+ ";");
							buffer.append(i.getTitle().replace(";","- ").replace("\r\n", "- ").replace("\r", "- ") + ";");
							buffer.append(i.getComments() + ";");
							if(i.getBody() != null){
							buffer.append(i.getBody().replace(";","- ").replace("\n", "- ").replace("\r", "- ")  + ";");
							} else{
								buffer.append(";");
							}
							buffer.append(i.getUpdatedAt() + ";");
							buffer.append(repositorio.calculaTimeToFixIssue(i) + ";");
							buffer.append(i.getLabels().size());
							buffer.append(System.getProperty("line.separator"));
							printMarcacoesPorIssueLabel(repositorio.getUserName(), repositorio.getRepositoryName(), i, nomeArquivoMarcacaoPorIssueLabel);
							printMarcacoesPorIssueMilestone(repositorio.getUserName(), repositorio.getRepositoryName(), i, nomeArquivoMarcacaoPorIssueMilestone);
							finished = true;
						}catch(RequestException e) {
							if(e.getStatus() == 403){
								System.out.println("Imprimindo issue!! Reposit�rio: " + repositorio.getRepositoryName());
								Thread.sleep(600 * 1000);
							}else{
								System.out.println("Excess�o imprimindo issue Encerrado!! Reposit�rio: " + repositorio.getRepositoryName());
								finished= true;
							}
						}
					}


		}
		escreveArquivo(nomeArquivoListaIssue, buffer);

	}



	private static void printMarcacoesPorIssueMilestone(String userName, String repositoryName, Issue i,
			String nomeArquivoMarcacaoPorIssueMilestone2) throws IOException {
		StringBuilder buffer = new StringBuilder();
		if(i.getMilestone() != null){
			buffer.append(userName + ";");
			buffer.append(repositoryName + ";");
			buffer.append(i.getId() + ";");
			buffer.append(i.getNumber() + ";");
			buffer.append("MILESTONE;");
			buffer.append(i.getMilestone().getNumber() + ";");
			buffer.append(i.getMilestone().getDescription() + ";");
			buffer.append(i.getMilestone().getOpenIssues() + ";");
			buffer.append(i.getMilestone().getClosedIssues() + ";");
			buffer.append(i.getMilestone().getDueOn() + ";");
			buffer.append(i.getMilestone().getState() + ";");
			buffer.append(i.getMilestone().getTitle() + ";");
			buffer.append(i.getMilestone().getCreatedAt() + ";");
			buffer.append(i.getMilestone().getCreator() + ";");
			buffer.append(System.getProperty("line.separator"));

			escreveArquivo(nomeArquivoMarcacaoPorIssueMilestone2, buffer);
		}
	}

	private static void printMarcacoesPorIssueLabel(String userName, String repositoryName, Issue i,
			String nomeArquivoMarcacaoPorIssue2) throws IOException {
		StringBuilder buffer = new StringBuilder();

		for(Label l : i.getLabels()){
			buffer.append(userName + ";");
			buffer.append(repositoryName + ";");
			buffer.append(i.getId() + ";");
			buffer.append(i.getNumber() + ";");
			buffer.append("LABEL;");
			buffer.append(l.getName() + ";");
			buffer.append(l.getColor() + ";");
			buffer.append(System.getProperty("line.separator"));
		}
		escreveArquivo(nomeArquivoMarcacaoPorIssue2, buffer);
	}

	public static void printConteudoTodosRepositoriosCSV(int totalOpenIssue, int totalClosedIssue, int totalOpenIssueBug, int totalClosedIssueBug, int totalContadorIssuesCorrigidosCommits, int totalContadorIssuesBugCorrigidosCommits, double totalPorcentualIssuesFechadosCommit, double totalPorcentualIssuesBugFechadosCommit, double totalPorcentualIssuesBugFechadosCommitTotal) throws IOException{
		StringBuilder buffer = new StringBuilder();

		//Informa formato de impress�o de Double
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

		//Informa formato de impress�o de Double
		DecimalFormat format = new DecimalFormat("0");

		buffer.append("____________________||||(Todos os Reposit�rios)||||______________________" + System.getProperty("line.separator"));
		buffer.append("Listas de Issues" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + totalOpenIssue + " Fechados: " + totalClosedIssue + System.getProperty("line.separator"));
		buffer.append("Listas de Issues (Marcados como Bug)" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + totalOpenIssueBug + " Fechados: " + totalClosedIssueBug + System.getProperty("line.separator"));
		buffer.append("Issues Encerrados em um Commit: " + totalContadorIssuesCorrigidosCommits + System.getProperty("line.separator"));
		buffer.append("Issues Bug Encerrados em um Commit: " + totalContadorIssuesBugCorrigidosCommits + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues em um Commit : " + format.format(totalPorcentualIssuesFechadosCommit) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Rela��o aos Bugs): " + format.format(totalPorcentualIssuesBugFechadosCommit) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues Bug em um Commit (Em Rela��o a todos os Issues): " + format.format(totalPorcentualIssuesBugFechadosCommitTotal) + "%" + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));

		escreveArquivo(nome, buffer);

	}

	public static void printAnaliseMarcacaoIssuePorProjeto(Repositorio repositorio, int cont) throws IOException{
		
		if(!repositorio.getMarcacaoIssue().isEmpty()){
			StringBuilder buffer = new StringBuilder();
			for(MarcacaoIssue m : repositorio.getMarcacaoIssue() ){
				buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";" + repositorio.getIdUnico() + ";");
				buffer.append(m.getTipo() + ";" + m.getNome()+ ";" + m.getQuantidade() + ";");
				buffer.append(m.getTimeToFix() + ";" + m.calculaMediaTimeToFix()+ ";" + System.getProperty("line.separator"));
			}
	
			escreveArquivo(nomeArquivoMarcacaoAnalitico, buffer);
		}else{
			return;
		}

	}

	public static void printAnaliseMarcacaoCompleta(ArrayList<MarcacaoIssue> marcacao) throws IOException{
		StringBuilder buffer = new StringBuilder();

		for(MarcacaoIssue m : marcacao){
			if(m.getTipo().equals(TipoMarcacao.LABEL)){
				buffer.append(m.getTipo() + ";" + m.getNome() + ";" + m.getQuantidade() + ";" + m.getTimeToFix() +";" + m.calculaMediaTimeToFix()+  System.getProperty("line.separator"));
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

	public static void printRepositorios(StringBuilder buffer, Repositorio repositorio, int cont){

			//Informa Dados De Reposit�rio
			buffer.append(cont + ";" + repositorio.getUserName() + ";" + repositorio.getRepositoryName());
			buffer.append(System.getProperty("line.separator"));
			

	}
	
	public static void geraBufferRepositorio(StringBuilder buffer, long id, String login, String name, int cont) {
		buffer.append(cont + ";" + id + ";" + login + ";" + name);
		
	}

	public static void armazenaCommits(String userName, String repositoryName,
			String sha, String conteudo) throws IOException {
		StringBuilder buffer = new StringBuilder();
		String nomePasta = userName+"-"+repositoryName;

		String caminhoArmazenamento = "C://Users//Casimiro//Documents//Casimiro Conde//Aulas//Mestrado//Semin�rio de Acompanhamento Discente 2//Territorialidade//Commits//"+nomePasta+"//"+"Commit-"+sha+".txt";

		buffer.append(conteudo);
		sobreescreveArquivo(caminhoArmazenamento, buffer);

	}

	public static void printContributors(Repositorio repositorio) throws IOException{
		StringBuilder buffer = new StringBuilder();
		for(Contributors c : repositorio.getContributorsAjustado()){
			buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";");
			buffer.append(c.getLogin() + ";");
			buffer.append(c.getNome() + ";");
			buffer.append(c.getEmail() + ";");
			buffer.append(c.getTipoAjustado() + ";");
			buffer.append(c.isDeveloper() + ";");
			buffer.append(c.isReporter() + ";");
			buffer.append(c.getFollowers() + ";");
			buffer.append(c.getFollowing() + ";");
			buffer.append(c.getIssuesAbertos() + ";");
			buffer.append(c.getIssuesFechados() + ";");
			buffer.append(c.getIssuesFechadosPorCommit() + ";");
			buffer.append(c.getIssuesFechadosPorCommitContinhamLabel() + ";");
			buffer.append(c.getOwnedPrivateRepos() + ";");
			buffer.append(c.getPublicRepors() + ";");
			buffer.append(c.getTempoCriacao() + ";");
			buffer.append(c.getTotalPrivateRepos() + ";");
			buffer.append(c.getCommitsRegistrados() + ";");
			buffer.append(c.getDataPrimeiraInteracao() + System.getProperty("line.separator"));
		}

		escreveArquivo(nomeArquivoContributors, buffer);

	}

	public static void arquivoConsolidado(String arquivoConsolidado, String linha) throws IOException {
		StringBuilder buffer = new StringBuilder();

		buffer.append(linha);
		buffer.append(System.getProperty("line.separator"));

		escreveArquivo(arquivoConsolidado, buffer);
	}

	public static void printMarcacoesConsolidadasComInfoCommit(ArrayList<LabelConsolidado> listaLabels) throws IOException {
		StringBuilder buffer = new StringBuilder();
		for(LabelConsolidado l : listaLabels){
			buffer.append(l.getTipo() + ";");
			buffer.append(l.getLabelNormatizada()+ ";");
			buffer.append(l.getContador() + ";");
			buffer.append(l.getContadorFechadoPorCommit() + ";");
			buffer.append(System.getProperty("line.separator"));
		}

		sobreescreveArquivo(LABELSFINAL, buffer);
		
	}

	public static void printColaboratorConsolidadoTodosRepos(ArrayList<ColaboratorConsolidado> colaboratorsConsolidadoFinal) throws IOException {
		StringBuilder buffer = new StringBuilder();
		for(ColaboratorConsolidado c : colaboratorsConsolidadoFinal){
			buffer.append(c.getNome() + ";");
			buffer.append(c.getLogin() + ";");
			buffer.append(c.getEmail() + ";");
			buffer.append(c.getTipoAjustado() + ";");
			buffer.append(c.isDeveloper() + ";");
			buffer.append(c.isReporter() + ";");
			buffer.append(c.getFollowers() + ";");
			buffer.append(c.getFollowing() + ";");
			buffer.append(c.getCommitsRegistrados() + ";");
			buffer.append(c.getIssuesAbertos() + ";");
			buffer.append(c.getIssuesFechados() + ";");
			buffer.append(c.getTentativaIssueFechadoPorCommit() + ";");
			buffer.append(c.getIssuesFechadosPorCommit() + ";");
			buffer.append(c.getIssuesFechadosPorCommitComLabels() + ";");
			buffer.append(c.getOwnedPrivateRepos() + ";");
			buffer.append(c.getPublicRepos() + ";");
			buffer.append(c.getTempoCriado() + ";");
			buffer.append(c.getTotalPrivateRepos() + ";");
			buffer.append(System.getProperty("line.separator"));
		}

		sobreescreveArquivo(COLABORATORSFINAL, buffer);
	}

}
