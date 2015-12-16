package leituraEscrita;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConsolidadorDeArquivos {

	final static String PASTA = "C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utiliza��o R/futuro/arquivos originais/Marca��o/Maquina Facul";
	final static String CONSOLIDADO = "C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utiliza��o R/futuro/Consolidado/MarcacaoConsolidado.txt";
	
	public static void consolidador() throws IOException{
		
	File pasta = new File (PASTA);
	File [] files = pasta.listFiles();
	StringBuilder buffer = new StringBuilder();
	
	if(files != null){
		for (File f : files){
			if(f.isFile()){
				Scanner content = new Scanner(new BufferedReader(new FileReader(f)));
				while(content.hasNext()){
					buffer.append(content.nextLine() + System.getProperty("line.separator"));
				}
			}
	}
		Writer.escreveArquivo(CONSOLIDADO, buffer);
		
	}
}
	
	
	public static void consolidaLocDeArquivoOrigem(String pasta, String arquivoOrigem, String arquivoConsolidado) throws IOException{
		Scanner file = new Scanner(new BufferedReader(new FileReader(arquivoOrigem)));
		String linhaCabecalho = file.nextLine();
		
		while(file.hasNext()){
			String linha = file.nextLine();
			String id = retornaId(linha);
			String loc = retornaLoc(pasta, id);
			
			linha = linha + ";" + loc;
			
			Writer.arquivoConsolidado(arquivoConsolidado, linha);
			
		}
		
	}
	
	private static String retornaId(String linha) {
		String [] tokens = linha.split(";");
		return tokens[3];		
	}


	public static String retornaLoc (String pasta, String id){
		String pastaOrigem = pasta;
		File pastaGeral = new File (pastaOrigem);
		File [] pastas = pastaGeral.listFiles();
		for(File p : pastas){
			if(p != null){
				if(verificaId(id, p)){
					File [] files = p.listFiles();
					for(File f : files){
						if(f.getName().contains("loc.txt")){
						return Reader.retiraLoc(f);
						}
					}
				}
			}
		}
		return "NA";
		
	}

	private static boolean verificaId(String id, File file) {
		String nomePasta = retornaNomePasta(file);
		
		if(nomePasta.equals(id))
			return true;
		return false;
	}


	private static String retornaNomePasta(File file) {
		int token = file.getName().indexOf("-"); 
		String user = file.getName().substring(0, token);
		String repository = file.getName().substring(token+1);
		return user + repository;
	}
}