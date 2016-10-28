package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.TournamentTeamService;

@Controller
@RequestMapping(TOURNAMENT_TEAM_URL)
public class TournamentTeamController {

	@Autowired
	private TournamentTeamService tournamentTeamService;
	
	private static final Logger logger = LoggerFactory.getLogger(TournamentTeamController.class);

	/**
	 * GET /create --> Create a new tournamentTeam and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String tournamentId, String teamId) {
		Long tournamentTeamId = null;
		try {
			tournamentTeamId = tournamentTeamService.insert(tournamentId, teamId);
		} catch (Exception ex) {
			logger.error("Error creating the tournamentTeam: ", ex);
			return "Error creating the tournamentTeam: " + ex.toString();
		}
		return "tournamentTeam succesfully created with id = " + tournamentTeamId;
	}

	/**
	 * GET /delete --> Delete the tournamentTeam having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			tournamentTeamService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the tournamentTeam: ", ex);
			return "Error deleting the tournamentTeam:" + ex.toString();
		}
		return "tournamentTeam succesfully deleted!";
	}

	/**
	 * GET /modify --> Update the tournament and team for the
	 * tournamentTeam in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateTournamentTeam(String id, String tournamentId, String teamId) {
		try {
			tournamentTeamService.modify(id, tournamentId, teamId);
		} catch (Exception ex) {
			logger.error("Error updating the tournamentTeam: ", ex);
			return "Error updating the tournamentTeam: " + ex.toString();
		}
		return "tournamentTeam succesfully updated for id = " + id;
	}

}
