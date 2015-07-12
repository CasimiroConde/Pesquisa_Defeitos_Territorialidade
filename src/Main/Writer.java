package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Writer {
	
		
	public static void criaArquivo(String nome, StringBuilder buffer) throws IOException{
		File arquivo = new File(nome);
		FileWriter fw = new FileWriter(arquivo, true);
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
		
		criaArquivo(nome, buffer);
		
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
		 
		criaArquivo(nome, buffer); 
		
	}
	
public static void printRepositórios(StringBuilder buffer, Repositorio repositorio){
		
		//Informa Dados De Repositório
		buffer.append("USER NAME: " + repositorio.getUserName() + "     REPOSITORY NAME: " + repositorio.getRepositoryName() + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));
		
	}

}
