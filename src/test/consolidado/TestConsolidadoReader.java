package test.consolidado;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import consolidadoWriter.ListaRepositorioConsolidado;
import Consolidado.ConsolidadoReader;

public class TestConsolidadoReader
{
	private static String EXEMPLOS = 
		"Indice;usuario;Repositorio;ID;owner;dataCriado;IdadeDias;Contributors;ContributorsAjustado;Linguagem;Nissue;NissueAjustado;OpenIssues;ClosedIssues;OpenIssueBug;ClosedIssueBug;Followers;FollowersAjustado;Following;Forks;Watchers;KB;MB;IssueXMB;IssueXMBAjustado;ContemIssue;IdadeSemana;ReportersIssue;SomaTimeToFix;MediaTimeToFix\n" +
		"0;mojombo;grit;mojombogrit;Tom Preston-Werner;Mon Oct 29 12:37:16 BRST 2007;2924;48;48;Ruby;189;189;2;187;0;4;18594;18594;11;451;1850;7954;7,767578125;24,33190847;24,33191;Contem Issue;417,71;136;98974;523,6719577\n" +
		"1;wycats;merb-core;wycatsmerb-core;Yehuda Katz;Sat Jan 12 03:50:53 BRST 2008;2849;101;101;Ruby;0;NA;0;0;0;0;6730;6730;3;46;397;3462;3,380859375;0;NA;Nao Contem Issue;407;0;0;NA\n" +
		"2;rubinius;rubinius;rubiniusrubinius;Rubinius;Sat Jan 12 14:46:52 BRST 2008;2849;555;555;Ruby;3516;3516;209;3307;0;0;0;NA;0;585;2494;217329;212,2353516;16,56651436;16,56651;Contem Issue;407;915;208767;59,37627986\n" +
		"3;mojombo;god;mojombogod;Tom Preston-Werner;Sun Jan 13 03:16:23 BRST 2008;2848;85;85;Ruby;221;221;100;121;0;0;18594;18594;11;444;1856;3991;3,897460938;56,70358306;56,70358;Contem Issue;406,86;157;111087;502,6561086\n" +
		"4;vanpelt;jsawesome;vanpeltjsawesome;Chris Van Pelt;Sun Jan 13 04:04:19 BRST 2008;2848;2;2;JavaScript;0;NA;0;0;0;0;51;51;13;0;16;192;0,1875;0;NA;Nao Contem Issue;406,86;0;0;NA\n" +
		"5;wycats;jspec;wycatsjspec;Yehuda Katz;Sun Jan 13 13:50:31 BRST 2008;2848;2;2;JavaScript;0;NA;0;0;0;0;6730;6730;3;3;48;156;0,15234375;0;NA;Nao Contem Issue;406,86;0;0;NA\n" +
		"6;defunkt;exception_logger;defunktexception_logger;Chris Wanstrath;Mon Jan 14 01:32:19 BRST 2008;2847;7;7;Ruby;0;NA;0;0;0;0;14788;14788;208;77;226;232;0,2265625;0;NA;Nao Contem Issue;406,71;0;0;NA\n" +
		"7;defunkt;ambition;defunktambition;Chris Wanstrath;Mon Jan 14 04:28:56 BRST 2008;2847;3;3;Ruby;0;NA;0;0;0;0;14788;14788;208;13;141;473;0,461914063;0;NA;Nao Contem Issue;406,71;0;0;NA\n" +
		"8;technoweenie;restful-authentication;technoweenierestful-authentication;risk danger olson;Mon Jan 14 12:44:23 BRST 2008;2847;39;39;Ruby;33;33;28;5;0;0;2204;2204;17;275;1630;1208;1,1796875;27,97350993;27,97351;Contem Issue;406,71;28;55526;1682,606061\n" +
		"9;technoweenie;attachment_fu;technoweenieattachment_fu;risk danger olson;Mon Jan 14 12:51:56 BRST 2008;2847;36;36;Ruby;52;52;36;16;0;0;2204;2204;17;293;1036;1008;0,984375;52,82539683;52,8254;Contem Issue;406,71;44;64774;1245,653846\n" +
		"10;topfunky;bong;topfunkybong;Geoffrey Grosenbach;Tue Jan 15 03:42:45 BRST 2008;2846;2;2;Ruby;2;2;2;0;0;0;1085;1085;199;4;60;123;0,120117188;16,6504065;16,65041;Contem Issue;406,57;2;3918;1959\n";

	@Test
	public void testSimples() throws IOException
	{
		ListaRepositorioConsolidado lista = new ConsolidadoReader().loadFromString(EXEMPLOS);
		assertEquals(11, lista.size());
		
		assertEquals("mojombo", lista.get(0).getUsuario());
		assertEquals("grit", lista.get(0).getRepositorio());
		assertEquals(48, lista.get(0).getContributors());
	}
}