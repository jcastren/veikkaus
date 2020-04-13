package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		Bet dbBet = betDao.findById(Long.valueOf(betId)).get();
		assertNotNull(dbBet);
		assertThat(dbBet.getId().equals(Long.valueOf(betId)));
		assertThat(dbBet.getUser().getId().equals(user.getId()));
		assertThat(dbBet.getStatus().getId().equals(status.getId()));
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, betId).build();
		callUrl(BET_DELETE_URL + getQuery(paramMap), false);
		assertNull(betDao.findById(Long.valueOf(betId)));
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
		Bet dbBet = betDao.findById(Long.valueOf(dbBetId)).get();
		assertNotNull(dbBet);
		assertThat(dbBetId.equals(betId));
		assertThat(dbBet.getUser().getId().equals(userId));
		assertThat(dbBet.getStatus().getId().equals(statusId));

		deleteBet(bet);
	}

}