package Main;

public class CodigosOld {
	
	//consigo paginar os issues, por�m apenas os abertos, n�o consegui encontrar os fechados.
	/*for(Collection<Issue> issue : issueService.pageIssues(repoId)){
		for(Issue i : issue){
			System.out.println(i.getTitle() + " " + i.getState() + " " + i.getNumber());
			
			if(i.getState().equalsIgnoreCase("open"))
				openIssue++;
			
			if(i.getState().equalsIgnoreCase("closed"))
				closedIssue++;
		}
	}*/
	
	//Consigo achar abertos e fechados, mas por causa da pagina��o, acho apenas 10.
	/*for(SearchIssue search : issueService.searchIssues(repoId, IssueService.STATE_OPEN, " ")){
		System.out.println(search.getTitle() + " " + search.getState() + " " + search.getNumber());
		
		if(search.getState().equalsIgnoreCase("open"))
			openIssue++;
		
		if(search.getState().equalsIgnoreCase("closed"))
			closedIssue++;
		
	}*/

}
