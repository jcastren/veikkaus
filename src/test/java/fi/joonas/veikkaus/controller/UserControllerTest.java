package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

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
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest extends JUnitTestUtil {

	//private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@Autowired
	private UserDao userDao;

	private UserRole userRole;

	@Before
	public void setup() throws Exception {
		cleanDb();
		userRole = addUserRole();
	}
	
	@After
	public void destroy() throws Exception {
		deleteUserRole(userRole);
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
				.put(PARAM_NAME_USER_ROLE_ID, userRole.getId().toString())
				.build();
		
		String userId = callUrl(USER_CREATE_URL + getQuery(paramMap), true);
		User dbUser = userDao.findOne(Long.valueOf(userId));
		assertNotNull(dbUser);
		assertThat(dbUser.getId().equals(Long.valueOf(userId)));
		assertThat(dbUser.getEmail().equals(email));
		assertThat(dbUser.getName().equals(name));
		assertThat(dbUser.getPassword().equals(password));
		assertThat(dbUser.getUserRole().getId().equals(userRole.getId()));
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, userId)
				.build();
		callUrl(USER_DELETE_URL + getQuery(paramMap), false);
		assertNull(userDao.findOne(Long.valueOf(userId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String email = "sposti";
		String name = "pena";
		String password = "penanen";
		
		User user = addUser();
		String userId = user.getId().toString();
		
		// We have to be careful with roleId used as @Before annotation creates
		// a user role in addition to addUser method and we don't want to delete
		// that user role before @After annotation
		String userRoleId = user.getUserRole().getId().toString(); 
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, userId)
				.put(PARAM_NAME_EMAIL, email)
				.put(PARAM_NAME_NAME, name)
				.put(PARAM_NAME_PASSWORD, password)
				.put(PARAM_NAME_USER_ROLE_ID, userRoleId)
				.build();
		
		String dbUserId = callUrl(USER_MODIFY_URL + getQuery(paramMap), true);
		User dbUser = userDao.findOne(Long.valueOf(dbUserId));
		assertNotNull(dbUser);
		assertThat(dbUserId.equals(userId));
		assertThat(dbUser.getEmail().equals(email));
		assertThat(dbUser.getName().equals(name));
		assertThat(dbUser.getPassword().equals(password));
		assertThat(dbUser.getUserRole().getId().equals(userRoleId));

		deleteUser(dbUser);
	}
	
}