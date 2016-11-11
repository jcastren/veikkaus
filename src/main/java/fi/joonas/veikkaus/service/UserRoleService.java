package fi.joonas.veikkaus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.jpaentity.UserRole;

@Service
public class UserRoleService {
	
	@Autowired
	UserRoleDao userRoleDao;
	
	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public Long insert(UserRoleGuiEntity userRole) {
		return userRoleDao.save(convertGuiToDb(userRole)).getId();
	}
	
	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public Long modify(UserRoleGuiEntity userRole) {
		return userRoleDao.save(convertGuiToDb(userRole)).getId();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		boolean succeed = false;
		userRoleDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<UserRoleGuiEntity> findAllUserRoles() {
		List<UserRoleGuiEntity> geList = new ArrayList<>();
		List<UserRole> dbTourns =  ImmutableList.copyOf(userRoleDao.findAll());
		
		for (UserRole dbTourn : dbTourns) {
			geList.add(convertDbToGui(dbTourn));
		}
		
		return geList;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public UserRoleGuiEntity findOneUserRole(String id) {
		UserRoleGuiEntity tournGe = convertDbToGui(userRoleDao.findOne(Long.valueOf(id)));
		return tournGe;
	}
	
	protected static UserRoleGuiEntity convertDbToGui(UserRole db) {
		UserRoleGuiEntity ge = new UserRoleGuiEntity();
		
		ge.setId(db.getId().toString());
		ge.setName(db.getName());
		
		return ge;
	}
	
	protected static UserRole convertGuiToDb(UserRoleGuiEntity ge) {
		UserRole db = new UserRole();
		
		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setName(ge.getName());
		
		return db;
	}
	
}
