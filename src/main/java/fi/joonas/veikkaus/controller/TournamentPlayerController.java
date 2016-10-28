package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.TournamentPlayerService;

@Controller
@RequestMapping(TOURNAMENT_PLAYER_URL)
public class TournamentPlayerController {

	@Autowired
	private TournamentPlayerService tournamentPlayerService;
	
	private static final Logger logger = LoggerFactory.getLogger(TournamentPlayerController.class);

	/**
	 * GET /create --> Create a new tournamentPlayer and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String tournamentTeamId, String playerId, String goals) {
		Long tournamentPlayerId = null;
		try {
			tournamentPlayerId = tournamentPlayerService.insert(tournamentTeamId, playerId, goals);
		} catch (Exception ex) {
			logger.error("Error creating the tournamentPlayer: ", ex);
			return "Error creating the tournamentPlayer: " + ex.toString();
		}
		return "TournamentPlayer succesfully created with id = " + tournamentPlayerId;
	}

	/**
	 * GET /modify --> Update the tournamentTeam, player and goals for the
	 * tournamentPlayer in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateTournamentPlayer(String id, String tournamentTeamId, String playerId, String goals) {
		try {
			tournamentPlayerService.modify(id, tournamentTeamId, playerId, goals);
		} catch (Exception ex) {
			logger.error("Error updating the tournamentPlayer: ", ex);
			return "Error updating the tournamentPlayer: " + ex.toString();
		}
		return "TournamentPlayer succesfully updated for id = " + id;
	}
	
	/**
	 * GET /delete --> Delete the tournamentPlayer having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			tournamentPlayerService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the tournamentPlayer: ", ex);
			return "Error deleting the tournamentPlayer:" + ex.toString();
		}
		return "TournamentPlayer succesfully deleted!";
	}

}