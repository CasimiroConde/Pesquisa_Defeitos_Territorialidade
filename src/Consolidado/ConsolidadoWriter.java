package Consolidado;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Classe que representa um leitor de arquivos de entradas consolidadas
 * 
 * @author marciobarros
 */
public class ConsolidadoWriter
{
	/**
	 * 
	 */
	public void saveToFile(String filename, ListaEntradaConsolidado lista) throws IOException
	{
		saveToWriter(new PrintWriter(filename, "UTF-8"), lista);
	}

	/**
	 * 
	 */
	public String saveToString(ListaEntradaConsolidado lista) throws IOException
	{
		StringWriter writer = new StringWriter();
		saveToWriter(writer, lista);
		return writer.toString();
	}

	/**
	 * 
	 */
	private void saveToWriter(Writer writer, ListaEntradaConsolidado lista) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(writer);
		
		for (EntradaConsolidado entrada : lista)
			saveConsolidatedEntry(bw, entrada);
		
		bw.close();
	}

	/**
	 * 
	 */
	private void saveConsolidatedEntry(BufferedWriter writer, EntradaConsolidado entrada) throws IOException
	{
		writer.append(entrada.getIndice() + ";");
		writer.append(entrada.getUsuario() + ";");
		writer.append(entrada.getRepositorio() + ";");
		writer.append(entrada.getId() + ";");
		writer.append(entrada.getOwner() + ";");
		writer.append(entrada.getDataCriado() + ";");
		writer.append(entrada.getIdadeDias() + ";");
		writer.append(entrada.getContributors() + ";");
		writer.append(entrada.getContributorsAjustado() + ";");
		writer.append(entrada.getLinguagem() + ";");
		writer.append(entrada.getNissue() + ";");
		writer.append(entrada.getNissueAjustado() + ";");
		writer.append(entrada.getOpenIssues() + ";");
		writer.append(entrada.getClosedIssues() + ";");
		writer.append(entrada.getOpenIssueBug() + ";");
		writer.append(entrada.getClosedIssueBug() + ";");
		writer.append(entrada.getFollowers() + ";");
		writer.append(entrada.getFollowersAjustado() + ";");
		writer.append(entrada.getFollowing() + ";");
		writer.append(entrada.getForks() + ";");
		writer.append(entrada.getWatchers() + ";");
		writer.append(entrada.getSizeInKilobytes() + ";");
		writer.append(entrada.getSizeInMegabytes() + ";");
		writer.append(entrada.getIssueXMB() + ";");
		writer.append(entrada.getIssueXMBAjustado() + ";");
		writer.append(entrada.getContemIssue() + ";");
		writer.append(entrada.getIdadeSemana() + ";");
		writer.append(entrada.getReportersIssue() + ";");
		writer.append(entrada.getSomaTimeToFix() + ";");
		writer.append(entrada.getMediaTimeToFix() + ";");
	}
}