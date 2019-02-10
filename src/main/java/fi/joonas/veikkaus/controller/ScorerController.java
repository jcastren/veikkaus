package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.ALL_GAMES;
import static fi.joonas.veikkaus.constants.VeikkausConstants.ALL_TOURNAMENT_PLAYERS;
import static fi.joonas.veikkaus.constants.VeikkausConstants.SCORER_GET_ALL_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.SCORER_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_PLAYER_GET_ALL_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_ALL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_MODIFY;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_CREATE;

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
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.service.GameService;
import fi.joonas.veikkaus.service.ScorerService;
import fi.joonas.veikkaus.service.TournamentPlayerService;

@Controller
@RequestMapping(SCORER_URL)
public class ScorerController {

	@Autowired
	private ScorerService scorerService;
	
	@Autowired
	private TournamentPlayerService tournamentPlayerService;
	
	@Autowired
	private GameService gameService;

	private static final Logger logger = LoggerFactory.getLogger(ScorerController.class);
	
   	@ModelAttribute(ALL_TOURNAMENT_PLAYERS)
    public List<TournamentPlayerGuiEntity> populateTournamentPlayers() {
        return tournamentPlayerService.findAllTournamentPlayers();
    }
	
   	@ModelAttribute(ALL_GAMES)
    public List<GameGuiEntity> populateGames() {
        return gameService.findAllGames();
    }
   	
	@GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("scorers", scorerService.findAllScorers());
		return "viewScorerList";
	}

	/**
	 * GET /create --> Create a new scorer and save it in the database.
	 */
//	@RequestMapping(URL_CREATE)
//	@ResponseBody
//	public String create(String tournamentPlayerId, String gameId) {
//		Long scorerId = null;
//		try {
//			scorerId = scorerService.insert(tournamentPlayerId, gameId);
//		} catch (Exception ex) {
//			logger.error("Error creating the scorer: ", ex);
//			return "Error creating the scorer: " + ex.toString();
//		}
//		return "Scorer succesfully created with id = " + scorerId;
//	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("scorer", new ScorerGuiEntity());
		return "viewScorerCreate";
	}
	
	/**
	 * POST /postCreate --> Create a new tournamentPlayer and save it in the database.
	 */
	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute ScorerGuiEntity scorer) {
		Long scorerId = null;
		try {
			scorerId = scorerService.insert(scorer);
		} catch (Exception ex) {
			logger.error("Error creating the scorer: ", ex);
			return "Error creating the scorer: " + ex.toString();
		}
		logger.debug("Scorer succesfully created with id = " + scorerId);
		return "redirect:"+ SCORER_GET_ALL_URL;
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