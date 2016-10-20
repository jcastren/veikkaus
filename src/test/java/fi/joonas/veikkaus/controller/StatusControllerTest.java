package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_ID;
import static fi.joonas.veikkaus.constants.VeikkausConstants.PARAM_NAME_STATUS_NUMBER;
import static fi.joonas.veikkaus.constants.VeikkausConstants.STATUS_COMPLETED;
import static fi.joonas.veikkaus.constants.VeikkausConstants.STATUS_CREATE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.STATUS_DELETE_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.STATUS_MODIFY_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.STATUS_UNDER_WORK;
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

import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StatusControllerTest extends JUnitTestUtil {

	//private static final Logger logger = LoggerFactory.getLogger(StatusControllerTest.class);

	@Autowired
	private StatusDao statusDao;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		String query = String.format(getFormattedStr(1), 
				PARAM_NAME_STATUS_NUMBER, getEncodedStr(Integer.valueOf(STATUS_UNDER_WORK).toString()));
		String url = STATUS_CREATE_URL + "?" + query;
		String statusId = callUrl(url, true);
		Status status = statusDao.findOne(Long.valueOf(statusId));
		assertNotNull(status);
		assertThat(status.getId().equals(Long.valueOf(statusId)));
		assertThat(status.getStatusNumber() == STATUS_UNDER_WORK);
		
		query = String.format(getFormattedStr(1), PARAM_NAME_ID, getEncodedStr(statusId));
		url = STATUS_DELETE_URL + "?" + query;
		callUrl(url, false);
		assertNull(statusDao.findOne(Long.valueOf(statusId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String statusId = addStatus();
		
		String query = String.format(getFormattedStr(2), 
				PARAM_NAME_ID, getEncodedStr(statusId),
				PARAM_NAME_STATUS_NUMBER, getEncodedStr(Integer.valueOf(STATUS_COMPLETED).toString()));
		String url = STATUS_MODIFY_URL + "?" + query;
		String newStatusId = callUrl(url, true);
		
		assertThat(statusId.equals(newStatusId));
		Status newStatus = statusDao.findOne(Long.valueOf(newStatusId));
		assertNotNull(newStatus);
		assertThat(newStatus.getStatusNumber() == STATUS_COMPLETED);
		
		deleteStatus(statusId);
	}

}