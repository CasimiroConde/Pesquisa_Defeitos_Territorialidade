package Consolidado;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import Contributors.TipoContributor;
import leituraEscrita.ConsolidadorDeArquivos;
import lombok.Data;
import marcacoesIssues.LabelConsolidado;

public @Data class IssueConsolidado {

	String userName;
	String repoName;
	String estado;
	String id;
	String numero;
	String nomeUsuario;
	String createdAt;
	String closedAt;
	String titulo;
	String comentario;
	String texto;
	String updatedAt;
	String timeToFix;
	Boolean fechadoPorCommit;
	Boolean hasLabel;
	int numeroLabel;
	Boolean temTopDezLabel;
	Boolean temTopVinteLabel;
	ArrayList<LabelsIssueConsolidado> labels;

	final static String LABEL = "C:/Users/MARCIO.BARROS/Documents/Pesquisa_Defeitos_Territorialidade/arquivos/analiseMarcacao/PorIssue/Analise_Marcacao_Por_Issue_Label20-05-2016_01-09.txt";
	
	
	public IssueConsolidado(String user, String repository,
			String state, String id, String number, String nomeUsuario,
			String createdAt, String closedAt, String titulo,
			String comentario, String texto, String updatedAt, String timeToFix, String idRepositorio) throws FileNotFoundException {

		this.userName = user;
		this.repoName = repository;
		this.estado = state;
		this.id = id;
		this.numero = number;
		this.nomeUsuario= nomeUsuario;
		this.createdAt = createdAt;
		this.closedAt = closedAt;
		this.titulo = titulo.replace(";", "-");
		this.comentario = comentario;
		this.texto = texto.replace(";", "-");
		this.updatedAt = updatedAt;
		this.timeToFix = timeToFix;
		this.labels = carregaInfoLabelsIssues(idRepositorio);
		this.fechadoPorCommit = false;
		this.temTopDezLabel = verificaTopLabel(10);
		this.temTopVinteLabel = verificaTopLabel(20);
		
		
		if(this.labels.isEmpty())
			this.numeroLabel = 0;
		else
			this.numeroLabel = this.labels.size();
		
		if(this.numeroLabel >= 1){
			this.hasLabel = true;
		}else{
			this.hasLabel = false;
		}
		
	}
	
	
	private Boolean verificaTopLabel(int i) {
		// TODO Auto-generated method stub
		return false;
	}


	private ArrayList<LabelsIssueConsolidado> carregaInfoLabelsIssues(String id) throws FileNotFoundException {
		Scanner file = new Scanner(new BufferedReader(new FileReader(LABEL)));
		ArrayList<LabelsIssueConsolidado> labels= new ArrayList<LabelsIssueConsolidado>();
		String linha = file.nextLine();

		while(file.hasNext()){
			linha = file.nextLine();
			String [] tokens = linha.split(";");
			if(tokens.length > 1){
				String idArquivoLabel = ConsolidadorDeArquivos.retornaId(tokens, "label");
				if(id.equals(idArquivoLabel)){
					
					if(this.getNumero().equals(tokens[3])){
						LabelsIssueConsolidado label = new LabelsIssueConsolidado(tokens[0],
																						tokens[1],
																						tokens[2],
																						tokens[3],
																						tokens[4],
																						tokens[5],
																						tokens[6]);
						labels.add(label);
						}
				}
			}

		}
		
		return labels;

	}


	public void informaCorrigidoPorCommito() {
		this.fechadoPorCommit = true;		
	}


	public void informaLabelsCorrigidasPorCommit() {
		for(LabelsIssueConsolidado l : this.getLabels()){
			l.informaFechadoPorCommit();
		}
	}




}
