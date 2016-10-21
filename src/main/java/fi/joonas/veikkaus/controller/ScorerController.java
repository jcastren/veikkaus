package fi.joonas.veikkaus.controller;


import static fi.joonas.veikkaus.constants.VeikkausConstants.SCORER;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_MODIFY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.ScorerService;

@Controller
@RequestMapping(SCORER)
public class ScorerController {

	@Autowired
	private ScorerService scorerService;
	
	private static final Logger logger = LoggerFactory.getLogger(ScorerController.class);

	/**
	 * GET /create --> Create a new scorer and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String tournamentPlayerId, String gameId) {
		Long scorerId = null;
		try {
			scorerId = scorerService.insert(tournamentPlayerId, gameId);
		} catch (Exception ex) {
			logger.error("Error creating the scorer: ", ex);
			return "Error creating the scorer: " + ex.toString();
		}
		return "Scorer succesfully created with id = " + scorerId;
	}

	/**
	 * GET /modify --> Update the tournamentPlayer and game for the
	 * scorer in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateScorer(String id, String tournamentPlayerId, String gameId) {
		try {
			scorerService.modify(id, tournamentPlayerId, gameId);
		} catch (Exception ex) {
			logger.error("Error updating the scorer: ", ex);
			return "Error updating the scorer: " + ex.toString();
		}
		return "Scorer succesfully updated for id = " + id;
	}

	/**
	 * GET /delete --> Delete the scorer having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			scorerService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the scorer: ", ex);
			return "Error deleting the scorer:" + ex.toString();
		}
		return "Scorer succesfully deleted!";
	}

}