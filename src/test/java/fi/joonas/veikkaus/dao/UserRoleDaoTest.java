package fi.joonas.veikkaus.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.joonas.veikkaus.jpaentity.UserRole;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleDaoTest extends JUnitTestUtil {

	@Autowired
	private UserRoleDao userRoleDao;

	private UserRole userRole;

	private String NAME_ADMIN = "ADMIN";

	@Before
	public void setup() throws Exception {
		cleanDb();
	}

	@Test
	public void testCreateAndDelete() throws Exception {
		userRole = new UserRole(NAME_ADMIN);
		UserRole dbUserRole = userRoleDao.save(userRole);
		assertThat(dbUserRole.getId() > 0);
		assertThat(userRole.getName().equals(dbUserRole.getName()));
		assertThat(userRoleDao.findOne(dbUserRole.getId()) != null);

		userRoleDao.delete(dbUserRole);
		assertThat(userRoleDao.findOne(dbUserRole.getId()) == null);
	}
	
	@Test
	public void testUpdate() throws Exception {
		userRole = new UserRole(NAME_ADMIN);
		UserRole dbUserRole = userRoleDao.save(userRole);

		String newName = NAME_ADMIN + "_new";
		dbUserRole.setName(newName);
		UserRole newDbUserRole = userRoleDao.save(dbUserRole);
		assertThat(userRoleDao.findOne(newDbUserRole.getId()) == null);
		assertThat(dbUserRole.getId().equals(newDbUserRole.getId()));
		assertThat(dbUserRole.getName().equals(newDbUserRole.getName()));
		
		userRoleDao.delete(newDbUserRole);
	}

}