package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_GAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_TOURNAMENT_PLAYER_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.SCORER_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.SCORER_DELETE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.SCORER_MODIFY_URL;
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

import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Scorer;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ScorerControllerTest extends JUnitTestUtil {

	@Autowired
	private ScorerDao scorerDao;

	private TournamentPlayer tournamentPlayer;
	
	private Game game;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
		tournamentPlayer = addTournamentPlayer();
		game = addGame();
	}
	
	@After
	public void destroy() throws Exception {
		deleteGame(game);
		deleteTournamentPlayer(tournamentPlayer);
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_TOURNAMENT_PLAYER_ID, tournamentPlayer.getId().toString())
				.put(PARAM_NAME_GAME_ID, game.getId().toString())
				.build();
		String scorerId = callUrl(SCORER_CREATE_URL + getQuery(paramMap), true);
		Scorer dbScorer = scorerDao.findOne(Long.valueOf(scorerId));
		assertNotNull(dbScorer);
		assertThat(dbScorer.getId().equals(Long.valueOf(scorerId)));
		assertThat(dbScorer.getTournamentPlayer().getId().equals(tournamentPlayer.getId()));
		assertThat(dbScorer.getGame().getId().equals(game.getId()));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, scorerId).build();
		callUrl(SCORER_DELETE_URL + getQuery(paramMap), false);
		assertNull(scorerDao.findOne(Long.valueOf(scorerId)));
	}
	
	@Test
	public void testModify() throws Exception {
		Scorer scorer = addScorer();
		String scorerId = scorer.getId().toString();
		
		// We have to be careful with userId and statusId used as @Before annotation creates
		// a user and status in addition to addBet method and we don't want to delete
		// that user and status before @After annotation
		String tournamentPlayerId = scorer.getTournamentPlayer().getId().toString();
		String gameId = scorer.getGame().getId().toString();
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, scorerId)
				.put(PARAM_NAME_TOURNAMENT_PLAYER_ID, tournamentPlayerId)
				.put(PARAM_NAME_GAME_ID, gameId)
				.build();
		String dbScorerId = callUrl(SCORER_MODIFY_URL + getQuery(paramMap), true);
		Scorer dbScorer = scorerDao.findOne(Long.valueOf(dbScorerId));
		assertNotNull(dbScorer);
		assertThat(dbScorerId.equals(scorerId));
		assertThat(dbScorer.getTournamentPlayer().getId().equals(tournamentPlayerId));
		assertThat(dbScorer.getGame().getId().equals(gameId));

		deleteScorer(scorer);
	}

}