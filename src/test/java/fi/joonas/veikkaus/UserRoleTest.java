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

import static fi.joonas.veikkaus.util.JUnitTestUtil.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleTest {
	
    @Autowired
    private UserRoleDao userRoleDao;
    
    private UserRole userRole;
    
    private String ROLENAME_ADMIN = "ADMIN";
    
    @Before
    public void setup() {
    	if (CLEAN_BEFORE_RUN_JUNIT_TESTS) {
    		userRoleDao.deleteAll();
    	}
    	userRole = new UserRole(ROLENAME_ADMIN);
    	userRoleDao.save(userRole);
  }
    
    @After
    public void clean() {
    	userRoleDao.delete(userRole.getId());
    }

}