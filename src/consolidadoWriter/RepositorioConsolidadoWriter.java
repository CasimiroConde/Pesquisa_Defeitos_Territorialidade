package consolidadoWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import Consolidado.RepositorioConsolidado;

/**
 * Classe que representa um leitor de arquivos de entradas consolidadas
 *
 * @author marciobarros
 */
public class RepositorioConsolidadoWriter
{
	/**
	 *
	 */
	public void saveToFile(String filename, ListaRepositorioConsolidado lista) throws IOException
	{
		saveToWriter(new FileWriter(filename, true), lista);
	}

	/**
	 *
	 */
	public String saveToString(ListaRepositorioConsolidado lista) throws IOException
	{
		StringWriter writer = new StringWriter();
		saveToWriter(writer, lista);
		return writer.toString();
	}

	/**
	 *
	 */
	private void saveToWriter(Writer writer, ListaRepositorioConsolidado lista) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(writer);

		for (RepositorioConsolidado entrada : lista)
			saveConsolidatedEntry(bw, entrada);

		bw.close();
	}

	/**
	 *
	 */
	private void saveConsolidatedEntry(BufferedWriter writer, RepositorioConsolidado entrada) throws IOException
	{
		writer.append(entrada.getIndice() + ";");
		writer.append(entrada.getUsuario() + ";");
		writer.append(entrada.getRepositorio() + ";");
		writer.append(entrada.getOwner() + ";");
		writer.append(entrada.getIdUnico() + ";");
		writer.append(entrada.getDataCriado() + ";");
		writer.append(entrada.getIdadeDia() + ";");
		writer.append(entrada.getIdadeDiaAjudatado() + ";");
		writer.append(entrada.getIdadeSemana() + ";");
		writer.append(entrada.getIdadeSemanaAjustado() + ";");
		writer.append(entrada.getContributors() + ";");
		writer.append(entrada.getContributorsAjustado() + ";");
		writer.append(entrada.getLinguagem() + ";");
		writer.append(entrada.isHasIssue() + ";");
		writer.append(entrada.getContemIssue() + ";");
		writer.append(entrada.getNissue() + ";");
		writer.append(entrada.getNIssueAjustado() + ";");
		writer.append(entrada.getOpenIssues() + ";");
		writer.append(entrada.getOpenIssueAJustado() + ";");
		writer.append(entrada.getClosedIssues() + ";");
		writer.append(entrada.getClosedIssuesAJustado() + ";");
		writer.append(entrada.getOpenIssueBug() + ";");
		writer.append(entrada.getOpenIssueBugAjustado() + ";");
		writer.append(entrada.getClosedIssueBug() + ";");
		writer.append(entrada.getClosedIssueBugAjustado() + ";");
		writer.append(entrada.getFollowers() + ";");
		writer.append(entrada.getFollowersAjustado() + ";");
		writer.append(entrada.getFollowing() + ";");
		writer.append(entrada.getFollowingAjustado() + ";");
		writer.append(entrada.getForks() + ";");
		writer.append(entrada.getForksAjustado() + ";");
		writer.append(entrada.getWatchers() + ";");
		writer.append(entrada.getWatchersAjustado() + ";");
		writer.append(entrada.getSizeInKilobytes() + ";");
		writer.append(entrada.getSizeInKilobytesAjustado() + ";");
		writer.append(entrada.getSizeInMegabytes() + ";");
		writer.append(entrada.getIssueXMB() + ";");
		writer.append(entrada.getIssueXMBAjustado() + ";");
		writer.append(entrada.getContadorIssuesCorrigidosCommits() + ";");
		writer.append(entrada.getContadorIssuesCorrigidosCommitsAjustado() + ";");
		writer.append(entrada.getContadorIssuesBugCorrigidosCommits()+ ";");
		writer.append(entrada.getContadorIssuesBugCorrigidosCommitsAjustado()+ ";");
		writer.append(entrada.getContadorIssueLabelCorrigidosPorCommit()+ ";");
		writer.append(entrada.getContadorIssueLabelCorrigidosPorCommitAjustado()+ ";");
		writer.append(entrada.getContadorTentativaDeCorrecaoDeIssues()+ ";");
		writer.append(entrada.getContadorTentativaDeCorrecaoDeIssuesAjustado()+ ";");
		writer.append(entrada.getPorcentualIssuesFechadosCommit()+ ";");
		writer.append(entrada.getPorcentualIssuesBugFechadosCommit()+ ";");
		writer.append(entrada.getPorcentualIssuesBugFechadosCommitTotal() + ";");
		writer.append(entrada.getNumeroDevTrueRepTrue()+ ";");
		writer.append(entrada.getNumeroDevTrueRepTrueAjustado()+ ";");
		writer.append(entrada.getNumeroDevTrueRepFalse()+ ";");
		writer.append(entrada.getNumeroDevTrueRepFalseAjustado()+ ";");
		writer.append(entrada.getNumeroDevFalseRepTrue()+ ";");
		writer.append(entrada.getNumeroDevFalseRepTrueAjustado()+ ";");
		writer.append(entrada.getNumeroDevTrue()+ ";");
		writer.append(entrada.getNumeroDevTrueAjustado()+ ";");
		writer.append(entrada.getNumeroRepTrue()+ ";");
		writer.append(entrada.getNumeroRepTrueAjustado()+ ";");
		writer.append(entrada.getSomaTimeToFix() + ";");
		writer.append(entrada.getSomaTimeToFixAjustado() + ";");
		writer.append(entrada.getMediaTimetoFix() + ";");
		writer.append(entrada.getMediaTimetoFixAjustado() + ";");
		writer.append(entrada.getIsFork() + ";");
		writer.append(entrada.getRepositorioPai() + ";");	
		writer.append(entrada.getNumeroLoc() + ";");
		writer.append(System.getProperty("line.separator"));
	}
}
