package fi.joonas.veikkaus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.jpaentity.UserRole;

@Controller
@RequestMapping("/userrole")
public class UserRoleController {

	@Autowired
	private UserRoleDao userRoleDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

	/**
	 * GET /create --> Create a new user role and save it in the database.
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String create(String roleName) {
		String userRoleId = "";
		try {
			UserRole userRole = new UserRole(roleName);
			userRoleDao.save(userRole);
			userRoleId = String.valueOf(userRole.getId());
		} catch (Exception ex) {
			logger.error("Error creating the user role: ", ex);
			return "Error creating the user role: " + ex.toString();
		}
		return "User succesfully created with id = " + userRoleId;
	}

	/**
	 * GET /delete --> Delete the user role having the passed id.
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			UserRole userRole = new UserRole(id);
			userRoleDao.delete(userRole);
		} catch (Exception ex) {
			logger.error("Error deleting the user role: ", ex);
			return "Error deleting the user role:" + ex.toString();
		}
		return "User role succesfully deleted!";
	}

}
