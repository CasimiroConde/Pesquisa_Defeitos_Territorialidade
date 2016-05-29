package marcacoesIssues;

import lombok.Data;

public @Data class MarcacaoIssue {
	private String nome;
	private int quantidade = 0;
	private int issueFechadoPorCommitContinhaLabel;
	private int timeToFix;
	private TipoMarcacao tipo;
	
	public MarcacaoIssue(String nome, TipoMarcacao tipo){
		this.nome = nome;
		this.tipo = tipo;
		this.quantidade = 1;
		this.issueFechadoPorCommitContinhaLabel = 0;
	}
	
	public MarcacaoIssue(String nome, TipoMarcacao tipo, int quantidade){
		this.nome = nome;
		this.tipo = tipo;
		this.quantidade = quantidade;
		this.issueFechadoPorCommitContinhaLabel = 0;
	}
	
	public void incrementaContador() {
		this.quantidade++;
	}
	
	public void acrescentaNumeroContador(int cont){
		this.quantidade = this.quantidade + cont;
	}

	public void incrementaTimeToFix(int timeToFix){
		this.timeToFix += timeToFix;
	}
	
	public float calculaMediaTimeToFix(){
		return this.timeToFix / this.quantidade;
	}
}
