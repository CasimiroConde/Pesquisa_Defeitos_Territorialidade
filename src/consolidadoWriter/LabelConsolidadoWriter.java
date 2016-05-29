package consolidadoWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import Consolidado.IssueConsolidado;
import Consolidado.LabelsIssueConsolidado;
import Consolidado.RepositorioConsolidado;
import marcacoesIssues.LabelConsolidado;

/**
 * Classe que representa um leitor de arquivos de entradas consolidadas
 *
 * @author marciobarros
 */
public class LabelConsolidadoWriter
{
	/**
	 *
	 */
	public void saveToFile(String filename, ListaLabelConsolidado lista) throws IOException
	{
		saveToWriter(new FileWriter(filename, true), lista);
	}

	/**
	 *
	 */
	public String saveToString(ListaLabelConsolidado lista) throws IOException
	{
		StringWriter writer = new StringWriter();
		saveToWriter(writer, lista);
		return writer.toString();
	}

	/**
	 *
	 */
	private void saveToWriter(Writer writer, ListaLabelConsolidado lista) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(writer);

		for (LabelsIssueConsolidado entrada : lista)
			saveConsolidatedEntry(bw, entrada);

		bw.close();
	}

	/**
	 *
	 */
	private void saveConsolidatedEntry(BufferedWriter writer, LabelsIssueConsolidado entrada) throws IOException
	{
		writer.append(entrada.getUser() + ";");
		writer.append(entrada.getRepository() + ";");
		writer.append(entrada.getIdIssue()+ ";");
		writer.append(entrada.getNumeroIssue() + ";");
		writer.append(entrada.getTipo() + ";");
		writer.append(entrada.getLabel() + ";");
		writer.append(entrada.getCodigoCor() + ";");
		writer.append(entrada.getFechadoPorCommit() + ";");
		writer.append(System.getProperty("line.separator"));
	}
}
