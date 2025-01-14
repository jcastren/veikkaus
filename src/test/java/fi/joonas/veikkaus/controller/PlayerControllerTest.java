package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.jpaentity.Player;
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
public class PlayerControllerTest extends JUnitTestUtil {

    @Autowired
    private PlayerDao playerDao;

    @BeforeEach
    public void setup() throws Exception {
        cleanDb();
    }

    @Test
    public void testCreateAndDelete() throws Exception {
        String firstName = "Eric";
        String lastName = "Cantona";

        paramMap = ImmutableMap.<String, String>builder()
                .put(PARAM_NAME_FIRST_NAME, firstName)
                .put(PARAM_NAME_LAST_NAME, lastName)
                .build();
        String playerId = callUrl(PLAYER_CREATE_URL + getQuery(paramMap), true);
        Player dbPlayer = playerDao.findById(Long.valueOf(playerId)).get();
        assertNotNull(dbPlayer);
        assertThat(dbPlayer.getId().equals(Long.valueOf(playerId)));
        assertThat(dbPlayer.getFirstName().equals(firstName));
        assertThat(dbPlayer.getLastName().equals(lastName));

        paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, playerId).build();
        callUrl(PLAYER_DELETE_URL + getQuery(paramMap), false);
        assertNull(playerDao.findById(Long.valueOf(playerId)));
    }

    @Test
    public void testModify() throws Exception {
        String firstName = "Matt";
        String lastName = "Busby";

        String playerId = addPlayer().getId().toString();

        paramMap = ImmutableMap.<String, String>builder()
                .put(PARAM_NAME_ID, playerId)
                .put(PARAM_NAME_FIRST_NAME, firstName)
                .put(PARAM_NAME_LAST_NAME, lastName)
                .build();
        String dbPlayerId = callUrl(PLAYER_MODIFY_URL + getQuery(paramMap), true);
        Player dbPlayer = playerDao.findById(Long.valueOf(dbPlayerId)).get();
        assertNotNull(dbPlayer);
        assertThat(dbPlayerId.equals(playerId));
        assertThat(dbPlayer.getFirstName().equals(firstName));
        assertThat(dbPlayer.getLastName().equals(lastName));

        deletePlayer(dbPlayer);
    }

}