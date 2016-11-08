package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.collect.ImmutableMap;

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
	public void testNothing() {
		assertTrue(true);
	}

	/*@Test
	public void testCreateAndDelete() throws Exception {
		String name = "Brazil World Cup";
		String year = "2014";
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_NAME, name)
				.put(PARAM_NAME_YEAR, year)
				.build();
		String tournamentId = callUrl(TOURNAMENT_CREATE_URL + getQuery(paramMap), true);
		Tournament dbTournament = tournamentDao.findOne(Long.valueOf(tournamentId));
		assertNotNull(dbTournament);
		assertThat(dbTournament.getId().equals(Long.valueOf(tournamentId)));
		assertThat(dbTournament.getName() == name);
		assertThat(dbTournament.getYear() == Integer.parseInt(year));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, tournamentId).build();
		callUrl(TOURNAMENT_DELETE_URL + getQuery(paramMap), false);
		assertNull(tournamentDao.findOne(Long.valueOf(tournamentId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String tournamentId = addTournament().getId().toString();
		
		String name = "Mexico World Cup";
		String year = "1986";
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, tournamentId)
				.put(PARAM_NAME_NAME, name)
				.put(PARAM_NAME_YEAR, year)
				.build();
		
		String dbTournamentId = callUrl(TOURNAMENT_MODIFY_URL + getQuery(paramMap), true);
		Tournament dbTournament = tournamentDao.findOne(Long.valueOf(dbTournamentId));
		assertNotNull(dbTournament);
		assertThat(dbTournamentId.equals(tournamentId));
		assertThat(dbTournament.getName() == name);
		assertThat(dbTournament.getYear() == Integer.parseInt(year));
	
		deleteTournament(dbTournament);
	}
	*/
}