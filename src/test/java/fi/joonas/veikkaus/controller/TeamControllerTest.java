package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.util.JUnitTestUtil;
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
public class TeamControllerTest extends JUnitTestUtil {

    @Autowired
    private TeamDao teamDao;

    @BeforeEach
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