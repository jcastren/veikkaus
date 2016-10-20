package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
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

import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BetControllerTest extends JUnitTestUtil {

	//private static final Logger logger = LoggerFactory.getLogger(BetControllerTest.class);

	@Autowired
	private BetDao betDao;

	private String userId;
	
	private String statusId;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
		userId = addUser();
		statusId = addStatus();
	}
	
	@After
	public void destroy() throws Exception {
		deleteUser(userId);
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		String query = String.format(getFormattedStr(2),
				PARAM_NAME_USER_ID, getEncodedStr(userId),
				PARAM_NAME_STATUS_ID, getEncodedStr(statusId));
		String url = BET_CREATE_URL + "?" + query;
		String betId = callUrl(url, true);
		assertNotNull(betDao.findOne(Long.valueOf(betId)));
		
		query = String.format(getFormattedStr(1), PARAM_NAME_ID, getEncodedStr(betId));
		url = BET_DELETE_URL + "?" + query;
		callUrl(url, false);
		assertNull(betDao.findOne(Long.valueOf(betId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String betId = addBet();
		
		String query = String.format(getFormattedStr(2), 
				PARAM_NAME_ID, getEncodedStr(betId),
				PARAM_NAME_STATUS_ID, getEncodedStr(statusId));
		String url = BET_MODIFY_URL + "?" + query;
		String newBetId = callUrl(url, true);
		
		assertThat(betId.equals(newBetId));
		Bet newBet = betDao.findOne(Long.valueOf(newBetId));
		assertNotNull(newBet);
		assertThat(newBet.getStatus().getId().equals(statusId));
		
		deleteBet(betId);
	}

}