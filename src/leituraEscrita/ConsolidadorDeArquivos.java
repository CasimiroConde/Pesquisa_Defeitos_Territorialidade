package leituraEscrita;

import java.io.BufferedReader;
import java.io.File;
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
	
	public static void consolidaLoc (String pasta){
		String pastaOrigem = pasta;
		File pastaGeral = new File (pastaOrigem);
		File [] pastas = pastaGeral.listFiles();
		for(File p : pastas){
			if(p != null){
				File [] files = p.listFiles();
				for(File f : files){
					if(f.getName().contains("loc.txt")){
						int loc = Reader.retiraLoc(f);
						incluiLocArquivoConsolidado(loc);
					}
				}
			}
		}
		
	}

	private static void incluiLocArquivoConsolidado(int loc) {
		// TODO Auto-generated method stub	
	}
}