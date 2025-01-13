package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserDaoTest extends JUnitTestUtil {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    private User user;
    private UserRole userRole;

    private String ROLENAME_ADMIN = "ADMIN";

    @BeforeEach
    public void setup() throws Exception {
        cleanDb();
        userRole = new UserRole(ROLENAME_ADMIN);
        userRoleDao.save(userRole);

        user = new User("email", "name", "password", userRole);
    }

    @AfterEach
    public void clean() {
        userDao.deleteById(user.getId());
        userRoleDao.deleteById(userRole.getId());
    }

    @Test
    public void testFindByEmail() {
        String email = user.getEmail();
        userDao.save(user);

        User findByEmail = userDao.findByEmail(email);

        assertThat(findByEmail.getEmail().equals(email));
        assertThat(findByEmail.getEmail().equals(email));
    }

    @Test
    public void testModifyUser() {
        User userDb = userDao.save(user);

        String email = userDb.getEmail();

        String newEmail = email + "_new";
        user.setEmail(newEmail);

        userDb = userDao.save(user);

        assertThat(userDb.getEmail().equals(newEmail));
    }
    
    /*
    @Test
	public void testCreateAndDelete() throws Exception {
		userRole = new UserRole(NAME_ADMIN);
		UserRole dbUserRole = userRoleDao.save(userRole);
		assertThat(dbUserRole.getId() > 0);
		assertThat(userRole.getName().equals(dbUserRole.getName()));
		assertThat(userRoleDao.findById(dbUserRole.getId()) != null);

		userRoleDao.deleteById(dbUserRole);
		assertThat(userRoleDao.findById(dbUserRole.getId()) == null);
	}
	
	@Test
	public void testUpdate() throws Exception {
		userRole = new UserRole(NAME_ADMIN);
		UserRole dbUserRole = userRoleDao.save(userRole);

		String newName = NAME_ADMIN + "_new";
		dbUserRole.setName(newName);
		UserRole newDbUserRole = userRoleDao.save(dbUserRole);
		assertThat(userRoleDao.findById(newDbUserRole.getId()) == null);
		assertThat(dbUserRole.getId().equals(newDbUserRole.getId()));
		assertThat(dbUserRole.getName().equals(newDbUserRole.getName()));
		
		userRoleDao.deleteById(newDbUserRole);
	}
*/
}