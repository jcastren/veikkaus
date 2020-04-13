package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.UserGuiEntity;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserRoleDao userRoleDao;

	/**
	 * 
	 * @param userGe
	 * @return
	 */
	public Long insert(UserGuiEntity userGe) throws VeikkausServiceException {
		String userRoleId = userGe.getUserRole().getId();
		Optional<UserRole> userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId));
		if (!userRoleDb.isPresent()) {
			throw new VeikkausServiceException("User role with id: " + userRoleId + " wasn't found, insert failed");
		}

		/** TODO Why is userRole set again? Did it miss originally some fields????? */
		userGe.setUserRole(UserRoleService.convertDbToGui(userRoleDb.get()));

		return userDao.save(convertGuiToDb(userGe)).getId();
	}
	
	/**
	 * 
	 * @param userGe
	 * @return
	 */
	public Long modify(UserGuiEntity userGe) throws VeikkausServiceException {
		String id = userGe.getId();
		Optional<User> userDb = userDao.findById(Long.valueOf(id));
		if (!userDb.isPresent()) {
			throw new VeikkausServiceException("User with id: " + id + " wasn't found, modify failed");
		}
			
		String userRoleId = userGe.getUserRole().getId();
		Optional<UserRole> userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId));
		if (!userRoleDb.isPresent()) {
			throw new VeikkausServiceException("User role with id: " + id + " wasn't found, modify failed");
		}
		
		userGe.setUserRole(UserRoleService.convertDbToGui(userRoleDb.get()));
		
		return userDao.save(convertGuiToDb(userGe)).getId();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id) throws VeikkausServiceException {
		boolean succeed = false;
		userDao.deleteById(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<UserGuiEntity> findAllUsers() {
		List<UserGuiEntity> geList = new ArrayList<>();
		List<User> dbUsers =  ImmutableList.copyOf(userDao.findAll());
		
		for (User dbUser : dbUsers) {
			geList.add(convertDbToGui(dbUser));
		}
		
		return geList;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public UserGuiEntity findOneUser(String id) {
		UserGuiEntity userGe = convertDbToGui(userDao.findById(Long.valueOf(id)).get());
		return userGe;
	}
	
	protected static UserGuiEntity convertDbToGui(User db) {
		UserGuiEntity ge = new UserGuiEntity();
		
		ge.setId(db.getId().toString());
		ge.setEmail(db.getEmail());
		ge.setName(db.getName());
		ge.setPassword(db.getPassword());
		ge.setUserRole(UserRoleService.convertDbToGui(db.getUserRole()));
		return ge;
	}
	
	protected static User convertGuiToDb(UserGuiEntity ge) {
		User db = new User();
		
		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setEmail(ge.getEmail());
		db.setName(ge.getName());
		db.setPassword(ge.getPassword());
		db.setUserRole(UserRoleService.convertGuiToDb(ge.getUserRole()));
		
		return db;
	}

}
