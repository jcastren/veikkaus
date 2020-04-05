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

import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
import fi.joonas.veikkaus.service.PlayerService;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(PLAYER_URL)
public class PlayerController {

	@Autowired
	private PlayerService playerService;
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

	@GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("players", playerService.findAllPlayers());
		return "viewPlayerList";
	}

	@RequestMapping(URL_GET_DETAILS)
	public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
		PlayerGuiEntity player = playerService.findOnePlayer(id);
		model.addAttribute("player", player);
		return "viewPlayerDetails";
	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("player", new PlayerGuiEntity());
		return "viewPlayerCreate";
	}

	/**
	 * POST /postCreate --> Create a new player and save it in the database.
	 */
	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute PlayerGuiEntity player) {
		Long playerId = null;
		try {
			playerId = playerService.insert(player);
		} catch (Exception ex) {
			logger.error("Error creating the player: ", ex);
			return "Error creating the player: " + ex.toString();
		}
		logger.debug("Player successfully created with id = " + playerId);
		return REDIRECT + PLAYER_GET_ALL_URL;
	}
	
	/**
	 * @param id player Id
	 * @param model
	 * @return Player modify view
	 */
	@RequestMapping(URL_GET_MODIFY)
	public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
		PlayerGuiEntity player = playerService.findOnePlayer(id);
		model.addAttribute("player", player);
		return "viewPlayerModify";
	}

	/**
	 * Saves modified player data to DB
	 * 
	 * @param player
	 * @return
	 */
	@PostMapping(URL_POST_MODIFY)
	public String postModify(@ModelAttribute PlayerGuiEntity player) {
		Long playerId = null;
		try {
			playerId = playerService.modify(player);
		} catch (Exception ex) {
			logger.error("Error updating the player: ", ex);
			return "Error updating the player: " + ex.toString();
		}
		logger.debug("Player successfully updated for id = " + playerId);
		return REDIRECT + PLAYER_GET_ALL_URL;
	}
	
	/**
	 * @param player
	 * @param model
	 * @return Player modify view
	 */
	@RequestMapping(URL_GET_DELETE)
	public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
		PlayerGuiEntity player = playerService.findOnePlayer(id);
		model.addAttribute("player", player);
		return "viewPlayerDelete";
	}

	@PostMapping(URL_POST_DELETE)
	public String postDelete(@ModelAttribute PlayerGuiEntity player) {
		try {
			playerService.delete(player.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the player: ", ex);
			return "Error deleting the player:" + ex.toString();
		}
		return REDIRECT + PLAYER_GET_ALL_URL;
	}

}
