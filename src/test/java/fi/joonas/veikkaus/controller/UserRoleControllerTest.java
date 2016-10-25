package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.collect.ImmutableMap;

import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.UserRole;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserRoleControllerTest extends JUnitTestUtil {

	//private static final Logger logger = LoggerFactory.getLogger(UserRoleControllerTest.class);

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		String paramValueName = "ADMIN";
				
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_NAME, paramValueName).build();
		String userRoleId = callUrl(USER_ROLE_CREATE_URL + getQuery(paramMap), true);
		assertNotNull(userRoleDao.findOne(Long.valueOf(userRoleId)));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, userRoleId).build();
		callUrl(USER_ROLE_DELETE_URL + getQuery(paramMap), false);
		assertNull(userRoleDao.findOne(Long.valueOf(userRoleId)));
	}
	
	/** TODO */
	@Test
	public void testModify() throws Exception {
		String userRoleId = addUserRole().getId().toString();
		String paramValueName = "ADMIN";
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, userRoleId)
				.put(PARAM_NAME_NAME, paramValueName).build();
		String dbUserRoleId = callUrl(USER_ROLE_MODIFY_URL + getQuery(paramMap), true);
		UserRole dbUserRole = userRoleDao.findOne(Long.valueOf(dbUserRoleId));
		assertNotNull(dbUserRole);
		assertThat(userRoleId.equals(dbUserRoleId));
		assertThat(dbUserRole.getName().equals(paramValueName));

		deleteUserRole(dbUserRoleId);
	}

}