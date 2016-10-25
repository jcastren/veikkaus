package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_FIRST_NAME;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_LAST_NAME;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PLAYER_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PLAYER_DELETE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PLAYER_MODIFY_URL;
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

import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PlayerControllerTest extends JUnitTestUtil {

	// private static final Logger logger =
	// LoggerFactory.getLogger(TournamentControllerTest.class);

	@Autowired
	private PlayerDao playerDao;

	@Before
	public void setup() throws Exception {
		cleanDb();
	}

	@Test
	public void testCreateAndDelete() throws Exception {
		String firstName = "Eric";
		String lastName = "Cantona";
		String query = String.format(getFormattedStr(2), 
				PARAM_NAME_FIRST_NAME, getEncodedStr(firstName), 
				PARAM_NAME_LAST_NAME, getEncodedStr(lastName));
		String url = PLAYER_CREATE_URL + "?" + query;
		String playerId = callUrl(url, true);
		Player player = playerDao.findOne(Long.valueOf(playerId));
		assertNotNull(player);
		assertThat(player.getFirstName().equals(firstName));
		assertThat(player.getLastName().equals(lastName));

		query = String.format(getFormattedStr(1), PARAM_NAME_ID, getEncodedStr(playerId));
		url = PLAYER_DELETE_URL + "?" + query;
		callUrl(url, false);
		assertNull(playerDao.findOne(Long.valueOf(playerId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String playerId = addPlayer().getId().toString();
		
		String firstName = "Matt";
		String lastName = "Busby";
		
		String query = String.format(getFormattedStr(3), 
				PARAM_NAME_ID, getEncodedStr(playerId),
				PARAM_NAME_FIRST_NAME, getEncodedStr(firstName),
				PARAM_NAME_LAST_NAME, getEncodedStr(lastName));
		String url = PLAYER_MODIFY_URL + "?" + query;
		String newPlayerId = callUrl(url, true);
		
		assertThat(playerId.equals(newPlayerId));
		Player newPlayer = playerDao.findOne(Long.valueOf(newPlayerId));
		assertNotNull(newPlayer);
		assertThat(newPlayer.getFirstName().equals(firstName));
		assertThat(newPlayer.getLastName().equals(lastName));
		
		deletePlayer(playerId);
	}

}