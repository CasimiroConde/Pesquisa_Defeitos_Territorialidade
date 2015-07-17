package issuesRepositorios;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;

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
	
	static void insereMarcacaoMilestone(ArrayList<MarcacaoIssue> marcacaoIssue, Issue issue) {
		if(marcacaoIssue.isEmpty()){
			MarcacaoIssue marcacao = new MarcacaoIssue(issue.getMilestone().getTitle(), TipoMarcacao.MILESTONE);
			marcacaoIssue.add(marcacao);
		} else {
			boolean incrementadoMilestone = false;
			for(MarcacaoIssue m : marcacaoIssue){
				if(m.getTipo() == TipoMarcacao.MILESTONE){
					if(m.getNome().equals(issue.getMilestone().getTitle())){	
						m.incrementaContador();
						incrementadoMilestone = true;
					}
				}
			}
			if(!incrementadoMilestone){
				MarcacaoIssue marcacao = new MarcacaoIssue(issue.getMilestone().getTitle(), TipoMarcacao.MILESTONE);
				marcacaoIssue.add(marcacao);
			}
		}
	}
	
	static void insereMarcacaoLabel(ArrayList<MarcacaoIssue> marcacaoIssue, Issue issue) {

			for(Label l : issue.getLabels()){
				if(marcacaoIssue.isEmpty()){
					MarcacaoIssue marcacao = new MarcacaoIssue(l.getName(), TipoMarcacao.LABEL);
					marcacaoIssue.add(marcacao);
				} else {
					boolean incrementadoLabel = false;
					for(MarcacaoIssue m : marcacaoIssue){
						if(m.getTipo() == TipoMarcacao.LABEL){
							if(m.getNome().equals(l.getName())){	
								m.incrementaContador();
								incrementadoLabel = true;
							}
						}
					}
					if(!incrementadoLabel){
						MarcacaoIssue marcacao = new MarcacaoIssue(l.getName(), TipoMarcacao.LABEL);
						marcacaoIssue.add(marcacao);
					}
				}
			}
			
		}
	} 


