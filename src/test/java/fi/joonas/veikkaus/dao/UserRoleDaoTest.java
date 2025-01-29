package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.UserRole;
import fi.joonas.veikkaus.util.JUnitTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRoleDaoTest extends JUnitTestUtil {

    @Autowired
    private UserRoleDao userRoleDao;

    private UserRole userRole;

    private final String NAME_ADMIN = "ADMIN";

    @BeforeEach
    public void setup() throws Exception {

        cleanDb();
    }

    @Test
    public void testCreateAndDelete() throws Exception {

        userRole = new UserRole(NAME_ADMIN);
        UserRole dbUserRole = userRoleDao.save(userRole);

        assertThat(dbUserRole.getId() > 0);
        assertThat(userRole.getName().equals(dbUserRole.getName()));
        assertThat(userRoleDao.findById(dbUserRole.getId()) != null);

        userRoleDao.delete(dbUserRole);
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

        userRoleDao.delete(newDbUserRole);
    }

}