package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.BetResultService;

@Controller
@RequestMapping(BET_RESULT)
public class BetResultController {

	@Autowired
	private BetResultService betResultService;
	
	private static final Logger logger = LoggerFactory.getLogger(BetResultController.class);

	/**
	 * GET /create --> Create a new betResult and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String betId, String gameId, String homeScore, String awayScore) {
		Long betResultId = null;
		try {
			betResultId = betResultService.insert(betId, gameId, homeScore, awayScore);
		} catch (Exception ex) {
			logger.error("Error creating the betResult: ", ex);
			return "Error creating the betResult: " + ex.toString();
		}
		return "betResult succesfully created with id = " + betResultId;
	}

	/**
	 * GET /delete --> Delete the betResult having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			betResultService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the betResult: ", ex);
			return "Error deleting the betResult:" + ex.toString();
		}
		return "betResult succesfully deleted!";
	}

	/**
	 * GET /modify --> Update the homeScore and awayScore for the
	 * betResult in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateBetResult(String id, String homeScore, String awayScore) {
		try {
			betResultService.modify(id, homeScore, awayScore);
		} catch (Exception ex) {
			logger.error("Error updating the betResult: ", ex);
			return "Error updating the betResult: " + ex.toString();
		}
		return "betResult succesfully updated for id = " + id;
	}

}