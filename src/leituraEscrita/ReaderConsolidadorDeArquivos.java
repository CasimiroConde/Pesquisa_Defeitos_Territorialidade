package leituraEscrita;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Consolidado.CommitsConsolidado;

public class ReaderConsolidadorDeArquivos {

	public static String retiraLoc(File f) {
		int contValue = 0;
		try {
			String texto = Reader.retornaConteudo(f);
			int index = texto.indexOf("SUM");
			if(index > 0){
				String [] linhaLoc = texto.substring(index).split("\\W");

				for(String l : linhaLoc){
					if(!l.equals("")){
						contValue++;
						if(contValue == 4){
							return l;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "NA";
	}

	public static ArrayList<CommitsConsolidado> AnalisaCommitsBuscaIssues(File f, String id) throws FileNotFoundException {

		Scanner file = new Scanner(new BufferedReader(new FileReader(f)));
		ArrayList<CommitsConsolidado> commits = new ArrayList<CommitsConsolidado>();
		String nome = "";
		String email = "";
		String texto = "";
		
		try{
			while(file.hasNext()){
				String linha = file.nextLine();
				
	
				if(linha.startsWith("Author:")){
					nome = linha.substring(linha.indexOf(" "), linha.indexOf("<")).trim();
					email = linha.substring(linha.indexOf("<") + 1, linha.indexOf(">")).trim();
				}
	
				if(linha.startsWith("CommitDate:")){
					linha = file.nextLine();
					while(!linha.startsWith("commit")){
						texto = texto + " " + linha.replace(";", "-");
						if(file.hasNext()){
							linha = file.nextLine();
						}else{
							break;
						}
					}
					CommitsConsolidado commit = new CommitsConsolidado(nome, email, texto);
					commits.add(commit);
				}
				texto ="";
			}
			return commits;
		}catch (NoSuchElementException e){
			CommitsConsolidado commit = new CommitsConsolidado(nome, email, texto);
			commits.add(commit);
			return commits;
		}
		
	}
}
