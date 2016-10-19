package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.jpaentity.UserRole;

@Service
public class UserRoleService {
	
	@Autowired
	UserRoleDao userRoleDao;
	
	public Long insert(String roleName) {
		return userRoleDao.save(new UserRole(roleName)).getId();
	}
	
	public Long modify(String id, String roleName) {
		UserRole userRole = userRoleDao.findOne(Long.valueOf(id)); 
		userRole.setRoleName(roleName);
		return userRoleDao.save(userRole).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		userRoleDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	

}
