package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_NAME;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_YEAR;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_DELETE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_MODIFY_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TournamentControllerTest extends JUnitTestUtil {

	// private static final Logger logger =
	// LoggerFactory.getLogger(TournamentControllerTest.class);

	@Autowired
	private TournamentDao tournamentDao;

	@Before
	public void setup() throws Exception {
		cleanDb();
	}

	@Test
	public void testCreateAndDelete() throws Exception {
		String name = "Brazil World Cup";
		String year = "2014";
		String query = String.format(getFormattedStr(2), 
				PARAM_NAME_NAME, getEncodedStr(name), 
				PARAM_NAME_YEAR, getEncodedStr(year));
		String url = TOURNAMENT_CREATE_URL + "?" + query;
		String tournamentId = callUrl(url, true);
		assertNotNull(tournamentDao.findOne(Long.valueOf(tournamentId)));

		query = String.format(getFormattedStr(1), PARAM_NAME_ID, getEncodedStr(tournamentId));
		url = TOURNAMENT_DELETE_URL + "?" + query;
		callUrl(url, false);
		assertNull(tournamentDao.findOne(Long.valueOf(tournamentId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String tournamentId = addTournament().getId().toString();
		
		String name = "Mexico World Cup";
		String year = "1986";
		
		String query = String.format(getFormattedStr(3), 
				PARAM_NAME_ID, getEncodedStr(tournamentId),
				PARAM_NAME_NAME, getEncodedStr(name),
				PARAM_NAME_YEAR, getEncodedStr(year));
		String url = TOURNAMENT_MODIFY_URL + "?" + query;
		String newTOURNAMENTId = callUrl(url, true);
		
		assertThat(tournamentId.equals(newTOURNAMENTId));
		Tournament newTournament = tournamentDao.findOne(Long.valueOf(newTOURNAMENTId));
		assertNotNull(newTournament);
		assertThat(newTournament.getName() == name);
		assertThat(newTournament.getYear() == Integer.parseInt(year));
		
		deleteTournament(tournamentId);
	}

}