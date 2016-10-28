package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.GameService;

@Controller
@RequestMapping(GAME_URL)
public class GameController {

	@Autowired
	private GameService gameService;
	
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	/**
	 * GET /create --> Create a new game and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String homeTeamId, String awayTeamId, String homeScore, String awayScore, String gameDate) {
		Long gameId = null;
		try {
			gameId = gameService.insert(homeTeamId, awayTeamId, homeScore,  awayScore, gameDate);
		} catch (Exception ex) {
			logger.error("Error creating the game: ", ex);
			return "Error creating the game: " + ex.toString();
		}
		return "Game succesfully created with id = " + gameId;
	}

	/**
	 * GET /delete --> Delete the game having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			gameService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the game: ", ex);
			return "Error deleting the game:" + ex.toString();
		}
		return "Game succesfully deleted!";
	}

	/**
	 * GET /modify --> Update the homeTeam, awayTeam, homeScore, awayScore and gameDate for the
	 * game in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateGame(String id, String homeTeamId, String awayTeamId, String homeScore, String awayScore, String gameDate) {
		try {
			gameService.modify(id, homeTeamId, awayTeamId, homeScore,  awayScore, gameDate);
		} catch (Exception ex) {
			logger.error("Error updating the game: ", ex);
			return "Error updating the game: " + ex.toString();
		}
		return "Game succesfully updated for id = " + id;
	}

}
