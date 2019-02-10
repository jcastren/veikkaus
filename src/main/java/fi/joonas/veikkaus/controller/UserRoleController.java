package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_ALL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_DETAILS;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_MODIFY;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_MODIFY;
import static fi.joonas.veikkaus.constants.VeikkausConstants.USER_ROLE_GET_ALL_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.USER_ROLE_URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.service.UserRoleService;

@Controller
@RequestMapping(USER_ROLE_URL)
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

	@GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("userRoles", userRoleService.findAllUserRoles());
		return "viewUserRoleList";
	}

	@RequestMapping(URL_GET_DETAILS)
	public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
		UserRoleGuiEntity userRole = userRoleService.findOneUserRole(id);
		model.addAttribute("userRole", userRole);
		return "viewUserRoleDetails";
	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("userRole", new UserRoleGuiEntity());
		return "viewUserRoleCreate";
	}

	/**
	 * POST /postCreate --> Create a new userRole and save it in the database.
	 */
	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute UserRoleGuiEntity userRole) {
		Long userRoleId = null;
		try {
			userRoleId = userRoleService.insert(userRole);
		} catch (Exception ex) {
			logger.error("Error creating the userRole: ", ex);
			return "Error creating the userRole: " + ex.toString();
		}
		logger.debug("UserRole succesfully created with id = " + userRoleId);
		return "redirect:"+ USER_ROLE_GET_ALL_URL;
	}
	
	/**
	 * @param userRole
	 * @param model
	 * @return UserRole modify view
	 */
	@RequestMapping(URL_GET_MODIFY)
	public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
		UserRoleGuiEntity userRole = userRoleService.findOneUserRole(id);
		model.addAttribute("userRole", userRole);
		return "viewUserRoleModify";
	}

	/**
	 * Saves modified userRole data to DB
	 * 
	 * @param userRole
	 * @return
	 */
	@PostMapping(URL_POST_MODIFY)
	public String postModify(@ModelAttribute UserRoleGuiEntity userRole) {
		Long userRoleId = null;
		try {
			userRoleId = userRoleService.modify(userRole);
		} catch (Exception ex) {
			logger.error("Error updating the userRole: ", ex);
			return "Error updating the userRole: " + ex.toString();
		}
		logger.debug("UserRole succesfully updated for id = " + userRoleId);
		return "redirect:" + USER_ROLE_GET_ALL_URL;
	}
	
	/**
	 * @param userRole
	 * @param model
	 * @return UserRole modify view
	 */
	@RequestMapping(URL_GET_DELETE)
	public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
		UserRoleGuiEntity userRole = userRoleService.findOneUserRole(id);
		model.addAttribute("userRole", userRole);
		return "viewUserRoleDelete";
	}

	@PostMapping(URL_POST_DELETE)
	public String postDelete(@ModelAttribute UserRoleGuiEntity userRole) {
		try {
			userRoleService.delete(userRole.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the userRole: ", ex);
			return "Error deleting the userRole:" + ex.toString();
		}
		return "redirect:" + USER_ROLE_GET_ALL_URL;
	}

}
