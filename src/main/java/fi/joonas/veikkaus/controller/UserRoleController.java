package fi.joonas.veikkaus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.UserRoleService;
import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(USER_ROLE)
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

	/**
	 * GET /create --> Create a new user role and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String name) {		
		Long userRoleId = null;
		try {
			userRoleId = userRoleService.insert(name);
		} catch (Exception ex) {
			logger.error("Error creating the user role: ", ex);
			return "Error creating the user role: " + ex.toString();
		}
		return "User role succesfully created with id = " + userRoleId;
	}

	/**
	 * GET /delete --> Delete the user role having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			userRoleService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the user role: ", ex);
			return "Error deleting the user role:" + ex.toString();
		}
		return "User role succesfully deleted!";
	}
	
	/**
	 * GET /modify --> Update the roleName for the
	 * user role in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateUserRole(String id, String name) {
		try {
			userRoleService.modify(id, name);
		} catch (Exception ex) {
			logger.error("Error updating the user role: ", ex);
			return "Error updating the user role: " + ex.toString();
		}
		return "User role succesfully updated for id = " + id;
	}

}
