/*package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BetControllerTest extends JUnitTestUtil {

	private static final Logger logger = LoggerFactory.getLogger(BetControllerTest.class);

	@Autowired
	private BetDao betDao;
	
	@Autowired
	private UserDao userDao;

	@Before
	public void setup() throws Exception {
		String paramValueRoleName = "ADMIN";
		String query = String.format(getFormattedStr(1), PARAM_NAME_ROLENAME, getEncodedStr(paramValueRoleName));
		String url = USER_ROLE_CREATE_URL + "?" + query;
		String userRoleId = callUrl(url, true);
		
		
	}
	
	@After
	public void destroy() throws Exception {
		String query = String.format(getFormattedStr(1), PARAM_NAME_ID, getEncodedStr(userRoleId));
		String url = USER_ROLE_DELETE_URL + "?" + query;
		callUrl(url, false);
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		String email = "eemeli";
		String name = "nimi";
		String password = "salainensana";
		String query = String.format(getFormattedStr(4), 
				PARAM_NAME_EMAIL, getEncodedStr(email), 
				PARAM_NAME_NAME, getEncodedStr(name), 
				PARAM_NAME_PASSWORD, getEncodedStr(password), 
				PARAM_NAME_USER_ROLE_ID, getEncodedStr(userRoleId));
		String url = USER_CREATE_URL + "?" + query;
		String userId = callUrl(url, true);
		assertNotNull(userDao.findOne(Long.valueOf(userId)));
		
		query = String.format(getFormattedStr(1), PARAM_NAME_ID, getEncodedStr(userId));
		url = USER_DELETE_URL + "?" + query;
		callUrl(url, false);
		assertNull(userDao.findOne(Long.valueOf(userId)));
	}

}*/