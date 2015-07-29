package marcacoesIssues;

import java.util.ArrayList;

import lombok.Data;



public @Data class LabelConsolidado {
	
	private String labelNormatizada;
	private ArrayList<String> labelVaridade = new ArrayList<String>();
	
	
	public LabelConsolidado(String consolidado, ArrayList<String> lista){
		this.labelNormatizada = consolidado;
		this.labelVaridade.addAll(lista);
	}
	
	public void print(){
		System.out.print(this.labelNormatizada + ";");
		for(String s : labelVaridade){
			System.out.print(s + ";");
		}
		System.out.println();
	}
}
