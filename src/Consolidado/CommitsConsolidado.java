package Consolidado;

import lombok.Data;

public @Data class CommitsConsolidado {

	String autor;
	String email;
	String texto;


	public CommitsConsolidado(String autor, String email, String texto) {

		this.autor = autor;
		this.email = email;
		this.texto = texto;
	}

}
