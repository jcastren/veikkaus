package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ROLENAME;
import static fi.joonas.veikkaus.constants.VeikkausConstants.USER_ROLE_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.USER_ROLE_DELETE_URL;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest // (webEnvironment=DEFINED_PORT)
@WebAppConfiguration
public class UserRoleControllerTest extends JUnitTestUtil {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleControllerTest.class);

	@Autowired
	private UserRoleDao userRoleDao;

	
	@Test
	public void testCreateAndDelete() throws Exception {
		String paramValueRoleName = "ADMIN";
		String query = String.format(getFormattedStr(1), PARAM_NAME_ROLENAME, getEncodedStr(paramValueRoleName));
		String url = USER_ROLE_CREATE_URL + "?" + query;
		String userRoleId = callUrl(url, true);
		assertNotNull(userRoleDao.findOne(Long.valueOf(userRoleId)));
		
		query = String.format(getFormattedStr(1), PARAM_NAME_ID, getEncodedStr(userRoleId));
		url = USER_ROLE_DELETE_URL + "?" + query;
		callUrl(url, false);
		assertNull(userRoleDao.findOne(Long.valueOf(userRoleId)));
	}

}