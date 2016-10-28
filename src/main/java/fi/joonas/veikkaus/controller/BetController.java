package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.BetService;

@Controller
@RequestMapping(BET_URL)
public class BetController {

	@Autowired
	private BetService betService;
	
	private static final Logger logger = LoggerFactory.getLogger(BetController.class);

	/**
	 * GET /create --> Create a new bet and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String userId, String statusId) {
		Long betId = null;
		try {
			betId = betService.insert(userId, statusId);
		} catch (Exception ex) {
			logger.error("Error creating the bet: ", ex);
			return "Error creating the bet: " + ex.toString();
		}
		return "bet succesfully created with id = " + betId;
	}

	/**
	 * GET /delete --> Delete the bet having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			betService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the bet: ", ex);
			return "Error deleting the bet:" + ex.toString();
		}
		return "bet succesfully deleted!";
	}

	/**
	 * GET /modify --> Update the user and status for the
	 * bet in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateBet(String id, String userId, String statusId) {
		try {
			betService.modify(id, userId, statusId);
		} catch (Exception ex) {
			logger.error("Error updating the bet: ", ex);
			return "Error updating the bet: " + ex.toString();
		}
		return "bet succesfully updated for id = " + id;
	}

}
