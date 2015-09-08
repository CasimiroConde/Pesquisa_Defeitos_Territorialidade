package marcacoesIssues;

import lombok.Getter;

public enum AgrupadorMarcacao {
	STATUS("status"), AUTORES("autores"), COMPONENTES("componentes"), VERSOES("versoes"), OUTROS("outros");
	
	private @Getter String nome;

	private AgrupadorMarcacao(String nome) {
		this.nome = nome;
	}

	public static AgrupadorMarcacao get(String nome) {
		for (AgrupadorMarcacao d : values())
			if (d.getNome().compareToIgnoreCase(nome) == 0)
				return d;

		return null;
	}
}
