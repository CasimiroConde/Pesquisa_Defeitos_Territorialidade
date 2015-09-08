package marcacoesIssues;

import lombok.Data;

public @Data class MarcacaoIssue {
	private String nome;
	private int quantidade;
	private TipoMarcacao tipo;
	
	
	public MarcacaoIssue(String nome, TipoMarcacao tipo){
		this.nome = nome;
		this.tipo = tipo;
		this.quantidade = 1;
	}
	
	
	public void incrementaContador() {
		this.quantidade++;
	}
	
	public void acrescentaNumeroContador(int cont){
		this.quantidade = this.quantidade + cont;
	}

}
