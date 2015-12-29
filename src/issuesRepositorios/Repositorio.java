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
	private String user;
	private int numeroFollowersOwner;
	private int numeroFollowingOwner;
	private int numeroForks;
	private boolean hasIssue;
	private int numeroWatchers;
	private int tamanhoRepositorio;
	private Date create;
	private int idade;
	private IRepositoryIdProvider repoId;
	private int numeroIssues;
	private int numeroContributors;
	private ArrayList<Issue> issues;
	private List<Contributor> contributors;
	private List<Contributors> contributorsAjustado;
	private int openIssue = 0;
	private int closedIssue = 0;
	private int openIssueBug = 0;
	private int closedIssueBug = 0;
	private int contadorIssuesCorrigidosCommits = 0;
	private int contadorIssuesBugCorrigidosCommits = 0;
	private double porcentualIssuesFechadosCommit = 0.0;
	private double porcentualIssuesBugFechadosCommit = 0.0;
	private double porcentualIssuesBugFechadosCommitTotal = 0.0;
	private ArrayList<MarcacaoIssue> marcacaoIssue;
	private String language;
	
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
				System.out.println("Carregando Reposit�rio!! Reposit�rio: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);
			} catch (RequestException e) {
				System.out.println("Reposit�rio n�o acessado!! Reposit�rio: " + this.getRepositoryName());
				this.setRepositoryName("Vazio");
				finished = true;
			}
		}
	}*/
	
	public Repositorio(GitHubClient client, String userName, String repositoryName) throws IOException, RequestException{
		RepositoryService repositoryService = new RepositoryService(client);
		
		this.userName = userName;
		this.repositoryName = repositoryName;
		this.repoId = new RepositoryId(this.userName, this.repositoryName);
		Repository repositorio = repositoryService.getRepository(repoId);
	}
	
	public Repositorio(String userName, String repositoryName, GitHubClient client) throws InterruptedException, IOException{
		RepositoryService repositoryService = new RepositoryService(client);
		IssueService issueService = new IssueService(client);
		UserService userService = new UserService(client);
		
		boolean finished = false;
		WatcherService watcherService = new WatcherService(client);
		while(!finished){		
			try {	
				this.userName = userName;
				this.repositoryName = repositoryName;
				this.repoId = new RepositoryId(this.userName, this.repositoryName);
				Repository repositorio = repositoryService.getRepository(repoId);
				User usuario = userService.getUser(repositorio.getOwner().getLogin());
				this.user = usuario.getName();
				this.numeroForks = /*repositoryService.getForks(this.repoId).size();*/repositorio.getForks();
				this.create = repositorio.getCreatedAt();
				this.language = repositorio.getLanguage();
				this.idade = calculaIdade();
				this.hasIssue = repositorio.isHasIssues();
				this.tamanhoRepositorio = repositorio.getSize();
				this.numeroFollowersOwner = usuario.getFollowers();
				this.numeroFollowingOwner = usuario.getFollowing();
				this.numeroWatchers = /*watcherService.getWatchers(this.repoId).size();*/ repositorio.getWatchers();
				this.marcacaoIssue = new ArrayList<MarcacaoIssue>();
				this.issues = new ArrayList<Issue>();
				this.contributors = repositoryService.getContributors(this.repoId, true);
				this.contributorsAjustado = new ArrayList<Contributors>();
				this.numeroContributors = this.getContributors().size();
				this.coletaIssues(issueService, userService);
				this.numeroIssues = this.getIssues().size();
				finished = true;
			} catch (NoSuchPageException e) {
				System.out.println("Carregando Reposit�rio!! Aguardando Request Limit!! Reposit�rio: " + this.getRepositoryName() + " Erro: " + e.getMessage());
				Thread.sleep(600 * 1000);
			} catch (RequestException e) {
				if(e.getStatus() == 403){
					System.out.println("Carregando Reposit�rio!! Aguardando Request Limit!! Reposit�rio: " + this.getRepositoryName() + " Erro: " + e.getStatus() + "-" + e.getMessage());
					Thread.sleep(600 * 1000);
				} else {
					this.repositoryName = "Vazio";
					System.out.println("Reposit�rio n�o acessado!! Reposit�rio: " + this.getRepositoryName() + " Exception:" + e.getMessage());
					finished = true;
				}
			}
		}
	}
	
	public int calculaIdade(){
		int tempo;
		Date date = new Date();
		tempo = Days.daysBetween(new DateTime(this.create), new DateTime(date)).getDays();
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
						incluiContributorsIssue(userService, issue);
			    	}
		    	}		    
			    encerrado = true;
			} catch (NoSuchPageException e ){
				System.out.println("Coleta Issue!! Reposit�rio: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);			
			}
		}
	}

	private void incluiContributorsIssue(UserService userService, Issue issue)
			throws IOException {
		try{
			User user = userService.getUser(issue.getUser().getLogin());
			this.incluiContributorAjustado(user.getEmail(), user.getName(), user.getLogin(), issue.getCreatedAt(), TipoContributor.REPORTER);
		} catch (NullPointerException n){
			System.out.println("Usu�rio n�o encontrado");
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
				System.out.println("Coletando Colaboradores! M�ximo de Requisi��es Alcan�ada, tentaremos novamente em 10 min");
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
				System.out.println("Defeitos Corrigidos! M�ximo de Requisi��es Alcan�ada, tentaremos novamente em 10 min");
				Thread.sleep(600 * 1000);
				//return repositorios;		
			
			}catch(RequestException r){
				System.out.println("Defeitos Corrigidos! M�ximo de Requisi��es Alcan�ada, tentaremos novamente em 10 min");
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
		
		String caminhoArmazenamento = "C://Users//Casimiro//Documents//Casimiro Conde//Aulas//Mestrado//Semin�rio de Acompanhamento Discente 2//Territorialidade//Commits//"+nomePasta;
		
	
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
					System.out.println("Defeitos Corrigidos! M�ximo de Requisi��es Alcan�ada, tentaremos novamente em 10 min");
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
					System.out.println("N�o achou Issue: " + numeroIssue);
					return false;
	
			}
			return false;
		} catch (NumberFormatException n){
			System.out.println("Erro ao buscar o n�mero: " + numeroIssue);
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
								System.out.println("Downloado de Commit aguardando Request!! Reposit�rio: " + this.getRepositoryName());
								Thread.sleep(600 * 1000);					
							}else{
								System.out.println("Download de Commit Encerrado!! Reposit�rio: " + this.getRepositoryName());
								encerrado = true;
								i++;
							}
						}
					}
				}	
			}else{
					Writer.armazenaCommits(this.getUserName(), this.getRepositoryName(), "0","N�o Existe Commit");
			}
		} catch (NoSuchPageException n){
			//Writer.criaArquivo(criaNome(cont), buffer);
			System.out.println("Download de Commit! M�ximo de Requisi��es Alcan�ada, tentaremos novamente em 10 min");
			Thread.sleep(600 * 1000);
			//return repositorios;		
		
		}catch(RequestException r){
			if(r.getStatus() == 403){
				System.out.println("Downloado de Commit aguardando Request!! Reposit�rio: " + this.getRepositoryName());
				Thread.sleep(600 * 1000);					
			}else{
				System.out.println("Download de Commit Encerrado!! Reposit�rio: " + this.getRepositoryName());
			}
			
		} catch (ConnectException c){
			System.out.println("Download de Commit! Conex�o interrompida, tentaremos novamente em 1 min!!  Reposit�rio: " + this.getRepositoryName());
			Thread.sleep(60 * 1000);
		}
	}

	private void incluiContibutorsCommit(UserService userService,
			RepositoryCommit c) throws IOException {
		try{
			User user = userService.getUser(c.getCommitter().getLogin());
			incluiContributorAjustado(user.getEmail(), user.getName(), user.getLogin() ,c.getCommit().getAuthor().getDate() , TipoContributor.DEVELOPER);
		}catch(NullPointerException n){
			System.out.println("Usu�rio n�o encontrado");
		} catch(IllegalArgumentException e){
			System.out.println("Usu�rio n�o encontrado");
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
			System.out.println("Coletando Colaboradores! M�ximo de Requisi��es Alcan�ada, tentaremos novamente em 10 min");
			Thread.sleep(600 * 1000);
		}
		
	}
	
	public boolean existeContributorAjustado(String email){
		if(this.getContributorsAjustado().isEmpty())
			return false;
		
		for(Contributors c : this.getContributorsAjustado()){
			if(c.getEmail() != null){
				if(c.getEmail().equals(email)){
					return true;
				}
			}
		}
		return false;
	}
	public Contributors pegaContributorAjustado(String email){
		for(Contributors c : this.getContributorsAjustado()){
			if(c.getEmail() != null){
				if(c.getEmail().equals(email)){
					return c;
				}
			}
		}
		return null;
	}
	
	public void incluiContributorAjustado(String email, String name, String login, Date date, TipoContributor tipo){
		if(existeContributorAjustado(email)){
			pegaContributorAjustado(email).incluiDataPrimeiraInteracao(date, tipo);
		} else {
			Contributors contributor = new Contributors(email, name, login);
			contributor.incluiDataPrimeiraInteracao(date, tipo);
			this.getContributorsAjustado().add(contributor);
		}
	}
}
