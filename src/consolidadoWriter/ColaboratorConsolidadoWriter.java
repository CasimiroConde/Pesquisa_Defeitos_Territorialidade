package consolidadoWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import lombok.Data;
import Consolidado.ColaboratorConsolidado;

/**
 * Classe que representa um leitor de arquivos de entradas consolidadas
 *
 * @author casimiroconde
 */
public @Data class ColaboratorConsolidadoWriter{
	/**
	 *
	 */
	public void saveToFile(String filename, ListaColaboratorConsolidado lista) throws IOException
	{
		saveToWriter(new FileWriter(filename, true), lista);
	}

	/**
	 *
	 */
	public String saveToString(ListaColaboratorConsolidado lista) throws IOException
	{
		StringWriter writer = new StringWriter();
		saveToWriter(writer, lista);
		return writer.toString();
	}

	/**
	 *
	 */
	private void saveToWriter(Writer writer, ListaColaboratorConsolidado lista) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(writer);

		for (ColaboratorConsolidado entrada : lista)
			saveConsolidatedEntry(bw, entrada);

		bw.close();
	}

	/**
	 *
	 */
	private void saveConsolidatedEntry(BufferedWriter writer, ColaboratorConsolidado entrada) throws IOException
	{
		writer.append(entrada.getUserName() + ";");
		writer.append(entrada.getRepoName() + ";");
		writer.append(entrada.getLogin() + ";");
		writer.append(entrada.getNome() + ";");
		writer.append(entrada.getEmail() + ";");
		writer.append(entrada.getTipoAjustado() + ";");
		writer.append(entrada.isDeveloper() + ";");
		writer.append(entrada.isReporter() + ";");
		writer.append(entrada.getFollowers() + ";");
		writer.append(entrada.getFollowing() + ";");
		writer.append(entrada.getCommitsRegistrados() + ";");
		writer.append(entrada.getIssuesAbertos() + ";");
		writer.append(entrada.getIssuesFechados() + ";");
		writer.append(entrada.getTentativaIssueFechadoPorCommit() + ";");
		writer.append(entrada.getIssuesFechadosPorCommit() + ";");
		writer.append(entrada.getIssuesFechadosPorCommitComLabels() + ";");
		writer.append(entrada.getOwnedPrivateRepos() + ";");
		writer.append(entrada.getPublicRepos() + ";");
		writer.append(entrada.getTempoCriado() + ";");
		writer.append(entrada.getTotalPrivateRepos() + ";");
		writer.append(entrada.getDataPrimeiraInteracao() + ";");
		writer.append(System.getProperty("line.separator"));
		
	
	}
}
