package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_GOALS;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_PLAYER_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_TOURNAMENT_TEAM_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_PLAYER_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_PLAYER_DELETE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_PLAYER_MODIFY_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.collect.ImmutableMap;

import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TournamentPlayerControllerTest extends JUnitTestUtil {

	@Autowired
	private TournamentPlayerDao tournamentPlayerDao;
	
	private TournamentTeam tournamentTeam;
	
	private Player player;

	@Before
	public void setup() throws Exception {
		cleanDb();
		tournamentTeam = addTournamentTeam();
		player = addPlayer();
	}
	
	@After
	public void destroy() throws Exception {
		deletePlayer(player);
		deleteTournamentTeam(tournamentTeam);
	
	}

	@Test
	public void testCreateAndDelete() throws Exception {
		int goals = 15;
				
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_TOURNAMENT_TEAM_ID, tournamentTeam.getId().toString())
				.put(PARAM_NAME_PLAYER_ID, player.getId().toString())
				.put(PARAM_NAME_GOALS, Integer.valueOf(goals).toString())
				.build();
		String tournamentPlayerId = callUrl(TOURNAMENT_PLAYER_CREATE_URL + getQuery(paramMap), true);
		TournamentPlayer dbTournamentPlayer = tournamentPlayerDao.findOne(Long.valueOf(tournamentPlayerId));
		assertNotNull(dbTournamentPlayer);
		assertThat(dbTournamentPlayer.getId().equals(Long.valueOf(tournamentPlayerId)));
		assertThat(dbTournamentPlayer.getTournamentTeam().getId().equals(tournamentTeam.getId()));
		assertThat(dbTournamentPlayer.getPlayer().getId().equals(player.getId()));
		assertThat(dbTournamentPlayer.getGoals() == goals);
				
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, tournamentPlayerId).build();
		callUrl(TOURNAMENT_PLAYER_DELETE_URL + getQuery(paramMap), false);
		assertNull(tournamentPlayerDao.findOne(Long.valueOf(tournamentPlayerId)));
	}
	
	@Test
	public void testModify() throws Exception {
		TournamentPlayer tournamentPlayer = addTournamentPlayer();
		String tournamentTeamId = tournamentPlayer.getTournamentTeam().getId().toString();
		String playerId = tournamentPlayer.getPlayer().getId().toString();
		int goals = 20;
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, tournamentPlayer.getId().toString())
				.put(PARAM_NAME_TOURNAMENT_TEAM_ID, tournamentTeamId)
				.put(PARAM_NAME_PLAYER_ID, playerId)
				.put(PARAM_NAME_GOALS, Integer.valueOf(goals).toString())
				.build();
		
		String dbTournamentPlayerId = callUrl(TOURNAMENT_PLAYER_MODIFY_URL + getQuery(paramMap), true);
		TournamentPlayer dbTournamentPlayer = tournamentPlayerDao.findOne(Long.valueOf(dbTournamentPlayerId));
		assertNotNull(dbTournamentPlayer);
		assertThat(dbTournamentPlayer.getId().equals(tournamentPlayer.getId()));
		assertThat(dbTournamentPlayer.getTournamentTeam().getId().equals(tournamentTeam.getId()));
		assertThat(dbTournamentPlayer.getPlayer().getId().equals(player.getId()));
		assertThat(dbTournamentPlayer.getGoals() == goals);
	
		deleteTournamentPlayer(dbTournamentPlayer);
	}

}