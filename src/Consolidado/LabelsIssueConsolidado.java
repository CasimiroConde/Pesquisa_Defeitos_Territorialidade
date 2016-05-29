package Consolidado;

import lombok.Data;

public @Data class LabelsIssueConsolidado {

	String user;
	String repository;
	String idIssue;
	String numeroIssue;
	String tipo;
	String label;
	String codigoCor;
	Boolean fechadoPorCommit;


	public LabelsIssueConsolidado(String usuario, String repositorio, String idIssue, String numero, String tipo, String label, String codigo){
		this.user = usuario;
		this.repository = repositorio;
		this.idIssue = idIssue;
		this.numeroIssue = numero;
		this.tipo = tipo;
		this.label = label;
		this.codigoCor = codigo;
		this.fechadoPorCommit = false;
	}


	public void informaFechadoPorCommit() {
		this.fechadoPorCommit = true;	
	}
}
