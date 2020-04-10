package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameDaoTest extends JUnitTestUtil {

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private TournamentTeamDao tournamentTeamDao;

    @Autowired
    private GameDao gameDao;

    private Tournament tournament;

    private List<Team> teamList;

    private List<TournamentTeam> tournamentTeamList;

    private List<Game> gameList;

    @Before
    public void setup() throws Exception {
        tournament = new Tournament("Käpä Cup", 2026);
        tournamentDao.save(tournament);

        Team team1 = new Team("Käpa");
        Team team2 = new Team("Kiffen");
        Team team3 = new Team("EIF");
        Team team4 = new Team("Kaapo");

        teamList = new ArrayList<>();

        teamList.add(team1);
        teamList.add(team2);
        teamList.add(team3);
        teamList.add(team4);

        teamList.forEach(team -> teamDao.save(team)
        );

        tournamentTeamList = new ArrayList<>();

        TournamentTeam tournamentTeam1 = new TournamentTeam(tournament, team1);
        TournamentTeam tournamentTeam2 = new TournamentTeam(tournament, team2);
        TournamentTeam tournamentTeam3 = new TournamentTeam(tournament, team3);
        TournamentTeam tournamentTeam4 = new TournamentTeam(tournament, team4);

        tournamentTeamList.add(tournamentTeam1);
        tournamentTeamList.add(tournamentTeam2);
        tournamentTeamList.add(tournamentTeam3);
        tournamentTeamList.add(tournamentTeam4);

        tournamentTeamList.forEach(tournamentTeam -> tournamentTeamDao.save(tournamentTeam));

        gameList = new ArrayList<>();

        gameList.add(new Game(tournament, tournamentTeam1, tournamentTeam2, 3, 4, VeikkausUtil.getDate(2026, 8, 1)));
        gameList.add(new Game(tournament, tournamentTeam3, tournamentTeam4, 5, 2, VeikkausUtil.getDate(2026, 8, 2)));
        gameList.add(new Game(tournament, tournamentTeam1, tournamentTeam3, 6, 1, VeikkausUtil.getDate(2026, 8, 3)));
        gameList.add(new Game(tournament, tournamentTeam2, tournamentTeam4, 5, 5, VeikkausUtil.getDate(2026, 8, 4)));

        gameList.forEach(game -> gameDao.save(game));
    }

    @After
    public void clean() {
        gameList.forEach(game -> gameDao.delete(game.getId()));
        tournamentTeamList.forEach(tournamentTeam -> tournamentTeamDao.delete(tournamentTeam.getId()));
        teamList.forEach(team -> teamDao.delete(team.getId()));
        tournamentDao.delete(tournament.getId());
    }

    @Test
    public void testFindGamesByTournament() {
        List<Game> gameListDb = gameDao.findByTournament(tournament);
        assertThat(gameListDb.size() == gameList.size());
    }

    @Test
    public void testFindGamesByTournamentOrderByGameDate() {
        List<Game> gameListDb = gameDao.findByTournamentOrderByGameDate(tournament);
        assertThat(gameListDb.size() == gameList.size());
    }


}