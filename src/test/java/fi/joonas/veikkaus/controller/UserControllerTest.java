package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_EMAIL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_NAME;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_PASSWORD;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_USER_ROLE_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.USER_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.USER_DELETE_URL;
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

import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest extends JUnitTestUtil {

	//private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@Autowired
	private UserDao userDao;

	private String userRoleId;

	@Before
	public void setup() throws Exception {
		cleanDb();
		userRoleId = addUserRole();
	}
	
	@After
	public void destroy() throws Exception {
		deleteUserRole(userRoleId);
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		String email = "eemeli";
		String name = "nimi";
		String password = "salainensana";
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_EMAIL, email)
				.put(PARAM_NAME_NAME, name)
				.put(PARAM_NAME_PASSWORD, password)
				.put(PARAM_NAME_USER_ROLE_ID, userRoleId)
				.build();
		
		String userId = callUrl(USER_CREATE_URL + getQuery(paramMap), true);
		assertNotNull(userDao.findOne(Long.valueOf(userId)));
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, userId)
				.build();
		
		callUrl(USER_DELETE_URL + getQuery(paramMap), false);
		assertNull(userDao.findOne(Long.valueOf(userId)));
	}
	
}