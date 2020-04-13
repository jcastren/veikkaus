package fi.joonas.veikkaus.controller;

import com.google.common.collect.ImmutableMap;
import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.util.JUnitTestUtil;
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
public class StatusControllerTest extends JUnitTestUtil {

	@Autowired
	private StatusDao statusDao;
	
	@Before
	public void setup() throws Exception {
		cleanDb();
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		String description = "hommat menee ku länsimetrossa";
		
		paramMap = ImmutableMap.<String, String>builder()
				.put(PARAM_NAME_STATUS_NUMBER, Integer.valueOf(STATUS_UNDER_WORK).toString())
				.put(PARAM_NAME_DESCRIPTION, description)
				.build();
		String statusId = callUrl(STATUS_CREATE_URL + getQuery(paramMap), true);
		Status dbStatus = statusDao.findById(Long.valueOf(statusId)).get();
		assertNotNull(dbStatus);
		assertThat(dbStatus.getId().equals(Long.valueOf(statusId)));
		assertThat(dbStatus.getStatusNumber() == STATUS_UNDER_WORK);
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, statusId).build();
		callUrl(STATUS_DELETE_URL + getQuery(paramMap), false);
		assertNull(statusDao.findById(Long.valueOf(statusId)));
	}
	
	@Test
	public void testModify() throws Exception {
		String description = "hommat kesken";
		String statusId = addStatus().getId().toString();
		
		paramMap = ImmutableMap.<String, String>builder().put(PARAM_NAME_ID, statusId)
				.put(PARAM_NAME_STATUS_NUMBER, Integer.valueOf(STATUS_COMPLETED).toString())
				.put(PARAM_NAME_DESCRIPTION, description)
						.build();
		String dbStatusId = callUrl(STATUS_MODIFY_URL + getQuery(paramMap), true);
		Status dbStatus = statusDao.findById(Long.valueOf(dbStatusId)).get();
		assertNotNull(dbStatus);
		assertThat(dbStatusId.equals(statusId));
		assertThat(dbStatus.getStatusNumber() == STATUS_COMPLETED);
		assertThat(dbStatus.getDescription().equals(description));

		deleteStatus(dbStatus);
	}

}