package marcacoesIssues;

import java.util.ArrayList;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class MetodosAuxiliaresLabel {
	public static void insereMarcacaoMilestoneRepositorio(ArrayList<MarcacaoIssue> marcacaoIssue, Issue issue) {
		
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
	
	public static void insereMarcacaoMilestoneConsolidado(ArrayList<MarcacaoIssue> marcacaoIssue, MarcacaoIssue marcacaoRepositorio) {
		if(marcacaoIssue.isEmpty()){
			MarcacaoIssue marcacao = new MarcacaoIssue(marcacaoRepositorio.getNome(), TipoMarcacao.MILESTONE);
			marcacaoIssue.add(marcacao);
		} else {
			boolean incrementadoMilestone = false;
			for(MarcacaoIssue m : marcacaoIssue){
				if(m.getTipo() == TipoMarcacao.MILESTONE){
					if(m.getNome().equals(marcacaoRepositorio.getNome())){	
						m.acrescentaNumeroContador(marcacaoRepositorio.getQuantidade());
						incrementadoMilestone = true;
					}
				}
			}
			if(!incrementadoMilestone){
				MarcacaoIssue marcacao = new MarcacaoIssue(marcacaoRepositorio.getNome(), TipoMarcacao.MILESTONE);
				marcacaoIssue.add(marcacao);
			}
		}
	}
	
	public static void insereMarcacaoLabelRepositorio(ArrayList<MarcacaoIssue> marcacaoIssue, Issue issue, ArrayList<LabelConsolidado> labels) {
		
		for(Label l : issue.getLabels()){
			String labelNormatizada = normatizaLabel(labels, l.getName().trim());
			if(marcacaoIssue.isEmpty()){
				MarcacaoIssue marcacao = new MarcacaoIssue(labelNormatizada, TipoMarcacao.LABEL);
				marcacao.incrementaTimeToFix(Days.daysBetween(new DateTime(issue.getCreatedAt()),new DateTime(issue.getClosedAt())).getDays());
				marcacaoIssue.add(marcacao);
			} else {
				boolean incrementadoLabel = false;
				for(MarcacaoIssue m : marcacaoIssue){
					if(m.getTipo() == TipoMarcacao.LABEL){
						if(m.getNome().equals(labelNormatizada)){	
							m.incrementaContador();
							m.incrementaTimeToFix(Days.daysBetween(new DateTime(issue.getCreatedAt()),new DateTime(issue.getClosedAt())).getDays());
							incrementadoLabel = true;
						}
					}
				}
				if(!incrementadoLabel){
					MarcacaoIssue marcacao = new MarcacaoIssue(labelNormatizada, TipoMarcacao.LABEL);
					marcacao.incrementaTimeToFix(Days.daysBetween(new DateTime(issue.getCreatedAt()),new DateTime(issue.getClosedAt())).getDays());
					marcacaoIssue.add(marcacao);
				}
			}
		}		
	}
	
	public static void insereMarcacaoLabelConsolidado(ArrayList<MarcacaoIssue> marcacaoIssue, ArrayList<LabelConsolidado> labels, ArrayList<MarcacaoIssue> marcacaoRepositorio) {
		try{
			for(MarcacaoIssue mi : marcacaoRepositorio){
				String labelNormatizada = normatizaLabel(labels, mi.getNome());
				if(marcacaoIssue.isEmpty()){
					MarcacaoIssue marcacao = new MarcacaoIssue(labelNormatizada, TipoMarcacao.LABEL, mi.getQuantidade());
					marcacao.incrementaTimeToFix(mi.getTimeToFix());
					marcacaoIssue.add(marcacao);
				} else {
					boolean incrementadoLabel = false;
					for(MarcacaoIssue m : marcacaoIssue){
						if(m.getTipo() == TipoMarcacao.LABEL){
							if(m.getNome().equals(labelNormatizada)){	
								m.acrescentaNumeroContador(mi.getQuantidade());
								m.incrementaTimeToFix(mi.getTimeToFix());
								incrementadoLabel = true;
							}
						}
					}
					if(!incrementadoLabel){
						MarcacaoIssue marcacao = new MarcacaoIssue(labelNormatizada, TipoMarcacao.LABEL);
						marcacao.incrementaTimeToFix(mi.getTimeToFix());
						marcacaoIssue.add(marcacao);
					}
				}
			}	
		} catch (NullPointerException n){
			System.out.println("Não encontrada Marcação");
		}
	}



	private static String normatizaLabel(ArrayList<LabelConsolidado> labels,
			String label) {
		for(LabelConsolidado lconsolidado : labels){
			for(String s : lconsolidado.getLabelVaridade()){
				if(s.compareToIgnoreCase(label) == 0)
					return lconsolidado.getLabelNormatizada();
			}
		}
		return label.toUpperCase();
	}

}
