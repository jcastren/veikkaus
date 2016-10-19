package fi.joonas.veikkaus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.UserRoleService;

@Controller
@RequestMapping("/userrole")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

	/**
	 * GET /create --> Create a new user role and save it in the database.
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String create(String roleName) {		
		Long userRoleId = null;
		try {
			userRoleId = userRoleService.insert(roleName);
		} catch (Exception ex) {
			logger.error("Error creating the user role: ", ex);
			return "Error creating the user role: " + ex.toString();
		}
		return "User role succesfully created with id = " + userRoleId;
	}

	/**
	 * GET /delete --> Delete the user role having the passed id.
	 */
	@RequestMapping("/delete")
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

}
