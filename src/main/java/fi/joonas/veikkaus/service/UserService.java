package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserRoleDao userRoleDao;

	public Long insert(String email, String name, String password, String userRoleId) throws VeikkausServiceException {
		UserRole userRole = userRoleDao.findOne(Long.valueOf(userRoleId));

		if (userRole == null) {
			throw new VeikkausServiceException("userRole with id: " + userRoleId + " wasn't found, insert failed");
		}
		return userDao.save(new User(email, name, password, userRole)).getId();
	}
	
	public Long modify(String id, String email, String name, String password, String userRoleId) throws VeikkausServiceException {
		User user = userDao.findOne(Long.valueOf(id));
		
		if (user == null) {
			throw new VeikkausServiceException("user with id: " + id + " wasn't found, modify failed");
		}
		
		UserRole userRole = userRoleDao.findOne(Long.valueOf(userRoleId));
		
		if (userRole == null) {
			throw new VeikkausServiceException("userRole with id: " + userRoleId + " wasn't found, modify failed");
		}
		
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setRole(userRole);
		return userDao.save(user).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		userDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

}
