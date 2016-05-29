package issuesRepositorios;

import java.util.ArrayList;
import java.util.List;

import marcacoesIssues.LabelConsolidado;
import marcacoesIssues.MarcacaoIssue;
import marcacoesIssues.MetodosAuxiliaresLabel;
import marcacoesIssues.TipoMarcacao;

import org.eclipse.egit.github.core.Label;

import Consolidado.LabelsIssueConsolidado;

public class MetodosAuxiliares {
	
	public static boolean ePalavraChave(String mensagem){
		mensagem = mensagem.toLowerCase();
		
		if(mensagem.equals("close") || 
			mensagem.equals("closes") || 
			mensagem.equals("closed") || 
			mensagem.equals("fix") || 
			mensagem.equals("fixes") || 
			mensagem.equals("fixed") || 
			mensagem.equals("resole") || 
			mensagem.equals("resolves") || 
			mensagem.equals("resolved")){
			return true;
		}
		
		return false;
		
	}
	
	public static boolean contemPalavraChave(String mensagem){
		mensagem = mensagem.toLowerCase();
		
		if(mensagem.contains("close") || 
			mensagem.contains("closes") || 
			mensagem.contains("closed") || 
			mensagem.contains("fix") || 
			mensagem.contains("fixes") || 
			mensagem.contains("fixed") || 
			mensagem.contains("resole") || 
			mensagem.contains("resolves") || 
			mensagem.contains("resolved")){
			return true;
		}
		
		return false;
		
	}

	
	public static boolean eBug(List<Label> labels){
		
		for(Label l : labels){
			if(l.getName().equals("bug"))
				return true;
		}
		
		return false;
	}
	
	public static void analiseMarcacao(
			ArrayList<LabelConsolidado> consolidadoLabel,
			ArrayList<MarcacaoIssue> marcacoes, Repositorio r) {
		try{
			MetodosAuxiliaresLabel.insereMarcacaoLabelConsolidado(marcacoes, consolidadoLabel,r.getMarcacaoIssue());
			for(MarcacaoIssue m : r.getMarcacaoIssue()){
				if(m.getTipo().equals(TipoMarcacao.MILESTONE))
					MetodosAuxiliaresLabel.insereMarcacaoMilestoneConsolidado(marcacoes, m);
			}
		} catch (NullPointerException n){
			System.out.println("An�lise de Marca��o pulada!");
		}
	}

	public static boolean eBug(ArrayList<LabelsIssueConsolidado> labels) {

		for(LabelsIssueConsolidado l : labels){
			if(l.getLabel().toLowerCase().equals("bug"))
			return true;
		}
		return false;
	}
	
} 


