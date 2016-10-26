package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.BET_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.BET_DELETE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.BET_MODIFY_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_STATUS_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_USER_ID;
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

import com.google.common.collect.ImmutableMap;

import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BetControllerTest extends JUnitTestUtil {

	@Autowired
	private BetDao betDao;

	private User user;
	
	private Status status;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
		user = addUser();
		status = addStatus();
	}
	
	@After
	public void destroy() throws Exception {
		deleteUser(user);
		deleteStatus(status);
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_USER_ID, user.getId().toString())
				.put(PARAM_NAME_STATUS_ID, status.getId().toString())
				.build();
		String betId = callUrl(BET_CREATE_URL + getQuery(paramMap), true);
		Bet dbBet = betDao.findOne(Long.valueOf(betId));
		assertNotNull(dbBet);
		assertThat(dbBet.getId().equals(Long.valueOf(betId)));
		assertThat(dbBet.getUser().getId().equals(user.getId()));
		assertThat(dbBet.getStatus().getId().equals(status.getId()));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, betId).build();
		callUrl(BET_DELETE_URL + getQuery(paramMap), false);
		assertNull(betDao.findOne(Long.valueOf(betId)));
	}
	
	@Test
	public void testModify() throws Exception {
		Bet bet = addBet();
		String betId = bet.getId().toString();
		
		// We have to be careful with userId and statusId used as @Before annotation creates
		// a user and status in addition to addBet method and we don't want to delete
		// that user and status before @After annotation
		String userId = bet.getUser().getId().toString();
		String statusId = bet.getStatus().getId().toString();
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_ID, betId)
				.put(PARAM_NAME_USER_ID, userId)
				.put(PARAM_NAME_STATUS_ID, statusId)
				.build();
		String dbBetId = callUrl(BET_MODIFY_URL + getQuery(paramMap), true);
		Bet dbBet = betDao.findOne(Long.valueOf(dbBetId));
		assertNotNull(dbBet);
		assertThat(betId.equals(dbBetId));
		assertThat(dbBet.getUser().getId().equals(userId));
		assertThat(dbBet.getStatus().getId().equals(statusId));

		deleteBet(bet);
	}

}