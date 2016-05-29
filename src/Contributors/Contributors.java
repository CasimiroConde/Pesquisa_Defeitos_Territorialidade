package Contributors;

import java.util.Date;

import lombok.Data;

import org.eclipse.egit.github.core.User;
import org.joda.time.DateTime;
import org.joda.time.Days;

public @Data class Contributors {
	
	private String nome;
	private String login;
	private TipoContributor tipoAjustado;
	private Date dataPrimeiraInteracao;
	private String email;
	private boolean isDeveloper;
	private boolean isReporter;
	private Date dataCriacao;
	private int tempoCriacao;
	private int followers;
	private int following;
	private int ownedPrivateRepos;
	private int totalPrivateRepos;
	private int publicRepors;
	private int issuesAbertos;
	private int issuesFechados;
	private int issuesFechadosPorCommit;
	private int issuesFechadosPorCommitContinhamLabel;
	private int commitsRegistrados;
	
	public Contributors(User user){
		this.nome = user.getName();
		this.login = user.getLogin();
		this.tipoAjustado = null;
		this.email = user.getEmail();
		this.dataPrimeiraInteracao = null;
		this.isDeveloper = false;
		this.isReporter = false;
		this.dataCriacao = user.getCreatedAt();
		Date date = new Date();
		this.tempoCriacao = Days.daysBetween(new DateTime(this.dataCriacao), new DateTime(date)).getDays();
		this.followers =  user.getFollowers();
		this.following = user.getFollowing();
		this.publicRepors = user.getPublicRepos();
		this.ownedPrivateRepos = user.getOwnedPrivateRepos();
		this.totalPrivateRepos = user.getTotalPrivateRepos();
		this.issuesAbertos = 0;
		this.issuesFechados = 0;
		this.issuesFechadosPorCommit = 0;
		this.issuesFechadosPorCommitContinhamLabel = 0;
		this.commitsRegistrados = 0;
	}
	
	public void incrementaIssueAberto(){
		this.issuesAbertos++;
	}
	
	public void incrementaIssueFechado(){
		this.issuesFechados++;
	}
	
	public void incrementaIssueFechadoPorCommit(){
		this.issuesFechadosPorCommit++;
	}
	
	public void incrementaCommitRegistrado(){
		this.commitsRegistrados++;
	}
	
	public void incrementaIssueFechadoPorCommitContinhaLabel(){
		this.issuesFechadosPorCommitContinhamLabel++;
	}
	public void ajusteTipiicacao(Date data, TipoContributor tipo){
		if(this.dataPrimeiraInteracao == null){
			this.dataPrimeiraInteracao = data;
			this.tipoAjustado = tipo;
		}
		else if(this.dataPrimeiraInteracao.after(data)){
			this.dataPrimeiraInteracao = data;
		}
		
		if(tipo.equals(TipoContributor.REPORTER)){
			this.isReporter = true;
		} else if (tipo.equals(TipoContributor.DEVELOPER)){
			this.isDeveloper = true;
		}
		
		if(this.isDeveloper){
			this.setTipoAjustado(TipoContributor.DEVELOPER);
		}
		
	}
	
	
}
