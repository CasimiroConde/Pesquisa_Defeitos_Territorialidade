package Contributors;

import java.util.Date;

import lombok.Data;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.User;

public @Data class Contributors {
	
	private String nome;
	private String login;
	private TipoContributor tipoAjustado;
	private String type;
	private Date dataPrimeiraInteração;
	private boolean isDeveloper;
	private boolean isReporter;
	
	public Contributors(User user){
		this.nome = user.getName();
		this.login = user.getLogin();
		this.type = user.getType();
		this.tipoAjustado = null;
		this.dataPrimeiraInteração = null;
		this.isDeveloper = false;
		this.isReporter = false;
	}
	
	public void incluiDataPrimeiraInteração(Date data, TipoContributor tipo){
		if(this.dataPrimeiraInteração == null){
			this.dataPrimeiraInteração = data;
			this.tipoAjustado = tipo;
		}
		else if(this.dataPrimeiraInteração.after(data)){
			this.dataPrimeiraInteração = data;
			this.tipoAjustado = tipo;
		}
		
		if(tipo.equals(TipoContributor.REPORTER)){
			this.isReporter = true;
		} else if (tipo.equals(TipoContributor.DEVELOPER)){
			this.isDeveloper = true;
		}
		
	}
}
