package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.BetGuiEntity;
import fi.joonas.veikkaus.guientity.BetResultGuiEntity;
import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.service.BetResultService;
import fi.joonas.veikkaus.service.BetService;
import fi.joonas.veikkaus.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(BET_RESULT_URL)
public class BetResultController {

	@Autowired
	private BetResultService betResultService;

	@Autowired
	private BetService betService;

	@Autowired
	private GameService gameService;

	private static final Logger logger = LoggerFactory.getLogger(BetResultController.class);

	@ModelAttribute(ALL_BETS)
	public List<BetGuiEntity> populateBets() {
		return betService.findAllBets();
	}

	@ModelAttribute(ALL_GAMES)
	public List<GameGuiEntity> populateGames() {
		return gameService.findAllGames();
	}

	@GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("betResults", betResultService.findAllBetResults());
		return "viewBetResultList";
	}

	@RequestMapping(URL_GET_DETAILS)
	public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
		BetResultGuiEntity betResult = betResultService.findOneBetResult(id);
		model.addAttribute("betResult", betResult);
		return "viewBetResultDetails";
	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("betResult", new BetResultGuiEntity());
		return "viewBetResultCreate";
	}

	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute BetResultGuiEntity betResult) {
		Long betResultId = null;
		try {
			betResultId = betResultService.insert(betResult);
		} catch (Exception ex) {
			logger.error("Error creating the bet result: ", ex);
			return "Error creating the bet result: " + ex.toString();
		}
		logger.debug("Bet result successfully created with id = " + betResultId);
		return REDIRECT + BET_RESULT_GET_ALL_URL;
	}

	@RequestMapping(URL_GET_MODIFY)
	public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
		BetResultGuiEntity betResult = betResultService.findOneBetResult(id);
		model.addAttribute("betResult", betResult);
		return "viewBetResultModify";
	}

	@PostMapping(URL_POST_MODIFY)
	public String postModify(@ModelAttribute BetResultGuiEntity betResult) {
		Long betResultId = null;
		try {
			betResultId = betResultService.modify(betResult);
		} catch (Exception ex) {
			logger.error("Error updating the bet result: ", ex);
			return "Error updating the bet result: " + ex.toString();
		}
		logger.debug("Bet result successfully updated for id = " + betResultId);
		return REDIRECT + BET_GET_DETAILS_URL + betResult.getBet().getId();
	}

	@RequestMapping(URL_GET_DELETE)
	public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
		BetResultGuiEntity betResult = betResultService.findOneBetResult(id);
		model.addAttribute("betResult", betResult);
		return "viewBetResultDelete";
	}

	@PostMapping(URL_POST_DELETE)
	public String postDelete(@ModelAttribute BetResultGuiEntity betResult) {
		try {
			betResultService.delete(betResult.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the bet result: ", ex);
			return "Error deleting the bet result:" + ex.toString();
		}
		return REDIRECT + BET_GET_DETAILS_URL + betResult.getBet().getId();
	}

}