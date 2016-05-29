package leituraEscrita;

import issuesRepositorios.MetodosAuxiliares;
import marcacoesIssues.LabelConsolidado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.egit.github.core.Issue;

import consolidadoWriter.ColaboratorConsolidadoWriter;
import consolidadoWriter.ListaColaboratorConsolidado;
import consolidadoWriter.RepositorioConsolidadoWriter;
import Consolidado.ColaboratorConsolidado;
import Consolidado.CommitsConsolidado;
import Consolidado.IssueConsolidado;
import Consolidado.LabelsIssueConsolidado;
import Consolidado.RepositorioConsolidado;
import Contributors.TipoContributor;

public class ConsolidadorDeArquivos {

	final static String PASTA = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaRepositorios/saidaRepositorios";
	final static String CONSOLIDADO = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaRepositoriossaidaRepositorios/repositories_Consolidado.txt";




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
			String [] tokens = linha.split(";");
			String id = retornaId(tokens, "relatorio");
			String loc = retornaLoc(pasta, id);

			linha = linha + loc;

			Writer.arquivoConsolidado(arquivoConsolidado, linha);

		}

	}

	public static void consolidaInfoCommitsDeArquivoOrigem(String pasta, String arquivoOrigem, String arquivoConsolidado, int INICIO, int TAMANHOAMOSTRA) throws IOException{
		Scanner file = new Scanner(new BufferedReader(new FileReader(arquivoOrigem)));

		String linhaCabecalho = file.nextLine();
		int cont = 0;
		ArrayList<LabelConsolidado> labelsConsolidadoFinal = Reader.geraListaConsolidadaLabels();
		ArrayList<ColaboratorConsolidado> colaboratorsConsolidadoFinal = new ArrayList<ColaboratorConsolidado>();
		
		while((file.hasNext()) && (cont < TAMANHOAMOSTRA)){
			String linha = file.nextLine();
			if(cont >= INICIO){
				String [] tokens = linha.split(";"); 
				String id = retornaId(tokens, "relatorio");
				
				//Carga das informações de: Colaboradores; Issues; Commits; Repositórios; Labels;
				ArrayList<CommitsConsolidado> commits =  carregaInfoCommits(pasta, id);
				RepositorioConsolidado repositorio = carregaInfoRepositorio(linha);
				
				repositorio.setCommits(commits);
				
				//Trata informações de colaboradores
				repositorio.IdentificaColaboratorsCommits();
				repositorio.IdentificaIssuesEmCommits();
				
				
				repositorio.caculaPercentuais();
				repositorio.calculaQuantidadeDevRep();
				repositorio.imprimirColaboratorConsolidado();
				repositorio.imprimirIssueConsolidado();
				repositorio.imprimirLabelConsolidado();
				
				calculaConsolidadoLabels(repositorio.getIssues(), labelsConsolidadoFinal);
				
				ConsolidadaColaborators(repositorio.getColaborators(), colaboratorsConsolidadoFinal);
				
				
				repositorio.imprimirRepositorioConsolidado();
				System.out.println(cont + "; analisado repositorio: " + id);
			}
			Writer.printColaboratorConsolidadoTodosRepos(colaboratorsConsolidadoFinal);
			Writer.printMarcacoesConsolidadasComInfoCommit(labelsConsolidadoFinal);
			cont++;
		}
		
		
		
	}





	private static void ConsolidadaColaborators(ArrayList<ColaboratorConsolidado> colaborators, ArrayList<ColaboratorConsolidado> colaboratorsConsolidadoFinal) {
		for(ColaboratorConsolidado cRepo : colaborators){
			Boolean cEncontrado = false;
			for(ColaboratorConsolidado cConsolidado : colaboratorsConsolidadoFinal){
				if(cRepo.getNome().equals(cConsolidado.getNome())){
					cConsolidado.acrescentaCommitsRegistrados(cRepo.getCommitsRegistrados());
					cConsolidado.acrescentaIssueFechadoPorCommit(cRepo.getIssuesFechadosPorCommit());
					cConsolidado.acrescentaIssueFechadoPorCommitComLabels(cRepo.getIssuesFechadosPorCommitComLabels());
					cConsolidado.acrescentaIssueAbertos(cRepo.getIssuesAbertos());
					cConsolidado.acrescentaTentativaIssueFechadoPorCommit(cRepo.getTentativaIssueFechadoPorCommit());
					
					if((cRepo.getTipoAjustado().equals(TipoContributor.DEVELOPER)) && (cConsolidado.getTipoAjustado().equals(TipoContributor.REPORTER))){
						cConsolidado.setTipoAjustado(TipoContributor.DEVELOPER);
					}
					
					if((cRepo.isDeveloper()) && (!cConsolidado.isDeveloper())){
						cConsolidado.developerTrue();
					}
					
					if((cRepo.isReporter()) && (!cConsolidado.isReporter())){
						cConsolidado.reporterTrue();
					}
					
					if(cConsolidado.getOwnedPrivateRepos() == 0){
						cConsolidado.setOwnedPrivateRepos(cRepo.getOwnedPrivateRepos());
					}
					if(cConsolidado.getPublicRepos() == 0){
						cConsolidado.setPublicRepos(cRepo.getPublicRepos());
					}
					if(cConsolidado.getTempoCriado() == 0){
						cConsolidado.setTempoCriado(cRepo.getTempoCriado());
					}
					if(cConsolidado.getTotalPrivateRepos() == 0){
						cConsolidado.setTotalPrivateRepos(cRepo.getTotalPrivateRepos());
					}
					
					cEncontrado = true;	
				}
			}
			if(!cEncontrado){
				colaboratorsConsolidadoFinal.add(cRepo);
			}
		}
		
		
	}


	private static ArrayList<LabelConsolidado> calculaConsolidadoLabels(ArrayList<IssueConsolidado> issues, ArrayList<LabelConsolidado> labels) throws FileNotFoundException {
		for(IssueConsolidado i : issues){
			for(LabelsIssueConsolidado l : i.getLabels()){
				Boolean agrupadorEncontrado = false;
				for(LabelConsolidado ls : labels){
					for(String palavra : ls.getLabelVaridade()){
						if(l.getLabel().equals(palavra)){
							ls.incrementaContador();
							if(l.getFechadoPorCommit())
								ls.incrementaContadorFechadoPorCommit();
							agrupadorEncontrado = true;
						}
					}
				}
				if(!agrupadorEncontrado){
					LabelConsolidado labelOutros = labels.get(labels.size() - 1);
					labelOutros.incrementaContador();
					if(l.getFechadoPorCommit())
						labelOutros.incrementaContadorFechadoPorCommit();
				}
			}
		}
		
		return labels;
	}


	private static RepositorioConsolidado carregaInfoRepositorio(String linha) throws FileNotFoundException {
		String [] tokens = linha.split(";");

		RepositorioConsolidado repositorio= new RepositorioConsolidado(tokens[0],
																		tokens[1],
																		tokens[2],
																		tokens[3],
																		tokens[4],
																		tokens[5],
																		tokens[6],
																		tokens[7],
																		tokens[8],
																		tokens[9],
																		tokens[10],
																		tokens[11],
																		tokens[12],
																		tokens[13],
																		tokens[14],
																		tokens[15],
																		tokens[16],
																		tokens[17],
																		tokens[18],
																		tokens[19],
																		tokens[20],
																		tokens[21],
																		tokens[22],
																		tokens[23],
																		tokens[24],
																		tokens[25],
																		tokens[26],
																		tokens[27],
																		tokens[28],
																		tokens[29],
																		tokens[30],
																		tokens[31],
																		tokens[32],
																		tokens[33],
																		tokens[34],
																		tokens[35],
																		tokens[36],
																		tokens[37],
																		tokens[38],
																		tokens[39],
																		tokens[40],
																		tokens[41],
																		tokens[42],
																		tokens[43],
																		tokens[44],
																		tokens[45],
																		tokens[46],
																		tokens[47],
																		tokens[48],
																		tokens[49],
																		tokens[50],
																		tokens[51],
																		tokens[52],
																		tokens[53],
																		tokens[54],
																		tokens[55],
																		tokens[56],
																		tokens[57],
																		tokens[58]);

		return repositorio;
	}


	private static ArrayList<CommitsConsolidado> carregaInfoCommits(
			String pasta2, String id) throws FileNotFoundException {

		String pastaOrigem = pasta2;
		File pastaGeral = new File (pastaOrigem);
		File [] pastas = pastaGeral.listFiles();
		ArrayList<CommitsConsolidado> commits = new ArrayList<CommitsConsolidado>();

		for(File p : pastas){
			if(p != null){
				if(verificaId(id, p)){
					File [] files = p.listFiles();
					for(File f : files){
						if(f.getName().contains("commits.txt")){
						commits = ReaderConsolidadorDeArquivos.AnalisaCommitsBuscaIssues(f, id);
						}
					}
				}
			}
		}
		return commits;
	}

	public static String retornaId(String [] tokens, String tipoArquivo) {
		if(tipoArquivo.equals("relatorio"))
			return tokens[1] + tokens[2];
		if(tipoArquivo.equals("colaborator") || tipoArquivo.equals("issue") || tipoArquivo.equals("label"));
			return tokens[0] + tokens[1];
	}


	public static String retornaLoc (String pasta, String id){
		String pastaOrigem = pasta;
		File pastaGeral = new File (pastaOrigem);
		File [] pastas = pastaGeral.listFiles();
		for(File p : pastas)  {
			if(p != null){
				if(verificaId(id, p)){
					File [] files = p.listFiles();
					for(File f : files){
						if(f.getName().contains("loc.txt")){
						return ReaderConsolidadorDeArquivos.retiraLoc(f);
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


	/*public static void informaDeveloperColaborator(String nome, String id) throws FileNotFoundException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(COLABORATOR)));

		while(file.hasNext()){
			String linha = file.nextLine();
			if(!linha.isEmpty()){
				String idArquivoColaborador = retornaId(linha, "colaborator");
				if(id.equals(idArquivoColaborador)){
					String [] tokens = linha.split(";");
					if(nome.equals(tokens[3])){
						System.out.println("achei: " + nome);
					}
				}
			}
		}

	}*/

	/*
	public static void buscaIssuesResolvidosPorCommit(String commit,
			String nome, String id) {
		// TODO Auto-generated method stub

	}*/
}
