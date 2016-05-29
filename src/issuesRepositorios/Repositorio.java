package issuesRepositorios;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import leituraEscrita.Reader;
import leituraEscrita.Writer;
import lombok.Data;
import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.MarcacaoIssue;
import marcacoesIssues.MetodosAuxiliaresLabel;
import marcacoesIssues.TipoMarcacao;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.IssueEvent;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.egit.github.core.service.WatcherService;
import org.joda.time.DateTime;
import org.joda.time.Days;

import Contributors.Contributors;
import Contributors.TipoContributor;

public @Data class Repositorio {

	private String userName;
	private String repositoryName;
	private String idUnico;
	private String user;
	private String language;
	private String contemIssue;
	private boolean hasIssue;
	private boolean isFork;
	private IRepositoryIdProvider repoId;
	private Date create;
	private int numeroFollowersOwner;
	private int numeroFollowingOwner;
	private int numeroForks;
	private int numeroWatchers;
	private int tamanhoRepositorio;
	private int idadeDia;
	private int idadeSemana;
	private int numeroIssues;
	private int numeroContributors;
	private int numeroDevTrueRepTrue;
	private int numeroDevTrue;
	private int numeroRepTrue;
	private int numeroDevTrueRepFalse;
	private int numeroDevFalseRepTrue;
	private int openIssue = 0;
	private int closedIssue = 0;
	private int openIssueBug = 0;
	private int closedIssueBug = 0;
	private int contadorIssuesCorrigidosCommits = 0;
	private int contadorIssuesBugCorrigidosCommits = 0;
	private int somaTimeToFix;
	private int mediaTimeToFix;
	//Incluir todos os AJUSTADOS com "NA";
	private String numeroFollowersOwnerAjustado;
	private String numeroFollowingOwnerAjustado;
	private String numeroForksAjustado;
	private String numeroWatchersAjustado;
	private String tamanhoRepositorioAjustado;
	private String idadeDiaAjustado;
	private String idadeSemanaAjustado;
	private String numeroIssuesAjustado;
	private String numeroContributorsAjustado;
	private String numeroDevTrueRepTrueAjustado;
	private String numeroDevTrueAjustado;
	private String numeroRepTrueAjustado;
	private String  numeroDevTrueRepFalseAjustado;
	private String numeroDevFalseRepTrueAjustado;
	private String openIssueAjustado;
	private String closedIssueAjustado;
	private String openIssueBugAjustado;
	private String closedIssueBugAjustado;
	private String contadorIssuesCorrigidosCommitsAjustado;
	private String contadorIssuesBugCorrigidosCommitsAjustado;
	private String somaTimeToFixAjustado;
	private String mediaTimeToFixAjustado;
	private double porcentualIssuesFechadosCommit = 0.0;
	private double porcentualIssuesBugFechadosCommit = 0.0;
	private double porcentualIssuesBugFechadosCommitTotal = 0.0;
	private ArrayList<Issue> issues;
	private List<Contributor> contributors;
	private List<Contributors> contributorsAjustado;
	private ArrayList<MarcacaoIssue> marcacaoIssue;
	private String repositorioPai = " ";
	

	/*public Repositorio(String userName, String repositoryName, IssueService issueService, RepositoryService repositoryService, GitHubClient client) throws InterruptedException, IOException{
		boolean finished = false;
		WatcherService watcherService = new WatcherService(client);
		while(!finished){
			try {
				this.userName = repositorio.getOwner().getLogin();
				this.repositoryName = repositorio.getName();
				this.repoId = new RepositoryId(this.userName, this.repositoryName);
				Repository repo = repositoryService.getRepository(repoId);
				this.numeroForks = repositoryService.getForks(this.repoId).size();repositorio.getForks();
				this.tamanhoRepositorio = repositorio.getSize();
				this.numeroFollowersOwner = repositorio.getOwner().getFollowers();
				this.numeroFollowingOwner = repositorio.getOwner().getFollowing();
				this.numeroWatchers = watcherService.getWatchers(this.repoId).size(); repositorio.getWatchers();
				this.marcacaoIssue = new ArrayList<MarcacaoIssue>();
				this.issues = new ArrayList<Issue>();
				this.contributors = repositoryService.getContributors(this.repoId, true);
				this.coletaIssues(issueService);
				finished = true;
			} catch (NoSuchPageException e) {
				System.out.println("Carregando Repositï¿½rio!! Repositï¿½rio: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);
			} catch (RequestException e) {
				System.out.println("Repositï¿½rio nï¿½o acessado!! Repositï¿½rio: " + this.getRepositoryName());
				this.setRepositoryName("Vazio");
				finished = true;
			}
		}
	}*/
	public Repositorio(String userName, String repositoryName ) throws IOException, RequestException{
		this.userName = userName;
		this.repositoryName = repositoryName;
	}

	public Repositorio(GitHubClient client, String userName, String repositoryName) throws IOException, RequestException{
		RepositoryService repositoryService = new RepositoryService(client);

		this.userName = userName;
		this.repositoryName = repositoryName;
		this.repoId = new RepositoryId(this.userName, this.repositoryName);
		Repository repositorio = repositoryService.getRepository(repoId);
	}

	public Repositorio(String userName, String repositoryName, GitHubClient client, ArrayList<LabelConsolidado> consolidadoLabel) throws InterruptedException, IOException{
		RepositoryService repositoryService = new RepositoryService(client);
		IssueService issueService = new IssueService(client);
		UserService userService = new UserService(client);

		boolean finished = false;
		WatcherService watcherService = new WatcherService(client);
		while(!finished){
			try {
				this.userName = userName;
				this.repositoryName = repositoryName;
				this.idUnico = this.userName + this.repositoryName;
				this.repoId = new RepositoryId(this.userName, this.repositoryName);
				Repository repositorio = repositoryService.getRepository(repoId);
				User usuario = userService.getUser(repositorio.getOwner().getLogin());
				this.user = usuario.getName();
				this.numeroForks = /*repositoryService.getForks(this.repoId).size();*/repositorio.getForks();
				this.numeroForksAjustado = geraValorAjustadoEmString(this.numeroForks);
				this.create = repositorio.getCreatedAt();
				this.language = repositorio.getLanguage();
				this.idadeDia = calculaIdadeDia();
				this.idadeDiaAjustado = geraValorAjustadoEmString(this.idadeDia);
				this.idadeSemana = calculaIdadeSemana();
				this.idadeSemanaAjustado = geraValorAjustadoEmString(this.idadeSemana);
				this.hasIssue = repositorio.isHasIssues();
				this.tamanhoRepositorio = repositorio.getSize();
				this.tamanhoRepositorioAjustado = geraValorAjustadoEmString(this.tamanhoRepositorio);
				this.numeroFollowersOwner = usuario.getFollowers();
				this.numeroFollowersOwnerAjustado = geraValorAjustadoEmString(this.numeroFollowersOwner);
				this.numeroFollowingOwner = usuario.getFollowing();
				this.numeroFollowingOwnerAjustado = geraValorAjustadoEmString(this.numeroFollowingOwner);
				this.numeroWatchers = /*watcherService.getWatchers(this.repoId).size();*/ repositorio.getWatchers();
				this.numeroWatchersAjustado = geraValorAjustadoEmString(this.numeroWatchers);
				this.contributors = repositoryService.getContributors(this.repoId, true);
				this.contributorsAjustado = new ArrayList<Contributors>();
				this.marcacaoIssue = new ArrayList<MarcacaoIssue>();
				this.issues = new ArrayList<Issue>();
				this.coletaIssues(issueService, userService);
				this.numeroIssues = this.getIssues().size();
				this.numeroIssuesAjustado = geraValorAjustadoEmString(this.numeroIssues);
				this.numeroContributors = this.getContributors().size();
				this.numeroContributorsAjustado = geraValorAjustadoEmString(this.numeroContributors);
				this.numeroDevFalseRepTrue = calculcaDevFalseRepTrue();
				this.numeroDevFalseRepTrueAjustado = geraValorAjustadoEmString(this.numeroDevFalseRepTrue);
				this.numeroDevTrue = calculaDevTrue();
				this.numeroDevTrueAjustado = geraValorAjustadoEmString(this.numeroDevTrue);
				this.numeroDevTrueRepFalse = calculaDevTrueRepFalse();
				this.numeroDevTrueRepFalseAjustado = geraValorAjustadoEmString(this.numeroDevTrueRepFalse);
				this.numeroDevTrueRepTrue = calculaDevTrueRepTrue();
				this.numeroDevTrueRepTrueAjustado = geraValorAjustadoEmString(this.getNumeroDevTrueRepTrue());
				this.numeroRepTrue = calculaRepTrue();
				this.numeroRepTrueAjustado = geraValorAjustadoEmString(this.numeroRepTrue);
				this.somaTimeToFix = calculaSomaTimeToFix();
				this.somaTimeToFixAjustado = geraValorAjustadoEmString(this.somaTimeToFix);
				this.mediaTimeToFix = calculaMediaTimeToFix();
				this.mediaTimeToFixAjustado = geraValorAjustadoEmString(this.mediaTimeToFix);
				this.contemIssue = geraContemIssueEmString();
				this.isFork = repositorio.isFork();
				if(this.isFork)
					this.repositorioPai  = repositorio.getOwner().getLogin() + "/" + repositorio.getName();
				
				calculaQuantidadesIssues(consolidadoLabel);
				this.openIssueAjustado = geraValorAjustadoEmString(this.openIssue);
				this.closedIssueAjustado = geraValorAjustadoEmString(this.closedIssue);
				this.openIssueBugAjustado = geraValorAjustadoEmString(this.openIssueBug);
				this.closedIssueBugAjustado = geraValorAjustadoEmString(this.closedIssueBug);
				this.contadorIssuesCorrigidosCommitsAjustado = geraValorAjustadoEmString(this.contadorIssuesCorrigidosCommits);
				this.contadorIssuesBugCorrigidosCommitsAjustado = geraValorAjustadoEmString(this.contadorIssuesBugCorrigidosCommits);
				
				finished = true;
			} catch (NoSuchPageException e) {
				System.out.println("Carregando Repositï¿½rio!! Aguardando Request Limit!! Repositï¿½rio: " + this.getRepositoryName() + " Erro: " + e.getMessage());
				Thread.sleep(600 * 1000);
			} catch (RequestException e) {
				if(e.getStatus() == 403){
					System.out.println("Carregando Repositï¿½rio!! Aguardando Request Limit!! Repositï¿½rio: " + this.getRepositoryName() + " Erro: " + e.getStatus() + "-" + e.getMessage());
					Thread.sleep(600 * 1000);
				} else {
					this.repositoryName = "Vazio";
					System.out.println("Repositï¿½rio nï¿½o acessado!! Repositï¿½rio: " + this.getRepositoryName() + " Exception:" + e.getMessage());
					finished = true;
				}
			}
			
		}
	}
	
	
	private String geraContemIssueEmString() {
		return (this.numeroIssues > 0) ? "Contém Issue" : "Não Contém Issue";
	}

	private int calculaMediaTimeToFix() {
		if(this.numeroIssues == 0)
			return 0;
		return this.somaTimeToFix / this.numeroIssues;
	}

	private int calculaSomaTimeToFix() {
		int soma = 0;
		for(Issue i : this.issues){
			int timeToFix = Days.daysBetween(new DateTime(i.getCreatedAt()),new DateTime(i.getClosedAt())).getDays();
			soma += timeToFix;
		}
		return soma;
	}

	private int calculaRepTrue() {
		int soma = 0;
		for(Contributors c : this.contributorsAjustado){
			if(c.isReporter())
				soma++;
		}
		return soma;
	}

	private int calculaDevTrueRepTrue() {
		int soma = 0;
		for(Contributors c : this.contributorsAjustado){
			if(c.isReporter() && c.isDeveloper())
				soma++;
		}
		return soma;
	}

	private int calculaDevTrueRepFalse() {
		int soma = 0;
		for(Contributors c : this.contributorsAjustado){
			if(!c.isReporter() && c.isDeveloper())
				soma++;
		}
		return soma;
	}

	private int calculaDevTrue() {
		int soma = 0;
		for(Contributors c : this.contributorsAjustado){
			if(c.isDeveloper())
				soma++;
		}
		return soma;
	}

	private int calculcaDevFalseRepTrue() {
		int soma = 0;
		for(Contributors c : this.contributorsAjustado){
			if(c.isReporter() && !c.isDeveloper())
				soma++;
		}
		return soma;
	}

	public String geraValorAjustadoEmString(int numero){
		return (numero > 0) ? String.valueOf(numero): "NA";
	}

	public Repositorio(String userName, String repositoryName, GitHubClient client, String simples) throws InterruptedException, IOException{
		RepositoryService repositoryService = new RepositoryService(client);


		boolean finished = false;
		while(!finished){
			try {
				this.userName = userName;
				this.repositoryName = repositoryName;
				this.repoId = new RepositoryId(this.userName, this.repositoryName);
				Repository repositorio = repositoryService.getRepository(repoId);
				this.hasIssue = repositorio.isHasIssues();
				finished = true;
			} catch (NoSuchPageException e) {
				System.out.println("Carregando Repositï¿½rio!! Aguardando Request Limit!! Repositï¿½rio: " + this.getRepositoryName() + " Erro: " + e.getMessage());
				Thread.sleep(600 * 1000);
			} catch (RequestException e) {
				if(e.getStatus() == 403){
					System.out.println("Carregando Repositï¿½rio!! Aguardando Request Limit!! Repositï¿½rio: " + this.getRepositoryName() + " Erro: " + e.getStatus() + "-" + e.getMessage());
					Thread.sleep(600 * 1000);
				} else {
					this.repositoryName = "Vazio";
					System.out.println("Repositï¿½rio nï¿½o acessado!! Repositï¿½rio: " + this.getRepositoryName() + " Exception:" + e.getMessage());
					finished = true;
				}
			}
		}
	}

	public int calculaIdadeDia(){
		int tempo;
		Date date = new Date();
		tempo = Days.daysBetween(new DateTime(this.create), new DateTime(date)).getDays();
		return tempo;
	}
	
	public int calculaIdadeSemana(){
		int tempo;
		Date date = new Date();
		tempo = (Days.daysBetween(new DateTime(this.create), new DateTime(date)).getDays()) / 7;
		return tempo;
	}



	public void coletaIssues(IssueService issueService, UserService userService) throws InterruptedException, IOException{
		if(!this.hasIssue){
			return;
		}
		boolean encerrado = false;

		Map<String, String> params = new HashMap<String, String>();
		params.put(IssueService.FILTER_STATE, "all");
		PageIterator<Issue> iterator = issueService.pageIssues(this.getRepoId(), params, 100);

		while(!encerrado){
			try{
			    //Coleta Issue
			    while(iterator.hasNext()){
			    	Collection<Issue> page = iterator.next();
			    	java.util.Iterator<Issue> itr = page.iterator();
			    	while(itr.hasNext()){
			    		Issue issue = itr.next();
						this.issues.add(issue);
						incluiContributorsIssue(userService, issue, issueService);
			    	}
		    	}
			    encerrado = true;
			} catch (NoSuchPageException e ){
				System.out.println("Coleta Issue!! Repositï¿½rio: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);
			}
		}
	}


	private void incluiContributorsIssue(UserService userService, Issue issue, IssueService issueService)
			throws IOException {
		try{
			
			//Inclui usuário que registrou o Issue
			User user = userService.getUser(issue.getUser().getLogin());
			this.incluiContributorAjustado(user , issue.getCreatedAt(), TipoContributor.REPORTER);
			this.incrementaIssuesRegistradosPeloContributor(user, issue);
		} catch (NullPointerException n){
			System.out.println("Usuï¿½rio nï¿½o encontrado");
		}
	}

	

	public int calculaTimeToFixIssue(Issue issue){
		int tempo;
		tempo = Days.daysBetween(new DateTime(issue.getCreatedAt()), new DateTime(issue.getClosedAt())).getDays();
		return tempo;

	}

	private void contaLinhasCodigo(CommitService commitService)
			throws InterruptedException {
		boolean completo = false;
		while(!completo){
			try {
				List<RepositoryCommit> commitsRepositorio = commitService.getCommits(this.repoId);
				int indiceUltimoCommit = commitsRepositorio.lastIndexOf(commitsRepositorio);

			} catch (IOException e) {
				System.out.println("Coletando Colaboradores! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
			}
		}
	}

	public void calculaQuantidadesIssues(ArrayList<LabelConsolidado> labelConsolidado) throws InterruptedException, RequestException{
		//Conta Issues
		//Conta Issues com Label de Bug
		try{
			if(this.hasIssue){
				java.util.Iterator<Issue> itr = this.getIssues().iterator();
				while(itr.hasNext()){
					Issue issue = itr.next();
					this.insereMarcacoes(issue,labelConsolidado);
					List<Label> labels = issue.getLabels();
					if(issue.getState().equalsIgnoreCase("open")){
						this.openIssue++;
						if(MetodosAuxiliares.eBug(labels)){
							this.openIssueBug++;
						}
					}
					if(issue.getState().equalsIgnoreCase("closed")){
							this.closedIssue++;
						if(MetodosAuxiliares.eBug(labels)){
								this.closedIssueBug++;
						}
					}
				}
			}
		}catch (NullPointerException n){
			System.out.println("Issues nao coletados");
		}
	}

	public void defeitosCorrigidosCommitOrigemWeb(CommitService commitService, IssueService issueService) throws IOException, InterruptedException{
		if(!this.hasIssue){
			return;
		}

		boolean encerrado = false;

		while(!encerrado){
			try{
				int contadorIssuesCorrigidosCommits1 = 0;
				int contadorIssuesBugCorrigidosCommits1 = 0;
				for(RepositoryCommit c : commitService.getCommits(this.getRepoId())){
					if(MetodosAuxiliares.contemPalavraChave(c.getCommit().getMessage())){
						String[] palavras = c.getCommit().getMessage().split(" ");
						for(int i = 0 ; i < palavras.length ; i++){
							if(MetodosAuxiliares.ePalavraChave(palavras[i])){
								if((i + 1) < palavras.length ){
									if(palavras[i + 1].startsWith("#")){
										String numeroIssue = palavras[i + 1].substring(1).replaceAll("[^0-9]", "");
										if(verificaIssueCommitExiste(numeroIssue)){
											Issue issue = this.getIssueNumero(numeroIssue);
											if(verificaIssueCommitEstaFechado(issue)){
												contadorIssuesCorrigidosCommits1++;
												if(verificaIssueCommitEBug(issue)){
													contadorIssuesBugCorrigidosCommits1++;
												}
											}
										}
									}
								}
							}
						}
					}
				}

				this.contadorIssuesCorrigidosCommits = contadorIssuesCorrigidosCommits1;
				this.contadorIssuesBugCorrigidosCommits = contadorIssuesBugCorrigidosCommits1;
				encerrado = true;
			} catch (NoSuchPageException n){
				//Writer.criaArquivo(criaNome(cont), buffer);
				System.out.println("Defeitos Corrigidos! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;

			}catch(RequestException r){
				System.out.println("Defeitos Corrigidos! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
			}
		}
	}

	private boolean verificaIssueCommitEstaFechado(Issue issue) {
		if(!issue.equals(null)){
			if(issue.getState().equals("Closed"))
				return true;
		}
		return false;
	}

	public void defeitosCorrigidosCommitOrigemCSV() throws IOException, InterruptedException{
		if(!this.hasIssue){
			return;
		}

		boolean encerrado = false;

		String nomePasta = this.userName+"-"+this.repositoryName;

		String caminhoArmazenamento = "C://Users//Casimiro//Documents//Casimiro Conde//Aulas//Mestrado//Seminï¿½rio de Acompanhamento Discente 2//Territorialidade//Commits//"+nomePasta;


		File pasta = new File (caminhoArmazenamento);
		File [] files = pasta.listFiles();

		if(files != null){
			while(!encerrado){
				try{
					int contadorIssuesCorrigidosCommits1 = 0;
					int contadorIssuesBugCorrigidosCommits1 = 0;
					for(File f : files){
						if(f.isFile() && f.getName().endsWith(".txt")){
							String texto = Reader.retornaConteudo(f);
							if(MetodosAuxiliares.contemPalavraChave(texto)){
								String[] palavras = texto.split(" ");
								for(int i = 0 ; i < palavras.length ; i++){
									if(MetodosAuxiliares.ePalavraChave(palavras[i])){
										if((i + 1) < palavras.length ){
											if(palavras[i + 1].startsWith("#")){
												String numeroIssue = palavras[i + 1].substring(1).replaceAll("[^0-9]", "");
												if(verificaIssueCommitExiste(numeroIssue)){
													Issue issue = this.getIssueNumero(numeroIssue);
													if(verificaIssueCommitEstaFechado(issue)){
														contadorIssuesCorrigidosCommits1++;
														if(verificaIssueCommitEBug(issue)){
															contadorIssuesBugCorrigidosCommits1++;
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

					this.contadorIssuesCorrigidosCommits = contadorIssuesCorrigidosCommits1;
					this.contadorIssuesBugCorrigidosCommits = contadorIssuesBugCorrigidosCommits1;
					encerrado = true;
				} catch (NoSuchPageException n){
					//Writer.criaArquivo(criaNome(cont), buffer);
					System.out.println("Defeitos Corrigidos! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
					Thread.sleep(600 * 1000);
					//return repositorios;

				}
			}
		}
	}

	private Issue getIssueNumero(String numeroIssue) {
		for(Issue i: this.getIssues()){
			if(i.getNumber() == Integer.parseInt(numeroIssue))
				return i;
		}
		return null;
	}

	private boolean verificaIssueCommitExiste(String numeroIssue) {
		try{
			if(!numeroIssue.isEmpty()){
					for(Issue i : this.getIssues()){
						if(i.getNumber() == Integer.parseInt(numeroIssue))
							return true;
					}
					System.out.println("Nï¿½o achou Issue: " + numeroIssue);
					return false;

			}
			return false;
		} catch (NumberFormatException n){
			System.out.println("Erro ao buscar o nï¿½mero: " + numeroIssue);
			return false;
		}

	}

	private boolean verificaIssueCommitEBug(Issue issue) {
		if(!issue.equals(null)){
			if(MetodosAuxiliares.eBug(issue.getLabels()))
				return true;
		}
		return false;
	}

	public void downloadCommits(CommitService commitService, UserService userService) throws IOException, InterruptedException{
		int i = 0;
		try{
			if(!commitService.getCommits(this.repoId).isEmpty()){
				List<RepositoryCommit> commits = commitService.getCommits(this.repoId);
				while(i < commits.size()){
					boolean encerrado = false;
					while(!encerrado){
						try{
							Writer.armazenaCommits(this.getUserName(), this.getRepositoryName(), commits.get(i).getSha(), commits.get(i).getCommit().getMessage());
							incluiContibutorsCommit(userService, commits.get(i));
							encerrado = true;
							i++;
						}catch(RequestException r){
							if(r.getStatus() == 403){
								System.out.println("Downloado de Commit aguardando Request!! Repositï¿½rio: " + this.getRepositoryName());
								Thread.sleep(600 * 1000);
							}else{
								System.out.println("Download de Commit Encerrado!! Repositï¿½rio: " + this.getRepositoryName());
								encerrado = true;
								i++;
							}
						}
					}
				}
			}else{
					Writer.armazenaCommits(this.getUserName(), this.getRepositoryName(), "0","Nï¿½o Existe Commit");
			}
		} catch (NoSuchPageException n){
			//Writer.criaArquivo(criaNome(cont), buffer);
			System.out.println("Download de Commit! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
			Thread.sleep(600 * 1000);
			//return repositorios;

		}catch(RequestException r){
			if(r.getStatus() == 403){
				System.out.println("Downloado de Commit aguardando Request!! Repositï¿½rio: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);
			}else{
				System.out.println("Download de Commit Encerrado!! Repositï¿½rio: " + this.getRepositoryName());
			}

		} catch (ConnectException c){
			System.out.println("Download de Commit! Conexï¿½o interrompida, tentaremos novamente em 1 min!!  Repositï¿½rio: " + this.getRepositoryName());
			Thread.sleep(60 * 1000);
		}
	}

	private void incluiContibutorsCommit(UserService userService,
			RepositoryCommit c) throws IOException {
		try{
			User user = userService.getUser(c.getCommitter().getLogin());
			incluiContributorAjustado(user ,c.getCommit().getAuthor().getDate() , TipoContributor.DEVELOPER);
		}catch(NullPointerException n){
			System.out.println("Usuï¿½rio nï¿½o encontrado");
		} catch(IllegalArgumentException e){
			System.out.println("Usuï¿½rio nï¿½o encontrado");
		}
	}

	private void insereMarcacoes(Issue issue, ArrayList<LabelConsolidado> labelConsolidado) {

		//Analisa Contador de Label
		if(!issue.getLabels().isEmpty())
		MetodosAuxiliaresLabel.insereMarcacaoLabelRepositorio(this.marcacaoIssue,issue, labelConsolidado);

		//Analisa Contador de Milestones
		if(issue.getMilestone() != null)
		MetodosAuxiliaresLabel.insereMarcacaoMilestoneRepositorio(this.marcacaoIssue, issue);

	}


	private boolean existeLabel(String name) {
		for(MarcacaoIssue m : marcacaoIssue){
			if(m.getTipo().equals(TipoMarcacao.LABEL)){
				if(m.getNome().equals(name))
					return true;
			}
		}
		return false;
	}

	public void calculaIssuesFechadosCommit(){
		if(this.getContadorIssuesCorrigidosCommits() > 0){
			this.porcentualIssuesFechadosCommit = (double) (this.getContadorIssuesCorrigidosCommits() * 100) / this.getClosedIssue();
			this.porcentualIssuesBugFechadosCommit = (double) (this.getContadorIssuesBugCorrigidosCommits() * 100) / this.getClosedIssueBug();
			this.porcentualIssuesBugFechadosCommitTotal = (double) (this.getContadorIssuesBugCorrigidosCommits() * 100) / this.getClosedIssue();

		}
	}

	public void addMarcacaoIssue(MarcacaoIssue marcacao){
		this.marcacaoIssue.add(marcacao);
	}

	public void getInformacaoColaboradores(RepositoryService repositoryService) throws InterruptedException{
		List<Contributor> colaboradores = new ArrayList<Contributor>();
		try {
			colaboradores = repositoryService.getContributors(repoId, true);
			for(Contributor c : colaboradores){
				System.out.println(c.getName());
			}
		} catch (IOException e) {
			System.out.println("Coletando Colaboradores! Mï¿½ximo de Requisiï¿½ï¿½es Alcanï¿½ada, tentaremos novamente em 10 min");
			Thread.sleep(600 * 1000);
		}

	}

	public boolean existeContributorAjustado(String login){
		if(this.getContributorsAjustado() == null)
			return false;

		for(Contributors c : this.getContributorsAjustado()){
			if(c.getLogin() != null){
				if(c.getLogin().equals(login)){
					return true;
				}
			}
		}
		return false;
	}
	public Contributors pegaContributorAjustado(String login){
		for(Contributors c : this.getContributorsAjustado()){
			if(c.getLogin() != null){
				if(c.getLogin().equals(login)){
					return c;
				}
			}
		}
		return null;
	}

	public void incluiContributorAjustado(User user, Date date, TipoContributor tipo){
		if(existeContributorAjustado(user.getLogin())){
			pegaContributorAjustado(user.getLogin()).ajusteTipiicacao(date, tipo);
		} else {
			Contributors contributor = new Contributors(user);
			contributor.ajusteTipiicacao(date, tipo);
			this.getContributorsAjustado().add(contributor);
		}
	}
	
	private void incrementaIssuesRegistradosPeloContributor(User user2, Issue issue) {
		pegaContributorAjustado(user2.getLogin()).incrementaIssueAberto();
	}
}
