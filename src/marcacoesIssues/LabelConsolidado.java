package marcacoesIssues;

import java.util.ArrayList;

import lombok.Data;



public @Data class LabelConsolidado {
	
	private String labelNormatizada;
	private AgrupadorMarcacao tipo;
	private ArrayList<String> labelVaridade = new ArrayList<String>();
	private int contador;
	private int contadorFechadoPorCommit;
	
	
	public LabelConsolidado(String consolidado,AgrupadorMarcacao tipo, ArrayList<String> lista){
		this.labelNormatizada = consolidado;
		this.tipo = tipo;
		this.labelVaridade.addAll(lista);
		this.contador = 0;
		this.contadorFechadoPorCommit = 0;
	}
	
	public void print(){
		System.out.print(this.labelNormatizada + ";");
		System.out.print(this.tipo + ";");
		for(String s : labelVaridade){
			System.out.print(s + ";");
		}
		System.out.println();
	}

	public void incrementaContador() {
		this.contador++;
	}

	public void incrementaContadorFechadoPorCommit() {
		this.contadorFechadoPorCommit++;
	}
}
