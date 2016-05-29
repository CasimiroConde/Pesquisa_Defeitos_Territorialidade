package consolidadoWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import Consolidado.IssueConsolidado;
import Consolidado.RepositorioConsolidado;

/**
 * Classe que representa um leitor de arquivos de entradas consolidadas
 *
 * @author marciobarros
 */
public class IssueConsolidadoWriter
{
	/**
	 *
	 */
	public void saveToFile(String filename, ListaIssueConsolidado lista) throws IOException
	{
		saveToWriter(new FileWriter(filename, true), lista);
	}

	/**
	 *
	 */
	public String saveToString(ListaIssueConsolidado lista) throws IOException
	{
		StringWriter writer = new StringWriter();
		saveToWriter(writer, lista);
		return writer.toString();
	}

	/**
	 *
	 */
	private void saveToWriter(Writer writer, ListaIssueConsolidado lista) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(writer);

		for (IssueConsolidado entrada : lista)
			saveConsolidatedEntry(bw, entrada);

		bw.close();
	}

	/**
	 *
	 */
	private void saveConsolidatedEntry(BufferedWriter writer, IssueConsolidado entrada) throws IOException
	{
		writer.append(entrada.getUserName() + ";");
		writer.append(entrada.getRepoName() + ";");
		writer.append(entrada.getEstado()+ ";");
		writer.append(entrada.getId() + ";");
		writer.append(entrada.getNumero() + ";");
		writer.append(entrada.getNomeUsuario() + ";");
		writer.append(entrada.getCreatedAt() + ";");
		writer.append(entrada.getClosedAt() + ";");
		writer.append(entrada.getTitulo() + ";");
		writer.append(entrada.getComentario()+ ";");
		writer.append(entrada.getTexto() + ";");
		writer.append(entrada.getUpdatedAt()+ ";");
		writer.append(entrada.getTimeToFix() + ";");
		writer.append(entrada.getFechadoPorCommit() + ";");
		writer.append(entrada.getNumeroLabel() + ";");
		writer.append(entrada.getHasLabel() + ";");
		writer.append(entrada.getTemTopDezLabel() + ";");
		writer.append(entrada.getTemTopVinteLabel() + ";");
		
		writer.append(System.getProperty("line.separator"));
	}
}
