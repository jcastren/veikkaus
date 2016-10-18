package fi.joonas.veikkaus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.PlayerService;

@Controller
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerService playerService;
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

	/**
	 * GET /create --> Create a new player and save it in the database.
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String create(String firstName, String lastName) {
		Long playerId = null;
		try {
			playerId = playerService.insert(firstName, lastName);
		} catch (Exception ex) {
			logger.error("Error creating the player: ", ex);
			return "Error creating the player: " + ex.toString();
		}
		return "Player succesfully created with id = " + playerId;
	}

	/**
	 * GET /delete --> Delete the player having the passed id.
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id) {
		try {
			playerService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the player: ", ex);
			return "Error deleting the player:" + ex.toString();
		}
		return "Player succesfully deleted!";
	}

	/**
	 * GET /update --> Update the firstName and lastName for the player in the
	 * database having the passed id.
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updatePlayer(String id, String firstName, String lastName) {
		try {
			playerService.modify(id, firstName, lastName);
		} catch (Exception ex) {
			logger.error("Error updating the player: ", ex);
			return "Error updating the player: " + ex.toString();
		}
		return "Player succesfully updated!";
	}

}
