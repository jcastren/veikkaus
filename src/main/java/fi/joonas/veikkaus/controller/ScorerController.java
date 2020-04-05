package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.service.GameService;
import fi.joonas.veikkaus.service.ScorerService;
import fi.joonas.veikkaus.service.TournamentPlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

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
		logger.debug("Scorer successfully created with id = " + scorerId);
		return REDIRECT + SCORER_GET_ALL_URL;
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
		return "Scorer successfully updated for id = " + id;
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
		return "Scorer successfully deleted!";
	}

}