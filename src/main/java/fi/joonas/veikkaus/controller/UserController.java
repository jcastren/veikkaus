package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.UserGuiEntity;
import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.service.UserRoleService;
import fi.joonas.veikkaus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(USER_URL)
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ModelAttribute(ALL_USER_ROLES)
    public List<UserRoleGuiEntity> populateTournaments() {
        return userRoleService.findAllUserRoles();
    }

    @GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("users", userService.findAllUsers());
		return "viewUserList";
	}

	@RequestMapping(URL_GET_DETAILS)
	public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
		UserGuiEntity user = userService.findOneUser(id);
		model.addAttribute("user", user);
		return "viewUserDetails";
	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("user", new UserGuiEntity());
		return "viewUserCreate";
	}

	/**
	 * POST /postCreate --> Create a new user and save it in the database.
	 */
	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute UserGuiEntity user) {
		Long userId = null;
		try {
			userId = userService.insert(user);
		} catch (Exception ex) {
			logger.error("Error creating the user: ", ex);
			return "Error creating the user: " + ex.toString();
		}
		logger.debug("User successfully created with id = " + userId);
		
		return REDIRECT + USER_GET_ALL_URL;
	}
	
	/**
	 * @param id user Id
	 * @param model
	 * @return User modify view
	 */
	@RequestMapping(URL_GET_MODIFY)
	public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
		UserGuiEntity user = userService.findOneUser(id);
		model.addAttribute("user", user);
		return "viewUserModify";
	}

	/**
	 * Saves modified user data to DB
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(URL_POST_MODIFY)
	public String postModify(@ModelAttribute UserGuiEntity user) {
		Long userId = null;
		try {
			userId = userService.modify(user);
		} catch (Exception ex) {
			logger.error("Error updating the user: ", ex);
			return "Error updating the user: " + ex.toString();
		}
		logger.debug("User successfully updated for id = " + userId);
		return REDIRECT + USER_GET_ALL_URL;
	}
	
	/**
	 * @param user
	 * @param model
	 * @return User modify view
	 */
	@RequestMapping(URL_GET_DELETE)
	public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
		UserGuiEntity user = userService.findOneUser(id);
		model.addAttribute("user", user);
		return "viewUserDelete";
	}

	@PostMapping(URL_POST_DELETE)
	public String postDelete(@ModelAttribute UserGuiEntity user) {
		try {
			userService.delete(user.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the user: ", ex);
			return "Error deleting the user:" + ex.toString();
		}
		return REDIRECT + USER_GET_ALL_URL;
	}
}
