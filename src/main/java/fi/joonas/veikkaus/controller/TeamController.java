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

import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.service.TeamService;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(TEAM_URL)
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	@GetMapping(URL_GET_ALL)
	public String getAll(Model model) {
		model.addAttribute("teams", teamService.findAllTeams());
		return "viewTeamList";
	}

	@RequestMapping(URL_GET_DETAILS)
	public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
		TeamGuiEntity team = teamService.findOneTeam(id);
		model.addAttribute("team", team);
		return "viewTeamDetails";
	}

	@GetMapping(URL_GET_CREATE)
	public String getCreate(Model model) {
		model.addAttribute("team", new TeamGuiEntity());
		return "viewTeamCreate";
	}

	/**
	 * POST /postCreate --> Create a new team and save it in the database.
	 */
	@PostMapping(URL_POST_CREATE)
	public String postCreate(@ModelAttribute TeamGuiEntity team) {
		Long teamId = null;
		try {
			teamId = teamService.insert(team);
		} catch (Exception ex) {
			logger.error("Error creating the team: ", ex);
			return "Error creating the team: " + ex.toString();
		}
		logger.debug("Team successfully created with id = " + teamId);
		return REDIRECT + TEAM_GET_ALL_URL;
	}
	
	/**
	 * @param id Id of team
	 * @param model UI model
	 * @return Team modify view
	 */
	@RequestMapping(URL_GET_MODIFY)
	public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
		TeamGuiEntity team = teamService.findOneTeam(id);
		model.addAttribute("team", team);
		return "viewTeamModify";
	}

	/**
	 * Saves modified team data to DB
	 * 
	 * @param team Team UI entity
	 * @return Redirect URL for getting all teams
	 */
	@PostMapping(URL_POST_MODIFY)
	public String postModify(@ModelAttribute TeamGuiEntity team) {
		Long teamId = null;
		try {
			teamId = teamService.modify(team);
		} catch (Exception ex) {
			logger.error("Error updating the team: ", ex);
			return "Error updating the team: " + ex.toString();
		}
		logger.debug("Team successfully updated for id = " + teamId);
		return REDIRECT + TEAM_GET_ALL_URL;
	}
	
	/**
	 * @param id Team id
	 * @param model UI model
	 * @return Team delete view
	 */
	@RequestMapping(URL_GET_DELETE)
	public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
		TeamGuiEntity team = teamService.findOneTeam(id);
		model.addAttribute("team", team);
		return "viewTeamDelete";
	}

	/**
	 *
	 * @param team Team UI entity
	 * @return Redirect URL for getting all teams
	 */
	@PostMapping(URL_POST_DELETE)
	public String postDelete(@ModelAttribute TeamGuiEntity team) {
		try {
			teamService.delete(team.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the team: ", ex);
			return "Error deleting the team:" + ex.toString();
		}
		return REDIRECT + TEAM_GET_ALL_URL;
	}

}
