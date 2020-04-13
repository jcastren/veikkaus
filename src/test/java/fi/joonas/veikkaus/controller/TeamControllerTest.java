package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TeamControllerTest extends JUnitTestUtil {

	@Autowired
	private TeamDao teamDao;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		String name = "FC AKI";
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_NAME, name)
				.build();
		String teamId = callUrl(TEAM_CREATE_URL + getQuery(paramMap), true);
		Team dbTeam = teamDao.findById(Long.valueOf(teamId)).get();
		assertNotNull(dbTeam);
		assertThat(dbTeam.getId().equals(Long.valueOf(teamId)));
		assertThat(dbTeam.getName().equals(name));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, teamId).build();
		callUrl(TEAM_DELETE_URL + getQuery(paramMap), false);
		assertNull(teamDao.findById(Long.valueOf(teamId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String name = "SAPA";
		String teamId = addTeam().getId().toString();
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, teamId)
				.put(PARAM_NAME_NAME, name)
						.build();
		String dbTeamId = callUrl(TEAM_MODIFY_URL + getQuery(paramMap), true);
		Team dbTeam = teamDao.findById(Long.valueOf(dbTeamId)).get();
		assertNotNull(dbTeam);
		assertThat(dbTeamId.equals(teamId));
		assertThat(dbTeam.getName().equals(name));

		deleteTeam(dbTeam);
	}

}