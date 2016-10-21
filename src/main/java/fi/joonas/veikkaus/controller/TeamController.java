package fi.joonas.veikkaus.controller;




import static fi.joonas.veikkaus.constants.VeikkausConstants.TEAM;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_MODIFY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.service.TeamService;

@Controller
@RequestMapping(TEAM)
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

	/**
	 * GET /create --> Create a new team and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	@ResponseBody
	public String create(String name) {		
		Long teamId = null;
		try {
			teamId = teamService.insert(name);
		} catch (Exception ex) {
			logger.error("Error creating the team: ", ex);
			return "Error creating the team: " + ex.toString();
		}
		return "Team succesfully created with id = " + teamId;
	}

	/**
	 * GET /delete --> Delete the team having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			teamService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the team: ", ex);
			return "Error deleting the team:" + ex.toString();
		}
		return "Team succesfully deleted!";
	}
	
	/**
	 * GET /modify --> Update the name for the
	 * team in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateTeam(String id, String name) {
		try {
			teamService.modify(id, name);
		} catch (Exception ex) {
			logger.error("Error updating the team: ", ex);
			return "Error updating the team: " + ex.toString();
		}
		return "Team succesfully updated for id = " + id;
	}

}
