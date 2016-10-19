package fi.joonas.veikkaus.dao;

import static fi.joonas.veikkaus.util.JUnitTestUtil.CLEAN_BEFORE_RUN_JUNIT_TESTS;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.joonas.veikkaus.jpaentity.UserRole;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleDaoTest {

	@Autowired
	private UserRoleDao userRoleDao;

	private UserRole userRole;

	private String ROLENAME_ADMIN = "ADMIN";

	@Before
	public void setup() {
		if (CLEAN_BEFORE_RUN_JUNIT_TESTS) {
			userRoleDao.deleteAll();
		}

	}

	@Test
	public void testCreateAndDelete() throws Exception {
		userRole = new UserRole(ROLENAME_ADMIN);
		UserRole dbUserRole = userRoleDao.save(userRole);
		assertThat(dbUserRole.getId() > 0);
		assertThat(userRole.getRoleName().equals(dbUserRole.getRoleName()));
		assertThat(userRoleDao.findOne(dbUserRole.getId()) != null);

		userRoleDao.delete(dbUserRole);
		assertThat(userRoleDao.findOne(dbUserRole.getId()) == null);
	}

}