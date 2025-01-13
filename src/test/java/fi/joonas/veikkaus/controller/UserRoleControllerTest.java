package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.jpaentity.UserRole;
import fi.joonas.veikkaus.util.JUnitTestUtil;
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
public class UserRoleControllerTest extends JUnitTestUtil {

    //private static final Logger logger = LoggerFactory.getLogger(UserRoleControllerTest.class);

    @Autowired
    private UserRoleDao userRoleDao;

    @BeforeEach
    public void setup() throws Exception {
        cleanDb();
    }

    @Test
    public void testCreateAndDelete() throws Exception {
        String paramValueName = "ADMIN";

        paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_NAME, paramValueName).build();
        String userRoleId = callUrl(USER_ROLE_CREATE_URL + getQuery(paramMap), true);
        assertNotNull(userRoleDao.findById(Long.valueOf(userRoleId)));

        paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, userRoleId).build();
        callUrl(USER_ROLE_DELETE_URL + getQuery(paramMap), false);
        assertNull(userRoleDao.findById(Long.valueOf(userRoleId)));
    }

    @Test
    public void testModify() throws Exception {
        String paramValueName = "ADMIN";

        String userRoleId = addUserRole().getId().toString();

        paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, userRoleId)
                .put(PARAM_NAME_NAME, paramValueName).build();
        String dbUserRoleId = callUrl(USER_ROLE_MODIFY_URL + getQuery(paramMap), true);
        UserRole dbUserRole = userRoleDao.findById(Long.valueOf(dbUserRoleId)).get();
        assertNotNull(dbUserRole);
        assertThat(userRoleId.equals(dbUserRoleId));
        assertThat(dbUserRole.getName().equals(paramValueName));

        deleteUserRole(dbUserRole);
    }

}