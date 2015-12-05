package Consolidado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Classe que representa um leitor de arquivos de entradas consolidadas
 * 
 * @author marciobarros
 */
public class ConsolidadoReader
{
	/**
	 * Carrega uma lista de entradas consolidadas a partir de um arquivo
	 */
	public ListaEntradaConsolidado loadFromFile(String filename) throws IOException
	{
		return loadFromReader(new FileReader(filename));
	}

	/**
	 * Carrega uma lista de entradas consolidadas a partir de uma string
	 */
	public ListaEntradaConsolidado loadFromString(String content) throws IOException
	{
		return loadFromReader(new StringReader(content));
	}

	/**
	 * Carrega o conteudo de uma lista de entradas consolidadas
	 */
	private ListaEntradaConsolidado loadFromReader(Reader reader) throws IOException
	{
		BufferedReader br = new BufferedReader(reader);
		ListaEntradaConsolidado lista = new ListaEntradaConsolidado();
	
		try
		{
			String line = br.readLine();
			
			if (line != null)
				line = br.readLine();

			while (line != null)
			{
				EntradaConsolidado entrada = loadConsolidatedEntry(line);
				
				if (entrada != null)
					lista.add(entrada);
				
				line = br.readLine();
			}
		} 
		finally
		{
			br.close();
		}
		
		return lista;
	}

	/**
	 * Carrega uma linha do arquivo de entradas consolidadas
	 */
	private EntradaConsolidado loadConsolidatedEntry(String line)
	{
		String[] tokens = line.split(";");
		
		if (tokens.length < 22)
			return null;
		
		EntradaConsolidado entrada = new EntradaConsolidado();
		entrada.setIndice(converteInteiro(tokens[0]));
		entrada.setUsuario(tokens[1]);
		entrada.setRepositorio(tokens[2]);
		// ID = tokens[3]
		entrada.setOwner(tokens[4]);
		entrada.setDataCriado(tokens[5]);
		// idadeDias = tokens[6]
		entrada.setContributors(converteInteiro(tokens[7]));
		// contributorsAjustado = tokens[8]
		entrada.setLinguagem(tokens[9]);
		entrada.setNissue(converteInteiro(tokens[10]));
		// NissueAjustado = tokens[11]
		entrada.setOpenIssues(converteInteiro(tokens[12]));
		entrada.setClosedIssues(converteInteiro(tokens[13]));
		entrada.setOpenIssueBug(converteInteiro(tokens[14]));
		entrada.setClosedIssueBug(converteInteiro(tokens[15]));
		entrada.setFollowers(converteInteiro(tokens[16]));
		// FollowersAjustado = tokens[17]
		entrada.setFollowing(converteInteiro(tokens[18]));
		entrada.setForks(converteInteiro(tokens[19]));
		entrada.setWatchers(converteInteiro(tokens[20]));
		entrada.setSizeInKilobytes(converteInteiro(tokens[21]));
		return entrada;		
	}

	/**
	 * Converte uma string para número inteiro
	 */
	private int converteInteiro(String s)
	{
		return Integer.parseInt(s);
	}
}