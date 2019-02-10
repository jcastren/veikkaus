package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.STATUS_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_MODIFY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.StatusService;

@Controller
@RequestMapping(STATUS_URL)
public class StatusController {

	@Autowired
	private StatusService statusService;
	
	private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

	/**
	 * GET /create --> Create a new status and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String statusNumber, String description) {		
		Long statusId = null;
		try {
			statusId = statusService.insert(statusNumber, description);
		} catch (Exception ex) {
			logger.error("Error creating the status: ", ex);
			return "Error creating the status: " + ex.toString();
		}
		return "Status succesfully created with id = " + statusId;
	}

	/**
	 * GET /delete --> Delete the status having the passed id.
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id) {
		try {
			statusService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the status: ", ex);
			return "Error deleting the status:" + ex.toString();
		}
		return "Status succesfully deleted!";
	}
	
	/**
	 * GET /modify --> Update the roleName for the
	 * status in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateStatus(String id, String statusNumber, String description) {
		try {
			statusService.modify(id, statusNumber, description);
		} catch (Exception ex) {
			logger.error("Error updating the status: ", ex);
			return "Error updating the status: " + ex.toString();
		}
		return "Status succesfully updated for id = " + id;
	}

}
