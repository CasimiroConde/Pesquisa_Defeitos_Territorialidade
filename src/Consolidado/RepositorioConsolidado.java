package Consolidado;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;

import consolidadoWriter.ColaboratorConsolidadoWriter;
import consolidadoWriter.IssueConsolidadoWriter;
import consolidadoWriter.LabelConsolidadoWriter;
import consolidadoWriter.ListaColaboratorConsolidado;
import consolidadoWriter.ListaIssueConsolidado;
import consolidadoWriter.ListaLabelConsolidado;
import consolidadoWriter.ListaRepositorioConsolidado;
import consolidadoWriter.RepositorioConsolidadoWriter;
import issuesRepositorios.MetodosAuxiliares;
import Contributors.Contributors;
import Contributors.TipoContributor;
import leituraEscrita.ConsolidadorDeArquivos;
import lombok.Data;
import marcacoesIssues.MarcacaoIssue;

/**
 * Classe que representa uma entrada do arquivo consolidado
 *
 * @author marciobarros
 */
public @Data class RepositorioConsolidado
{
	private int indice;
	private String usuario;
	private String repositorio;
	private String owner;
	private String dataCriado;
	private int contributors;
	private String linguagem;
	private int Nissue;
	private int openIssues;
	private int closedIssues;
	private int openIssueBug;
	private int closedIssueBug;
	private int followers;
	private int following;
	private int forks;
	private int watchers;
	private int sizeInKilobytes;
	private boolean hasIssue;
	private int idadeDia;
	private int contadorIssuesCorrigidosCommits;
	private int contadorIssuesBugCorrigidosCommits;
	private int contadorTentativaDeCorrecaoDeIssues;
	private int contadorIssueLabelCorrigidosPorCommit;
	private double porcentualIssuesFechadosCommit;
	private double porcentualIssuesBugFechadosCommit;
	private double porcentualIssuesBugFechadosCommitTotal;
	private int numeroLoc;
	ArrayList<IssueConsolidado> issues;
	ArrayList<ColaboratorConsolidado> colaborators;
	ArrayList<CommitsConsolidado> commits;
	private String idUnico;
	private String idadeDiaAjudatado;
	private int idadeSemana;
	private String idadeSemanaAjustado;
	private String contributorsAjustado;
	private String contemIssue;
	private String nIssueAjustado;
	private String openIssueAJustado;
	private String closedIssuesAJustado;
	private String openIssueBugAjustado;
	private String closedIssueBugAjustado;
	private String followersAjustado;
	private String followingAjustado;
	private String forksAjustado;
	private String watchersAjustado;
	private String sizeInKilobytesAjustado;
	private String contadorIssuesCorrigidosCommitsAjustado;
	private String contadorIssuesBugCorrigidosCommitsAjustado;
	private String contadorTentativaDeCorrecaoDeIssuesAjustado;
	private String contadorIssueLabelCorrigidosPorCommitAjustado;
	private int numeroDevTrueRepTrue;
	private String numeroDevTrueRepTrueAjustado;
	private int numeroDevTrueRepFalse;
	private String numeroDevTrueRepFalseAjustado;
	private int numeroDevFalseRepTrue;
	private String numeroDevFalseRepTrueAjustado;
	private int numeroDevTrue;
	private String numeroDevTrueAjustado;
	private int numeroRepTrue;
	private int somaTimeToFix;
	private String numeroRepTrueAjustado;
	private String somaTimeToFixAjustado;
	private int mediaTimetoFix;
	private String mediaTimetoFixAjustado;
	private String isFork;
	private String repositorioPai;
	
	
	final static String ISSUE = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaIssue/listaIssue20-05-2016_01-09.txt";
	final static String COLABORATOR = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/listaContributors/listaContributors20-05-2016_01-09.txt";
	final static String COLABORATORFINAL = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/ConsolidadoPosAnalise/Contributors/listaContributorsFinal_teste.txt";
	final static String LABELFINAL = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/ConsolidadoPosAnalise/Labels/listaLabelsFinal_teste.txt";
	final static String REPOSITORIOFINAL = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/ConsolidadoPosAnalise/Repositorio/listaRepositoriosFinal_teste.txt";
	final static String ISSUEFINAL = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/ConsolidadoPosAnalise/Issue/listaIssueFinal_teste.txt";

	
	public RepositorioConsolidado(String indice, String user, String repository, String owner,
			String IdUnico, String dataCriado, String idadeDia, String idadeDiaAjustado, String idadeSemana, String idadeSemanaAjustado,
			String contributors, String contributorsAjustado, String linguagem, String hasIssues, String contemIssue,
			String numeroIssues, String numeroIssueAjustado, String openIssue, String openIssueAjustado, 
			String closedIssue, String closedIssueAjustado, String openIssueBug, String openIssueBugAjustado, String closedIssueBug, String closedIssueBugAjustado, 
			String followers, String followersAjustado,	String following, String followingAjustado, String forks, String forksAjustado, String watchers, String watchersAjustado,
			String kilobytes, String kilobytesAjustado, String issuesCorrigidosCommits, String issuesCorrigidosCommitsAjustado, String issuesBugCorrigidosCommits, String issuesBugCorrigidosCommitsAjustado,
			String porcentualIssuesFechadosCommit, String porcentualIssuesBugFechadosCommit, String porcentualIssuesBugFechadosCommitTotal,
			String numeroDevTrueRepTrue, String numeroDevTrueRepTrueAjustado, 
			String numeroDevTrueRepFalse, String numeroDevTrueRepFalseAjustado,
			String numeroDevFalseRepTrue, String numeroDevFalseRepTrueAjustado, 
			String numeroDevTrue, String numeroDevTrueAjustado,	
			String numeroRepTrue, String numeroRepTrueAjustado, 
			String somaTimeToFix, String somaTimeToFixAjustado,
			String mediaTimetoFix, String mediaTimetoFixAjustado,
			String isFork, String repositorioPai,
			String numeroLoc) throws FileNotFoundException {

		this.indice = Integer.parseInt(indice);
		this.usuario = user;
		this.repositorio = repository;
		this.owner = owner;
		this.idUnico = IdUnico;
		this.dataCriado = dataCriado;
		this.idadeDia = Integer.parseInt(idadeDia);
		this.idadeDiaAjudatado = idadeDiaAjustado;
		this.idadeSemana = Integer.parseInt(idadeSemana);
		this.idadeSemanaAjustado = idadeSemanaAjustado;
		this.contributors = 0;
		this.contributorsAjustado = "NA";
		this.linguagem = linguagem;
		this.hasIssue = Boolean.parseBoolean(hasIssues);
		this.contemIssue = contemIssue;
		this.Nissue = Integer.parseInt(numeroIssues);
		this.nIssueAjustado = numeroIssueAjustado;
		this.openIssues= Integer.parseInt(openIssue);
		this.openIssueAJustado = openIssueAjustado;
		this.closedIssues = Integer.parseInt(closedIssue);
		this.closedIssuesAJustado = closedIssueAjustado;
		this.openIssueBug = Integer.parseInt(openIssueBug);
		this.openIssueBugAjustado = openIssueBugAjustado;
		this.closedIssueBug = Integer.parseInt(closedIssueBug);
		this.closedIssueBugAjustado = closedIssueBugAjustado;
		this.followers = Integer.parseInt(followers);
		this.followersAjustado = followersAjustado;
		this.following = Integer.parseInt(following);
		this.followingAjustado = followingAjustado;
		this.forks = Integer.parseInt(forks);
		this.forksAjustado = forksAjustado;
		this.watchers = Integer.parseInt(watchers);
		this.watchersAjustado = watchersAjustado;
		this.sizeInKilobytes = Integer.parseInt(kilobytes);
		this.sizeInKilobytesAjustado = kilobytesAjustado;
		this.contadorIssuesCorrigidosCommits = Integer.parseInt(issuesCorrigidosCommits);
		this.contadorIssuesCorrigidosCommitsAjustado = issuesCorrigidosCommitsAjustado;
		this.contadorIssuesBugCorrigidosCommits = Integer.parseInt(issuesBugCorrigidosCommits);
		this.contadorIssuesBugCorrigidosCommitsAjustado = issuesBugCorrigidosCommitsAjustado;
		this.contadorTentativaDeCorrecaoDeIssues = 0;
		this.contadorTentativaDeCorrecaoDeIssuesAjustado = "NA";
		this.contadorIssueLabelCorrigidosPorCommit = 0;
		this.contadorIssueLabelCorrigidosPorCommitAjustado = "NA";
		this.porcentualIssuesFechadosCommit = Double.parseDouble(porcentualIssuesFechadosCommit.substring(0, porcentualIssuesFechadosCommit.indexOf("%")));
		this.porcentualIssuesBugFechadosCommit = Double.parseDouble(porcentualIssuesBugFechadosCommit.substring(0, porcentualIssuesFechadosCommit.indexOf("%")));
		this.porcentualIssuesBugFechadosCommitTotal = Double.parseDouble(porcentualIssuesBugFechadosCommitTotal.substring(0, porcentualIssuesFechadosCommit.indexOf("%")));
		this.numeroDevTrueRepTrue = 0;
		this.numeroDevTrueRepTrueAjustado = "NA"; 
		this.numeroDevTrueRepFalse = 0;
		this.numeroDevTrueRepFalseAjustado = "NA";
		this.numeroDevFalseRepTrue = 0;
		this.numeroDevFalseRepTrueAjustado = "NA"; 
		this.numeroDevTrue = 0;
		this.numeroDevTrueAjustado = "NA";	
		this.numeroRepTrue = 0;
		this.numeroRepTrueAjustado = "NA"; 
		this.somaTimeToFix = Integer.parseInt(somaTimeToFix);
		this.somaTimeToFixAjustado = somaTimeToFixAjustado;
		this.mediaTimetoFix = Integer.parseInt(mediaTimetoFix);
		this.mediaTimetoFixAjustado = mediaTimetoFixAjustado; 
		this.isFork = isFork;
		this.repositorioPai = repositorioPai;
		if(numeroLoc.equals("NA"))
			this.numeroLoc = 0;
		else
			this.numeroLoc = Integer.parseInt(numeroLoc);
		this.issues = carregaInfoIssues(this.getIdUnico());
		this.colaborators = carregaInfoColaborators(this.getIdUnico());
		this.commits = new ArrayList<CommitsConsolidado>(); 
	}
	
	
	private static ArrayList<IssueConsolidado> carregaInfoIssues(String id) throws FileNotFoundException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(ISSUE)));
		ArrayList<IssueConsolidado> issues = new ArrayList<IssueConsolidado>();
		String linha = file.nextLine();

		while(file.hasNext()){
			linha = file.nextLine();
			String [] tokens = linha.split(";");
			if(tokens.length >1 ){
			String idArquivoIssue = ConsolidadorDeArquivos.retornaId(tokens, "issue");
			if(id.equals(idArquivoIssue)){
				IssueConsolidado issue = new IssueConsolidado(tokens[0],
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
																				id);
				issues.add(issue);
				}
			}

		}

		return issues;
	}
	
	public static ArrayList<ColaboratorConsolidado> carregaInfoColaborators (String id) throws FileNotFoundException{
		Scanner file = new Scanner(new BufferedReader(new FileReader(COLABORATOR)));
		ArrayList<ColaboratorConsolidado> colaborators = new ArrayList<ColaboratorConsolidado>();
		String linha = file.nextLine();


		while(file.hasNext()){
			linha = file.nextLine();
			String [] tokens = linha.split(";");
			if(tokens.length > 1){
			String idArquivoColaborador = ConsolidadorDeArquivos.retornaId(tokens, "colaborator");
			if(id.equals(idArquivoColaborador)){
				ColaboratorConsolidado colaborator = new ColaboratorConsolidado(tokens[0],
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
																				tokens[19]);
				colaborators.add(colaborator);
				}
			}

		}

		return colaborators;

	}


	public RepositorioConsolidado() {
		// TODO Auto-generated constructor stub
	}


	public double getSizeInMegabytes()
	{
		return sizeInKilobytes / 1024;
	}

	public double getIssueXMB()
	{
		return this.getNissue() / this.getSizeInMegabytes();
	}

	public String getIssueXMBAjustado()
	{
		return (this.getSizeInMegabytes() > 0) ? "" + this.getSizeInMegabytes(): "NA";
	}


	
	public int getReportersIssue()
	{
		int soma = 0;
		for(ColaboratorConsolidado c : this.colaborators){
			if(c.isReporter())
				soma++;
		}
		return soma;
	}

	public void acrescentaContadorIssuesCorrigidosPorCommit(){
		this.contadorIssuesCorrigidosCommits++;
		this.setContadorIssuesCorrigidosCommitsAjustado(Integer.toString(this.contadorIssuesCorrigidosCommits));
	}
	
	public void acrescentaContadorIssuesBugCorrigidosPorCommit(){
		this.contadorIssuesBugCorrigidosCommits++;
		this.setContadorIssuesBugCorrigidosCommitsAjustado(Integer.toString(this.contadorIssuesBugCorrigidosCommits));
	}
	
	public void imprimirColaboratorConsolidado() throws IOException {
		ListaColaboratorConsolidado lista = new ListaColaboratorConsolidado();
		lista.addAll(this.getColaborators());
		
		ColaboratorConsolidadoWriter writer = new ColaboratorConsolidadoWriter();
		writer.saveToFile(COLABORATORFINAL, lista);
	}
	
	public void imprimirLabelConsolidado() throws IOException {
		ListaLabelConsolidado lista = new ListaLabelConsolidado();
		
		for(IssueConsolidado i : this.getIssues()){
			lista.addAll(i.getLabels());	
		}
		
		LabelConsolidadoWriter writer = new LabelConsolidadoWriter();
		writer.saveToFile(LABELFINAL, lista);
	}


	public void imprimirRepositorioConsolidado() throws IOException {
		ListaRepositorioConsolidado lista = new ListaRepositorioConsolidado();
		lista.add(this);
		
		RepositorioConsolidadoWriter writer = new RepositorioConsolidadoWriter();
		writer.saveToFile(REPOSITORIOFINAL, lista);
	}


	public void caculaPercentuais() {
		this.setPorcentualIssuesFechadosCommit(((double) this.getContadorIssuesCorrigidosCommits() / (double) this.getClosedIssues()) * 100);
		this.setPorcentualIssuesBugFechadosCommit(((double)this.getContadorIssuesBugCorrigidosCommits() / (double) this.getClosedIssueBug()) * 100);
		this.setPorcentualIssuesBugFechadosCommitTotal(((double) this.getContadorIssuesCorrigidosCommits() / (double) this.getNissue()) * 100);
	}


	public void informaColaboratorCorrigiuIssuePorCommit(String autor, IssueConsolidado issue2) {
		for(ColaboratorConsolidado c : this.colaborators){
			if(c.getUserName().equals(autor) || c.getNome().equals(autor)){
				c.acrescentaIssueFechadoPorCommit();
				if(issue2.getHasLabel())
					c.acrescentaIssueFechadoPorCommitComLabels();
			}
		}
		
	}


	public void calculaQuantidadeDevRep() {
		for(ColaboratorConsolidado c : this.colaborators){
			if(c.isDeveloper() && c.isReporter())
				this.incrementaDevTrueRepTrue();
			if(c.isDeveloper() && !c.isReporter())
				this.incrementaDevTrueRepFalse();
			if(!c.isDeveloper() && c.isReporter())
				this.incrementaDevFalseRepTrue();
			if(c.isDeveloper())
				this.incrementaDevTrue();
			if(c.isReporter())
				this.incrementaRepTrue();
			
			this.incrementaContributors();
		}
	}


	private void incrementaContributors() {
		this.contributors++;
		this.setContributorsAjustado(Integer.toString(this.contributors));
	}


	private void incrementaRepTrue() {
		this.numeroRepTrue++;
		this.setNumeroRepTrueAjustado(Integer.toString(this.numeroRepTrue));
	}


	private void incrementaDevTrue() {
		this.numeroDevTrue++;
		this.setNumeroDevTrueAjustado(Integer.toString(this.numeroDevTrue));
	}


	private void incrementaDevFalseRepTrue() {
		this.numeroDevFalseRepTrue++;
		this.setNumeroDevFalseRepTrueAjustado(Integer.toString(this.numeroDevFalseRepTrue));	
	}


	private void incrementaDevTrueRepFalse() {
		this.numeroDevTrueRepFalse++;
		this.setNumeroDevTrueRepFalseAjustado(Integer.toString(this.numeroDevTrueRepFalse));
	}


	private void incrementaDevTrueRepTrue() {
		this.numeroDevTrueRepTrue++;
		this.setNumeroDevTrueRepTrueAjustado(Integer.toString(this.numeroDevTrueRepTrue));	
	}


	public void imprimirIssueConsolidado() throws IOException {
		ListaIssueConsolidado lista = new ListaIssueConsolidado();
		lista.addAll(this.getIssues());
		
		IssueConsolidadoWriter writer = new IssueConsolidadoWriter();
		writer.saveToFile(ISSUEFINAL, lista);
	}
	
	public  void IdentificaColaboratorsCommits() {
		
		for(CommitsConsolidado commit : this.getCommits()){
			boolean existe = false;
			for(ColaboratorConsolidado colaborator : this.getColaborators()){
				if(commit.getAutor().equals(colaborator.getNome()) || commit.getAutor().equals(colaborator.getUserName())){
					if(colaborator.getTipoAjustado().equals(TipoContributor.REPORTER)){
						colaborator.setTipoAjustado(TipoContributor.DEVELOPER);
						colaborator.setDeveloper(true);
						colaborator.acrescentaCommitsRegistrados();
						existe = true;
						break;
					} else {
						existe = true;
						colaborator.acrescentaCommitsRegistrados();
					}
				}
			}
			
			if(!existe){
				ColaboratorConsolidado novoColaborator = new ColaboratorConsolidado(this.getUsuario(), this.getRepositorio(), commit.getAutor(), commit.getEmail(), "developer");
				this.colaborators.add(novoColaborator);
			}
		}
	}

	public void IdentificaIssuesEmCommits () throws FileNotFoundException{
		
		for(CommitsConsolidado commit : this.getCommits()){
			if(MetodosAuxiliares.contemPalavraChave(commit.getTexto())){
				String [] tokens = commit.getTexto().split(" ");
				for(int i = 0 ; i < tokens.length ; i++){
					if(MetodosAuxiliares.ePalavraChave(tokens[i]) && ((i + 1) < tokens.length)){
						if(tokens[i + 1].startsWith("#")){
							String numeroIssue = tokens[i + 1].substring(1).replaceAll("[^0-9]", "");
							
							if(this.verificaIssueCommitExiste(numeroIssue)){
								IssueConsolidado issue = getIssueNumero(numeroIssue);
								if(this.verificaIssueCommitEstaFechado(issue)){
									this.acrescentaContadorIssuesCorrigidosPorCommit();
									this.informaColaboratorCorrigiuIssuePorCommit(commit.getAutor(), issue);
									this.informaIssueCorrigidoPorCommit(issue);
									if(this.verificaIssueCommitEBug(issue)){
										this.acrescentaContadorIssuesBugCorrigidosPorCommit();
									}
									if(this.verificaIssueCommitTemLabel(issue)){
										this.acresncentaContadorIssueLabelCorrigidosPorCommit();
									}
								}
							} else {
								this.acrescentaContadorTentativaDeCorrecaoDeIssues();
								this.informaColaboratorTentouCorrigirIssuePorCommit(commit.getAutor());
							}
							
						}
					}
				}
			}		
			
		}

	}
	
	private void informaColaboratorTentouCorrigirIssuePorCommit(String autor) {
		for(ColaboratorConsolidado c : this.colaborators){
			if(c.getUserName().equals(autor) || c.getNome().equals(autor)){
				c.acrescentaTentativaIssueFechadoPorCommit();
			}
		}
		
	}


	private void acresncentaContadorIssueLabelCorrigidosPorCommit() {
		this.contadorIssueLabelCorrigidosPorCommit++;
		this.setContadorIssueLabelCorrigidosPorCommitAjustado(Integer.toString(this.contadorIssueLabelCorrigidosPorCommit));
	}


	private boolean verificaIssueCommitTemLabel(IssueConsolidado issue2) {
		if(!issue2.getLabels().isEmpty())
			return true;
		return false;
	}


	private void acrescentaContadorTentativaDeCorrecaoDeIssues() {
		this.contadorTentativaDeCorrecaoDeIssues++;
		this.setContadorTentativaDeCorrecaoDeIssuesAjustado(Integer.toString(this.contadorTentativaDeCorrecaoDeIssues));
	}


	private IssueConsolidado getIssueNumero(String numeroIssue) {
		for(IssueConsolidado c: this.getIssues()){
			if(c.getNumero().equals(numeroIssue))
				return c;
		}
		return null;
	}

	private  boolean verificaIssueCommitExiste(String numeroIssue) {
		try{
			if(!numeroIssue.isEmpty()){
					for(IssueConsolidado i : this.getIssues()){
						if(i.getNumero().equals(numeroIssue))
							return true;
					}
					System.out.println("N�o achou Issue: " + numeroIssue);
					return false;

			}
			return false;
		} catch (NumberFormatException n){
			System.out.println("Erro ao buscar o n�mero: " + numeroIssue);
			return false;
		}

	}

	private boolean verificaIssueCommitEBug(IssueConsolidado issue) {
		if(!issue.equals(null)){
			if(MetodosAuxiliares.eBug(issue.getLabels()))
				return true;
		}
		return false;
	}
	
	private boolean verificaIssueCommitEstaFechado(IssueConsolidado issue) {
		if(!issue.equals(null)){
			if(issue.getEstado().toLowerCase().equals("closed"))
				return true;
		}
		return false;
	}

	
	public void informaIssueCorrigidoPorCommit(IssueConsolidado issue2) {
		for(IssueConsolidado i : this.getIssues()){
			if(i.getNumero().equals(issue2.getNumero())){
				i.informaCorrigidoPorCommito();
				if(i.getHasLabel()){
					i.informaLabelsCorrigidasPorCommit();
				}
			}
		}

		
	}
}
