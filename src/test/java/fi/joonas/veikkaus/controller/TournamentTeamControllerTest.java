package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_TEAM_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_TOURNAMENT_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_TEAM_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_TEAM_DELETE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_TEAM_MODIFY_URL;
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

import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TournamentTeamControllerTest extends JUnitTestUtil {

	@Autowired
	private TournamentTeamDao tournamentTeamDao;
	
	private Tournament tournament;
	
	private Team team;

	@Before
	public void setup() throws Exception {
		cleanDb();
		tournament = addTournament();
		team = addTeam();
	}
	
	@After
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
		TournamentTeam dbTournamentTeam = tournamentTeamDao.findOne(Long.valueOf(tournamentTeamId));
		assertNotNull(dbTournamentTeam);
		assertThat(dbTournamentTeam.getId().equals(Long.valueOf(tournamentTeamId)));
		assertThat(dbTournamentTeam.getTournament().getId().equals(tournament.getId()));
		assertThat(dbTournamentTeam.getTeam().getId().equals(team.getId()));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, tournamentTeamId).build();
		callUrl(TOURNAMENT_TEAM_DELETE_URL + getQuery(paramMap), false);
		assertNull(tournamentTeamDao.findOne(Long.valueOf(tournamentTeamId)));
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
		TournamentTeam dbTournamentTeam = tournamentTeamDao.findOne(Long.valueOf(dbTournamentTeamId));
		assertNotNull(dbTournamentTeam);
		assertThat(tournamentTeam.getId().toString().equals(dbTournamentTeamId));
		assertThat(tournamentId.equals(dbTournamentTeam.getTournament().getId()));
		assertThat(teamId.equals(dbTournamentTeam.getTeam().getId()));
	
		deleteTournamentTeam(dbTournamentTeam);
	}

}