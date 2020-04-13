package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import fi.joonas.veikkaus.util.VeikkausUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GameControllerTest extends JUnitTestUtil {

	@Autowired
	private GameDao gameDao;
	
	private TournamentTeam homeTeam;
	
	private TournamentTeam awayTeam;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
		homeTeam = addTournamentTeam();
		awayTeam = addTournamentTeam();
		awayTeam.getTeam().setName(awayTeam.getTeam().getName() + "_away");
	}
	
	@After
	public void destroy() throws Exception {
		deleteTournamentTeam(awayTeam);
		deleteTournamentTeam(homeTeam);
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		int homeScore = 7;
		int awayScore = 3;
		Date gameDate = new Date();
	
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_HOME_TEAM_ID, homeTeam.getId().toString())
				.put(PARAM_NAME_AWAY_TEAM_ID, awayTeam.getId().toString())
				.put(PARAM_NAME_HOME_SCORE, Integer.valueOf(homeScore).toString())
				.put(PARAM_NAME_AWAY_SCORE, Integer.valueOf(awayScore).toString())
				.put(PARAM_NAME_GAME_DATE, VeikkausUtil.getDateAsString(gameDate))
				.build();
		String gameId = callUrl(GAME_CREATE_URL + getQuery(paramMap), true);
		Game dbGame = gameDao.findById(Long.valueOf(gameId)).get();
		assertNotNull(dbGame);
		assertThat(dbGame.getId().equals(Long.valueOf(gameId)));
		assertThat(dbGame.getHomeTeam().getId().equals(homeTeam.getId()));
		assertThat(dbGame.getAwayTeam().getId().equals(awayTeam.getId()));
		assertThat(dbGame.getHomeScore() == homeScore);
		assertThat(dbGame.getAwayScore() == awayScore);
		assertThat(dbGame.getGameDate().equals(gameDate));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, gameId).build();
		callUrl(GAME_DELETE_URL + getQuery(paramMap), false);
		assertNull(gameDao.findById(Long.valueOf(gameId)));
	}
	
	@Test
	public void testModify() throws Exception {
		int homeScore = 7;
		int awayScore = 3;
		Date gameDate = new Date();
		
		Game game = addGame();
		String gameId = game.getId().toString();
		
		// We have to be careful with homeTeamId and awayTeamId used as @Before annotation creates
		// a homeGame and awayGame in addition to addGame method and we don't want to delete
		// that homeGame and awayGame before @After annotation
		String homeTeamId = game.getHomeTeam().getId().toString();
		String awayTeamId = game.getAwayTeam().getId().toString();
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, gameId)
				.put(PARAM_NAME_HOME_TEAM_ID, homeTeamId)
				.put(PARAM_NAME_AWAY_TEAM_ID, awayTeamId)
				.put(PARAM_NAME_HOME_SCORE, Integer.valueOf(homeScore).toString())
				.put(PARAM_NAME_AWAY_SCORE, Integer.valueOf(awayScore).toString())
				.put(PARAM_NAME_GAME_DATE, VeikkausUtil.getDateAsString(gameDate))
				.build();
		String dbGameId = callUrl(GAME_MODIFY_URL + getQuery(paramMap), true);
		Game dbGame = gameDao.findById(Long.valueOf(dbGameId)).get();
		assertNotNull(dbGame);
		assertThat(dbGameId.equals(gameId));
		assertThat(dbGame.getHomeTeam().getId().toString().equals(homeTeamId));
		assertThat(dbGame.getAwayTeam().getId().toString().equals(awayTeamId));
		assertThat(dbGame.getHomeScore() == homeScore);
		assertThat(dbGame.getAwayScore() == awayScore);
		assertThat(dbGame.getGameDate().equals(gameDate));

		deleteGame(game);
	}

}