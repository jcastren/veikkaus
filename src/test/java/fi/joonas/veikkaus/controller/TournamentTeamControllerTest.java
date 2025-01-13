package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class TournamentTeamControllerTest extends JUnitTestUtil {

    @Autowired
    private TournamentTeamDao tournamentTeamDao;

    private Tournament tournament;

    private Team team;

    @BeforeEach
    public void setup() throws Exception {
        cleanDb();
        tournament = addTournament();
        team = addTeam();
    }

    @AfterEach
    public void destroy() throws Exception {
        deleteTeam(team);
        deleteTournament(tournament);

    }

    @Test
    public void testCreateAndDelete() throws Exception {

        paramMap = ImmutableMap.<String, String>builder()
                .put(PARAM_NAME_TOURNAMENT_ID, tournament.getId().toString())
                .put(PARAM_NAME_TEAM_ID, team.getId().toString())
                .build();
        String tournamentTeamId = callUrl(TOURNAMENT_TEAM_CREATE_URL + getQuery(paramMap), true);
        TournamentTeam dbTournamentTeam = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId)).get();
        assertNotNull(dbTournamentTeam);
        assertThat(dbTournamentTeam.getId().equals(Long.valueOf(tournamentTeamId)));
        assertThat(dbTournamentTeam.getTournament().getId().equals(tournament.getId()));
        assertThat(dbTournamentTeam.getTeam().getId().equals(team.getId()));

        paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, tournamentTeamId).build();
        callUrl(TOURNAMENT_TEAM_DELETE_URL + getQuery(paramMap), false);
        assertNull(tournamentTeamDao.findById(Long.valueOf(tournamentTeamId)));
    }

    @Test
    public void testModify() throws Exception {
        TournamentTeam tournamentTeam = addTournamentTeam();
        String tournamentId = tournamentTeam.getTournament().getId().toString();
        String teamId = tournamentTeam.getTeam().getId().toString();

        paramMap = ImmutableMap.<String, String>builder()
                .put(PARAM_NAME_ID, tournamentTeam.getId().toString())
                .put(PARAM_NAME_TOURNAMENT_ID, tournamentId)
                .put(PARAM_NAME_TEAM_ID, teamId)
                .build();

        String dbTournamentTeamId = callUrl(TOURNAMENT_TEAM_MODIFY_URL + getQuery(paramMap), true);
        TournamentTeam dbTournamentTeam = tournamentTeamDao.findById(Long.valueOf(dbTournamentTeamId)).get();
        assertNotNull(dbTournamentTeam);
        assertThat(dbTournamentTeamId.equals(tournamentTeam.getId()));
        assertThat(dbTournamentTeam.getTournament().getId().equals(tournamentId));
        assertThat(dbTournamentTeam.getTeam().getId().equals(teamId));

        deleteTournamentTeam(dbTournamentTeam);
    }

}