package fi.joonas.veikkaus.controller;

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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.service.TournamentService;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(TOURNAMENT_URL)
public class TournamentController extends WebMvcConfigurerAdapter {

	@Autowired
	private TournamentService tournamentService;

	private static final Logger logger = LoggerFactory.getLogger(TournamentController.class);
	
	@GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("tournaments", tournamentService.findAllTournaments());
		return "viewTournamentList";
	}

	@RequestMapping(URL_GET_DETAILS)
	public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
		TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
		model.addAttribute("tournament", tournament);
		return "viewTournamentDetails";
	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("tournament", new TournamentGuiEntity());
		return "viewTournamentCreate";
	}

	/**
	 * POST /postCreate --> Create a new tournament and save it in the database.
	 */
	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute TournamentGuiEntity tournament) {
		Long tournamentId = null;
		try {
			tournamentId = tournamentService.insert(tournament);
		} catch (Exception ex) {
			logger.error("Error creating the tournament: ", ex);
			return "Error creating the tournament: " + ex.toString();
		}
		logger.debug("Tournament successfully created with id = " + tournamentId);
		return REDIRECT + TOURNAMENT_GET_ALL_URL;
	}
	
	/**
	 * @param id tournament Id
	 * @param model
	 * @return Tournament modify view
	 */
	@RequestMapping(URL_GET_MODIFY)
	public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
		TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
		model.addAttribute("tournament", tournament);
		return "viewTournamentModify";
	}

	/**
	 * Saves modified tournament data to DB
	 * 
	 * @param tournament
	 * @return
	 */
	@PostMapping(URL_POST_MODIFY)
	public String postModify(@ModelAttribute TournamentGuiEntity tournament) {
		Long tournamentId = null;
		try {
			tournamentId = tournamentService.modify(tournament);
		} catch (Exception ex) {
			logger.error("Error updating the tournament: ", ex);
			return "Error updating the tournament: " + ex.toString();
		}
		logger.debug("Tournament successfully updated for id = " + tournamentId);
		return REDIRECT + TOURNAMENT_GET_ALL_URL;
	}
	
	/**
	 * @param tournament
	 * @param model
	 * @return Tournament modify view
	 */
	@RequestMapping(URL_GET_DELETE)
	public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
		TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
		model.addAttribute("tournament", tournament);
		return "viewTournamentDelete";
	}

	@PostMapping(URL_POST_DELETE)
	public String postDelete(@ModelAttribute TournamentGuiEntity tournament) {
		try {
			tournamentService.delete(tournament.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the tournament: ", ex);
			return "Error deleting the tournament:" + ex.toString();
		}
		return REDIRECT + TOURNAMENT_GET_ALL_URL;
	}

}
