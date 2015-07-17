package issuesRepositorios;

import lombok.Getter;


public enum TipoMarcacao {
	LABEL("label"), MILESTONE("milestone");
	
	private @Getter String nome;

	private TipoMarcacao(String nome) {
		this.nome = nome;
	}

	public static TipoMarcacao get(String nome) {
		for (TipoMarcacao d : values())
			if (d.getNome().compareToIgnoreCase(nome) == 0)
				return d;

		return null;
	}

}
