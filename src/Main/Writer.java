package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Writer {
	
	
	public static void criaArquivo(String nome, StringBuilder buffer) throws IOException{
		File arquivo = new File(nome);
		FileWriter fw = new FileWriter(arquivo);
		BufferedWriter bw = new BufferedWriter( fw );
		bw.write(buffer.toString());
		bw.close();
	}
	
	public static void printConteudo(StringBuilder buffer, Repositorio repositorio){
		
		//Informa formato de impressão de Double
		DecimalFormat format = new DecimalFormat("0");
			    
		
		buffer.append("________________||||Repositório: " + repositorio.getRepositoryName() + "||||_________________________" + System.getProperty("line.separator"));
		buffer.append("Usuario: " + repositorio.getUserName() + " Repositório: " + repositorio.getRepositoryName() + System.getProperty("line.separator"));
		buffer.append("Listas de Issues dentro do Repositório" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + repositorio.getOpenIssue() + " Fechados: " + repositorio.getClosedIssue() + System.getProperty("line.separator"));
		buffer.append("Listas de Issues dentro do Repositório (Marcados como Bug)" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + repositorio.getOpenIssueBug() + " Fechados: " + repositorio.getClosedIssueBug()+ System.getProperty("line.separator"));
		buffer.append("Issues Encerrados em um Commit: " + repositorio.getContadorIssuesCorrigidosCommits()+ System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues em um Commit: " + format.format(repositorio.getPorcentualIssuesFechadosCommit()) + "%" + System.getProperty("line.separator"));		
		buffer.append("Porcentual fechado de Issues (Bug): " + format.format(repositorio.getPorcentualIssuesBugFechadosCommit()) + "%");
		buffer.append(System.getProperty("line.separator"));
	}
	
	public static void printConteudoTodosRepositorios(StringBuilder buffer, int totalOpenIssue, int totalClosedIssue, int totalOpenIssueBug, int totalClosedIssueBug, int totalContadorDefeitosCorrigidosCommits, double totalPorcentualIssuesFechadosCommit, double totalPorcentualIssuesBugFechadosCommit){
		
		//Informa formato de impressão de Double
		DecimalFormat format = new DecimalFormat("0");
		
		buffer.append("____________________||||(Todos os Repositórios)||||______________________" + System.getProperty("line.separator"));
		buffer.append("Listas de Issues" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + totalOpenIssue + " Fechados: " + totalClosedIssue + System.getProperty("line.separator"));
		buffer.append("Listas de Issues (Marcados como Bug)" + System.getProperty("line.separator"));
		buffer.append("Abertos: " + totalOpenIssueBug + " Fechados: " + totalClosedIssueBug + System.getProperty("line.separator"));
		buffer.append("Issues Encerrados em um Commit: " + totalContadorDefeitosCorrigidosCommits + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues em um Commit: " + format.format(totalPorcentualIssuesFechadosCommit) + "%" + System.getProperty("line.separator"));
		buffer.append("Porcentual fechado de Issues (Bug): " + format.format(totalPorcentualIssuesBugFechadosCommit) + "%");
		buffer.append(System.getProperty("line.separator"));
		
	}

}
