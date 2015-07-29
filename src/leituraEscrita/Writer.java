package leituraEscrita;

import issuesRepositorios.MarcacaoIssue;
import issuesRepositorios.Repositorio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import marcacoesIssues.TipoMarcacao;

import org.eclipse.egit.github.core.RepositoryCommit;

public class Writer {
	
		
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
	
public static void printConteudoCSV(String nome, Repositorio repositorio, int cont) throws IOException{
		
		StringBuilder buffer = new StringBuilder();
		//Informa formato de impressão de Double
		DecimalFormat format = new DecimalFormat("0");
			    
		// User name; Repository Name; Issues Abertos; Issues Fechados; Issues Bug Abertos; Issues Bug Fechados; Issues Encerrados em Commit;
		//Issues Bug encerrados em commit; % Issues fechados em commit; % issues bug fechados em commit (X bug); % issues bug fechados em commit (X Total) 
		
		buffer.append(cont + ";");
		buffer.append(repositorio.getUserName() + ";" + repositorio.getRepositoryName() + ";");
		buffer.append(repositorio.getOpenIssue() + ";" + repositorio.getClosedIssue() + ";");
		buffer.append(repositorio.getOpenIssueBug() + ";" + repositorio.getClosedIssueBug() + ";");
		buffer.append(repositorio.getContadorIssuesCorrigidosCommits()+ ";");
		buffer.append(repositorio.getContadorIssuesBugCorrigidosCommits() + ";");
		buffer.append(format.format(repositorio.getPorcentualIssuesFechadosCommit()) + "%" +";");		
		buffer.append(format.format(repositorio.getPorcentualIssuesBugFechadosCommit()) + "%" + ";");
		buffer.append(format.format(repositorio.getPorcentualIssuesBugFechadosCommitTotal()) + "%");
		buffer.append(System.getProperty("line.separator"));
		
		escreveArquivo(nome, buffer);
		
	}

public static void printConteudoTodosRepositoriosCSV(String nome,  int totalOpenIssue, int totalClosedIssue, int totalOpenIssueBug, int totalClosedIssueBug, int totalContadorIssuesCorrigidosCommits, int totalContadorIssuesBugCorrigidosCommits, double totalPorcentualIssuesFechadosCommit, double totalPorcentualIssuesBugFechadosCommit, double totalPorcentualIssuesBugFechadosCommitTotal) throws IOException{
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
			
	 
	escreveArquivo(nome, buffer); 
	
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
	
	public static void printAnaliseMarcacaoIssue(String nome, Repositorio repositorio, int cont) throws IOException{
		StringBuilder buffer = new StringBuilder();

		buffer.append("________________||||Repositório: " + repositorio.getRepositoryName() + " Cont: " + cont+ "||||_________________________" + System.getProperty("line.separator"));
		
		if(repositorio.getMarcacaoIssue() != null){
			
			buffer.append("Listas de Labels:" + System.getProperty("line.separator"));
			for(MarcacaoIssue m : repositorio.getMarcacaoIssue()){
				if(m.getTipo().equals(TipoMarcacao.LABEL)){
					buffer.append("Nome Label: " + m.getNome() + " ; Quantidade: " + m.getQuantidade() + System.getProperty("line.separator"));
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
		
		escreveArquivo(nome, buffer); 
		
	}
	
	public static void printAnaliseMarcacaoCompleta(String nome, ArrayList<MarcacaoIssue> marcacao) throws IOException{
		StringBuilder buffer = new StringBuilder();
			
		for(MarcacaoIssue m : marcacao){
			if(m.getTipo().equals(TipoMarcacao.LABEL)){
				buffer.append(m.getNome() + ";" + m.getQuantidade() + System.getProperty("line.separator"));
			}
		}
		
		buffer.append("Listas de Milestones:" + System.getProperty("line.separator"));
		for(MarcacaoIssue m : marcacao){
			if(m.getTipo().equals(TipoMarcacao.MILESTONE)){
				buffer.append(m.getNome() + ";" + m.getQuantidade() + System.getProperty("line.separator"));
			}
		}
		sobreescreveArquivo(nome, buffer); 
		
	}
	
public static void printRepositórios(StringBuilder buffer, Repositorio repositorio){
		
		//Informa Dados De Repositório
		buffer.append("USER NAME: " + repositorio.getUserName() + "     REPOSITORY NAME: " + repositorio.getRepositoryName() + System.getProperty("line.separator"));
		buffer.append(System.getProperty("line.separator"));
		
	}

public static void armazenaCommits(String userName, String repositoryName,
		RepositoryCommit c) throws IOException {
	StringBuilder buffer = new StringBuilder();
	String nomePasta = userName+"-"+repositoryName;
	
	String caminhoArmazenamento = "arquivos de saida//Commits//"+nomePasta+"//"+"Commit-"+c.getSha()+".txt";
	
	buffer.append(c.getCommit().getMessage());
	sobreescreveArquivo(caminhoArmazenamento, buffer);
	
}

}
