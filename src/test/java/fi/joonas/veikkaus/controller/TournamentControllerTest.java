package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@Disabled
public class TournamentControllerTest extends JUnitTestUtil {

    @Autowired
    private TournamentDao tournamentDao;

    @BeforeEach
    public void setup() throws Exception {
        cleanDb();
    }

    @Test
    public void testNothing() {
        assertTrue(true);
    }

    @Test
	public void testCreateAndDelete() throws Exception {
		String name = "Brazil World Cup";
		String year = "2014";

		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_NAME, name)
				.put(PARAM_NAME_YEAR, year)
				.build();
		String tournamentId = callUrl(TOURNAMENT_CREATE_URL + getQuery(paramMap), true);
		Tournament dbTournament = tournamentDao.findById(Long.valueOf(tournamentId)).get();
		assertNotNull(dbTournament);
		assertThat(dbTournament.getId().equals(Long.valueOf(tournamentId)));
		assertThat(dbTournament.getName().equals(name));
		assertThat(dbTournament.getYear() == Integer.parseInt(year));

		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, tournamentId).build();
		callUrl(TOURNAMENT_DELETE_URL + getQuery(paramMap), false);
		assertNull(tournamentDao.findById(Long.valueOf(tournamentId)));
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
		Tournament dbTournament = tournamentDao.findById(Long.valueOf(dbTournamentId)).get();
		assertNotNull(dbTournament);
		assertThat(dbTournamentId.equals(tournamentId));
		assertThat(dbTournament.getName() == name);
		assertThat(dbTournament.getYear() == Integer.parseInt(year));

		deleteTournament(dbTournament);
	}
}