package fi.joonas.veikkaus.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;
import fi.joonas.veikkaus.util.JUnitTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest extends JUnitTestUtil {
	
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserRoleDao userRoleDao;
    
    private User user;
    private UserRole userRole;
    
    private String ROLENAME_ADMIN = "ADMIN";
    
    @Before
    public void setup() throws Exception {
    	cleanDb();
    	userRole = new UserRole(ROLENAME_ADMIN);
    	userRoleDao.save(userRole);
    	
    	user = new User("email", "name", "password", userRole);
    }
    
    @After
    public void clean() {
    	userDao.delete(user.getId());
    	userRoleDao.delete(userRole.getId());
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
*/
}