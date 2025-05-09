package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
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
public class TournamentPlayerControllerTest extends JUnitTestUtil {

    @Autowired
    private TournamentPlayerDao tournamentPlayerDao;

    private TournamentTeam tournamentTeam;

    private Player player;

    @BeforeEach
    public void setup() throws Exception {
        cleanDb();
        tournamentTeam = addTournamentTeam();
        player = addPlayer();
    }

    @AfterEach
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
        TournamentPlayer dbTournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId)).get();
        assertNotNull(dbTournamentPlayer);
        assertThat(dbTournamentPlayer.getId().equals(Long.valueOf(tournamentPlayerId)));
        assertThat(dbTournamentPlayer.getTournamentTeam().getId().equals(tournamentTeam.getId()));
        assertThat(dbTournamentPlayer.getPlayer().getId().equals(player.getId()));
        assertThat(dbTournamentPlayer.getGoals() == goals);

        paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, tournamentPlayerId).build();
        callUrl(TOURNAMENT_PLAYER_DELETE_URL + getQuery(paramMap), false);
        assertNull(tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId)));
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
        TournamentPlayer dbTournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(dbTournamentPlayerId)).get();
        assertNotNull(dbTournamentPlayer);
        assertThat(dbTournamentPlayer.getId().equals(tournamentPlayer.getId()));
        assertThat(dbTournamentPlayer.getTournamentTeam().getId().equals(tournamentTeam.getId()));
        assertThat(dbTournamentPlayer.getPlayer().getId().equals(player.getId()));
        assertThat(dbTournamentPlayer.getGoals() == goals);

        deleteTournamentPlayer(dbTournamentPlayer);
    }

}