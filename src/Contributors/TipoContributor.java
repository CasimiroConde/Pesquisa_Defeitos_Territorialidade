package Contributors;

import lombok.Getter;

public enum TipoContributor {
	REPORTER("reporter"), DEVELOPER("developer");
	
	private @Getter String nome;

	private TipoContributor(String nome) {
		this.nome = nome;
	}

	public static TipoContributor get(String nome) {
		for (TipoContributor d : values())
			if (d.getNome().compareToIgnoreCase(nome) == 0)
				return d;

		return null;
	}
}
