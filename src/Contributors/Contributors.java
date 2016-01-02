package Contributors;

import java.util.Date;

import lombok.Data;

import org.eclipse.egit.github.core.User;

public @Data class Contributors {
	
	private String nome;
	private String login;
	private TipoContributor tipoAjustado;
	private Date dataPrimeiraInteracao;
	private String email;
	private boolean isDeveloper;
	private boolean isReporter;
	
	public Contributors(String email, String name, String login){
		this.nome = name;
		this.login = login;
		this.tipoAjustado = null;
		this.email = email;
		this.dataPrimeiraInteracao = null;
		this.isDeveloper = false;
		this.isReporter = false;
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
