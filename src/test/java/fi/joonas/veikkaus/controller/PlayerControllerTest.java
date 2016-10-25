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

import com.google.common.collect.ImmutableMap;

import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PlayerControllerTest extends JUnitTestUtil {

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
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_FIRST_NAME, firstName)
				.put(PARAM_NAME_LAST_NAME, lastName)
				.build();
		String playerId = callUrl(PLAYER_CREATE_URL + getQuery(paramMap), true);
		Player dbPlayer = playerDao.findOne(Long.valueOf(playerId));
		assertNotNull(dbPlayer);
		assertThat(dbPlayer.getId().equals(Long.valueOf(playerId)));
		assertThat(dbPlayer.getFirstName().equals(firstName));
		assertThat(dbPlayer.getLastName().equals(lastName));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, playerId).build();
		callUrl(PLAYER_DELETE_URL + getQuery(paramMap), false);
		assertNull(playerDao.findOne(Long.valueOf(playerId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String firstName = "Matt";
		String lastName = "Busby";

		String playerId = addPlayer().getId().toString();
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, playerId)
				.put(PARAM_NAME_FIRST_NAME, firstName)
				.put(PARAM_NAME_LAST_NAME, lastName)
				.build();
		String dbPlayerId = callUrl(PLAYER_MODIFY_URL + getQuery(paramMap), true);
		Player dbPlayer = playerDao.findOne(Long.valueOf(dbPlayerId));
		assertNotNull(dbPlayer);
		assertThat(playerId.equals(dbPlayerId));
		assertThat(dbPlayer.getFirstName().equals(firstName));
		assertThat(dbPlayer.getLastName().equals(lastName));

		deletePlayer(dbPlayer);
	}

}