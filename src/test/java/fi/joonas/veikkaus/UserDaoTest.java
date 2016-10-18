/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fi.joonas.veikkaus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
	public boolean CLEAN_BEFORE_RUN = true;
	
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserRoleDao userRoleDao;
    
    private User user;
    private UserRole userRole;
    
    private String ROLENAME_ADMIN = "ADMIN";
    
    @Before
    public void setup() {
    	if (CLEAN_BEFORE_RUN) {
    		userDao.deleteAll();
    		userRoleDao.deleteAll();
    	}
    	
    	userRole = new UserRole(ROLENAME_ADMIN);
    	userRoleDao.save(userRole);
    }
    
    @After
    public void clean() {
    	userDao.delete(user.getId());
    	userRoleDao.delete(userRole.getId());
    }

    @Test
    public void testFindByLastName() {
        user = new User("first", "last", "password", userRole);
        userDao.save(user);

        User findByEmail = userDao.findByEmail(user.getEmail());

        assertThat(findByEmail.getEmail().equals(user.getEmail()));
    }
}