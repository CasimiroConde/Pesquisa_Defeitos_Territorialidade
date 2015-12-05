package Consolidado;

import lombok.Data;

/**
 * Classe que representa uma entrada do arquivo consolidado
 * 
 * @author marciobarros
 */
public @Data class EntradaConsolidado
{
	private int indice;
	private String usuario;
	private String repositorio;
	private String owner;
	private String dataCriado;
	private int contributors;
	private String linguagem;
	private int Nissue;
	private int openIssues;
	private int closedIssues;
	private int openIssueBug;
	private int closedIssueBug;
	private int followers;
	private int following;
	private int forks;
	private int watchers;
	private int sizeInKilobytes;
	
	public String getId()
	{
		return usuario + repositorio;
	}

	public String getIdadeDias()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getContributorsAjustado()
	{
		return (contributors > 0) ? "" + contributors : "NA";
	}

	public String getNissueAjustado()
	{
		return (Nissue > 0) ? "" + Nissue : "NA";
	}

	public String getFollowersAjustado()
	{
		return (followers > 0) ? "" + followers : "NA";
	}

	public double getSizeInMegabytes()
	{
		return sizeInKilobytes / 1024;
	}

	public String getIssueXMB()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getIssueXMBAjustado()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getContemIssue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdadeSemana()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getReportersIssue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getSomaTimeToFix()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getMediaTimeToFix()
	{
		// TODO Auto-generated method stub
		return null;
	}
}