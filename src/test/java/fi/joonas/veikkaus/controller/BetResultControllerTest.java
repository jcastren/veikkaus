package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.BetResult;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import org.junit.jupiter.api.AfterEach;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@Disabled
public class BetResultControllerTest extends JUnitTestUtil {

    @Autowired
    private BetResultDao betResultDao;

    private Bet bet;

    private Game game;

    @BeforeEach
    public void setup() throws Exception {
        cleanDb();
        bet = addBet();
        game = addGame();
    }

    @AfterEach
    public void destroy() throws Exception {
        deleteGame(game);
        deleteBet(bet);
    }

    @Test
    public void testCreateAndDelete() throws Exception {
        int homeScore = 7;
        int awayScore = 3;

        paramMap = ImmutableMap.<String, String>builder()
                .put(PARAM_NAME_BET_ID, bet.getId().toString())
                .put(PARAM_NAME_GAME_ID, game.getId().toString())
                .put(PARAM_NAME_HOME_SCORE, Integer.valueOf(homeScore).toString())
                .put(PARAM_NAME_AWAY_SCORE, Integer.valueOf(awayScore).toString())
                .build();
        String betResultId = callUrl(BET_RESULT_CREATE_URL + getQuery(paramMap), true);
        BetResult dbBetResult = betResultDao.findById(Long.valueOf(betResultId)).get();
        assertNotNull(dbBetResult);
        assertThat(dbBetResult.getId().equals(Long.valueOf(betResultId)));
        assertThat(dbBetResult.getBet().getId().equals(bet.getId()));
        assertThat(dbBetResult.getGame().getId().equals(game.getId()));
        assertThat(dbBetResult.getHomeScore() == homeScore);
        assertThat(dbBetResult.getAwayScore() == awayScore);

        paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, betResultId).build();
        callUrl(BET_RESULT_DELETE_URL + getQuery(paramMap), false);
        assertNull(betResultDao.findById(Long.valueOf(betResultId)));
    }

    @Test
    public void testModify() throws Exception {
        int homeScore = 7;
        int awayScore = 3;
        BetResult betResult = addBetResult();
        String betResultId = betResult.getId().toString();
        String betId = betResult.getBet().getId().toString();
        String gameId = betResult.getGame().getId().toString();

        paramMap = ImmutableMap.<String, String>builder()
                .put(PARAM_NAME_ID, betResultId)
                .put(PARAM_NAME_BET_ID, betId)
                .put(PARAM_NAME_GAME_ID, gameId)
                .put(PARAM_NAME_HOME_SCORE, Integer.valueOf(homeScore).toString())
                .put(PARAM_NAME_AWAY_SCORE, Integer.valueOf(awayScore).toString())
                .build();
        String dbBetResultId = callUrl(BET_RESULT_MODIFY_URL + getQuery(paramMap), true);
        BetResult dbBetResult = betResultDao.findById(Long.valueOf(dbBetResultId)).get();
        assertNotNull(dbBetResult);
        assertThat(dbBetResultId.equals(betResultId));
        assertThat(dbBetResult.getBet().getId().equals(betId));
        assertThat(dbBetResult.getGame().getId().equals(gameId));
        assertThat(dbBetResult.getHomeScore() == homeScore);
        assertThat(dbBetResult.getAwayScore() == awayScore);

        deleteBetResult(betResult);
    }

}