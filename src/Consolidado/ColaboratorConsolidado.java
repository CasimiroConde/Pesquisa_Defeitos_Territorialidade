package Consolidado;

import java.util.Date;

import Contributors.TipoContributor;
import lombok.Data;

public @Data class ColaboratorConsolidado {

	String userName;
	String repoName;
	String login;
	String nome;
	String email;
	TipoContributor tipoAjustado;
	boolean isDeveloper;
	boolean isReporter;
	String dataPrimeiraInteracao;
	private int followers;
	private int following;
	private int issuesAbertos;
	private int issuesFechados;
	private int issuesFechadosPorCommit;
	private int TentativaIssueFechadoPorCommit;
	private int issuesFechadosPorCommitComLabels;
	private int ownedPrivateRepos;
	private int publicRepos;
	private int tempoCriado;
	private int totalPrivateRepos;
	private int commitsRegistrados;

	public ColaboratorConsolidado(String user, String repository,
			String login, String nome, String email, String tipoAjustado,
			String isDeveloper , String isReporter,
			String followers, String following, String issuesAbertos, String issuesFechados, 
			String issuesFechadosPorCommit, String issuesFechadosPorCommitComLabels, String ownedPrivateRepos,
			String publicRepos, String tempoCriado, String totalPrivateRepois, String commitsRegistrados, String data) {

		this.userName = user;
		this.repoName = repository;
		this.login = login;
		this.nome = nome;
		this.email = email;
		this.tipoAjustado = TipoContributor.get(tipoAjustado);
		this.isDeveloper = Boolean.parseBoolean(isDeveloper);
		this.isReporter = Boolean.parseBoolean(isReporter);
		this.followers = Integer.parseInt(followers);
		this.following = Integer.parseInt(following);
		this.issuesAbertos = Integer.parseInt(issuesAbertos);
		this.issuesFechados = Integer.parseInt(issuesFechados);
		this.TentativaIssueFechadoPorCommit = 0;
		this.issuesFechadosPorCommit = Integer.parseInt(issuesFechadosPorCommit);
		this.issuesFechadosPorCommitComLabels = Integer.parseInt(issuesFechadosPorCommitComLabels);
		this.ownedPrivateRepos = Integer.parseInt(ownedPrivateRepos);
		this.publicRepos = Integer.parseInt(publicRepos);
		this.tempoCriado = Integer.parseInt(tempoCriado);
		this.totalPrivateRepos =  Integer.parseInt(totalPrivateRepois);
		this.commitsRegistrados = Integer.parseInt(commitsRegistrados);
		this.dataPrimeiraInteracao = data;
	}	
	
	
	public ColaboratorConsolidado(String user, String repository,
			String nome, String email, String tipoAjustado) {

		this.userName = user;
		this.repoName = repository;
		this.nome = nome;
		this.email = email;
		this.isDeveloper = false;
		this.isReporter = false;
		this.tipoAjustado = TipoContributor.get(tipoAjustado);
		
		if(this.tipoAjustado.equals(TipoContributor.DEVELOPER))
			this.isDeveloper = true;

		if(this.tipoAjustado.equals(TipoContributor.REPORTER))
			this.isReporter = true;
		
		this.commitsRegistrados = 1;
		this.issuesAbertos = 0;
		this.issuesFechados = 0;
		this.issuesFechadosPorCommit = 0;
		this.issuesFechadosPorCommitComLabels = 0;
		
		
	}


	public void acrescentaCommitsRegistrados() {
		this.commitsRegistrados++;		
	}
	
	public void acrescentaIssueFechadoPorCommit(){
		this.issuesFechadosPorCommit++;
	}
	
	public void acrescentaIssueFechadoPorCommitComLabels(){
		this.issuesFechadosPorCommitComLabels++;
	}


	public void acrescentaCommitsRegistrados(int commitsRegistrados2) {
		this.commitsRegistrados += commitsRegistrados2;
	}


	public void acrescentaIssueFechadoPorCommit(int issuesFechadosPorCommit2) {
		this.issuesFechadosPorCommit += issuesFechadosPorCommit2;
	}


	public void acrescentaIssueFechadoPorCommitComLabels(int issuesFechadosPorCommitComLabels2) {
		this.issuesFechadosPorCommitComLabels += issuesFechadosPorCommitComLabels2;
	}


	public void acrescentaIssueAbertos(int issuesAbertos2) {
		this.issuesAbertos += issuesAbertos2;
	}


	public void developerTrue() {
		this.isDeveloper = true;
	}


	public void reporterTrue() {
		this.isReporter = true;
	}


	public void acrescentaTentativaIssueFechadoPorCommit() {
		this.TentativaIssueFechadoPorCommit++;
	}


	public void acrescentaTentativaIssueFechadoPorCommit(int tentativaIssueFechadoPorCommit2) {
		this.TentativaIssueFechadoPorCommit += tentativaIssueFechadoPorCommit2;
	}


}
