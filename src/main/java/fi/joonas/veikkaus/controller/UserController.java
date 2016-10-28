package fi.joonas.veikkaus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.service.UserService;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(USER_URL)
public class UserController {

	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * GET /create --> Create a new user and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String email, String name, String password, String userRoleId) {
		Long userId = null;
		try {
			userId = userService.insert(email, name, password, userRoleId);
		} catch (Exception ex) {
			logger.error("Error creating the user: ", ex);
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created with id = " + userId;
	}

	/**
	 * GET /delete --> Delete the user having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			userService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the user: ", ex);
			return "Error deleting the user:" + ex.toString();
		}
		return "User succesfully deleted!";
	}

	/**
	 * GET /get-by-email --> Return the id for the user having the passed email.
	 */
	@RequestMapping(URL_GET_BY_EMAIL)
	@ResponseBody
	public String getByEmail(String email) {
		String userId = "";
		try {
			User user = userService.findByEmail(email);
			userId = user.getId().toString();
		} catch (Exception ex) {
			logger.error("User not found: ", ex);
			return "User not found";
		}
		return "The user id is: " + userId;
	}

	/**
	 * GET /modify --> Update the email, name, password and user role for the
	 * user in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateUser(String id, String email, String name, String password, String userRoleId) {
		try {
			userService.modify(id, email, name, password, userRoleId);
		} catch (Exception ex) {
			logger.error("Error updating the user: ", ex);
			return "Error updating the user: " + ex.toString();
		}
		return "User succesfully updated for id = " + id;
	}

}
