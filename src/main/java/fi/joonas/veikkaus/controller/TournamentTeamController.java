package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_TEAM_GET_ALL_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_TEAM_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_ALL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_DETAILS;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_MODIFY;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_MODIFY;

import java.util.List;

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

import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.service.TeamService;
import fi.joonas.veikkaus.service.TournamentService;
import fi.joonas.veikkaus.service.TournamentTeamService;

@Controller
@RequestMapping(TOURNAMENT_TEAM_URL)
public class TournamentTeamController {

	@Autowired
	private TournamentTeamService tournamentTeamService;
	
	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private TeamService teamService;

	private static final Logger logger = LoggerFactory.getLogger(TournamentTeamController.class);
	
    @ModelAttribute("allTournaments")
    public List<TournamentGuiEntity> populateTournaments() {
        return tournamentService.findAllTournaments();
    }

    @ModelAttribute("allTeams")
    public List<TeamGuiEntity> populateTeams() {
        return teamService.findAllTeams();
    }
	
	@GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("tournamentTeams", tournamentTeamService.findAllTournamentTeams());
		return "viewTournamentTeamList";
	}

	@RequestMapping(URL_GET_DETAILS)
	public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
		TournamentTeamGuiEntity tournamentTeam = tournamentTeamService.findOneTournamentTeam(id);
		model.addAttribute("tournamentTeam", tournamentTeam);
		return "viewTournamentTeamDetails";
	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("tournamentTeam", new TournamentTeamGuiEntity());
		model.addAttribute("tournament", new TournamentGuiEntity());
		
		return "viewTournamentTeamCreate";
	}

	/**
	 * POST /postCreate --> Create a new tournamentTeam and save it in the database.
	 */
	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute TournamentTeamGuiEntity tournamentTeam) {
			//@ModelAttribute String tournament) {
		Long tournamentTeamId = null;
		
		//logger.debug(String.format("tournamentTeam: %s %s", tournamentTeam.getId(), tournamentTeam.getTournament().getName()));
		
		
		try {
			tournamentTeamId = tournamentTeamService.insert(tournamentTeam);
		} catch (Exception ex) {
			logger.error("Error creating the tournamentTeam: ", ex);
			return "Error creating the tournamentTeam: " + ex.toString();
		}
		logger.debug("Tournament team succesfully created with id = " + tournamentTeamId);
		
		return "redirect:"+ TOURNAMENT_TEAM_GET_ALL_URL;
	}
	
	/**
	 * @param tournamentTeam
	 * @param model
	 * @return Tournament modify view
	 */
	@RequestMapping(URL_GET_MODIFY)
	public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
		TournamentTeamGuiEntity tournamentTeam = tournamentTeamService.findOneTournamentTeam(id);
		model.addAttribute("tournamentTeam", tournamentTeam);
		return "viewTournamentTeamModify";
	}

	/**
	 * Saves modified tournamentTeam data to DB
	 * 
	 * @param tournamentTeam
	 * @return
	 */
	@PostMapping(URL_POST_MODIFY)
	public String postModify(@ModelAttribute TournamentTeamGuiEntity tournamentTeam) {
		Long tournamentTeamId = null;
		try {
			tournamentTeamId = tournamentTeamService.modify(tournamentTeam);
		} catch (Exception ex) {
			logger.error("Error updating the tournamentTeam: ", ex);
			return "Error updating the tournamentTeam: " + ex.toString();
		}
		logger.debug("Tournament team succesfully updated for id = " + tournamentTeamId);
		return "redirect:" + TOURNAMENT_TEAM_GET_ALL_URL;
	}
	
	/**
	 * @param tournamentTeam
	 * @param model
	 * @return Tournament team modify view
	 */
	@RequestMapping(URL_GET_DELETE)
	public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
		TournamentTeamGuiEntity tournamentTeam = tournamentTeamService.findOneTournamentTeam(id);
		model.addAttribute("tournamentTeam", tournamentTeam);
		return "viewTournamentTeamDelete";
	}

	@PostMapping(URL_POST_DELETE)
	public String postDelete(@ModelAttribute TournamentTeamGuiEntity tournamentTeam) {
		try {
			tournamentTeamService.delete(tournamentTeam.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the tournamentTeam: ", ex);
			return "Error deleting the tournamentTeam:" + ex.toString();
		}
		return "redirect:" + TOURNAMENT_TEAM_GET_ALL_URL;
	}

}
