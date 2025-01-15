package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(TEAM_URL)
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("teams", teamService.findAllTeams());
        return "viewTeamList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

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

        Long teamId;
        try {
            teamId = teamService.insert(team);
        } catch (Exception ex) {
            String msg = "Error creating the team: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Team successfully created with id = %s".formatted(teamId));
        return REDIRECT + TEAM_GET_ALL_URL;
    }

    /**
     * @param id    Id of team
     * @param model UI model
     * @return Team modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

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

        Long teamId;
        try {
            teamId = teamService.modify(team);
        } catch (Exception ex) {
            String msg = "Error updating the team: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Team successfully updated for id = %s ".formatted(teamId));
        return REDIRECT + TEAM_GET_ALL_URL;
    }

    /**
     * @param id    Team id
     * @param model UI model
     * @return Team delete view
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        TeamGuiEntity team = teamService.findOneTeam(id);
        model.addAttribute("team", team);
        return "viewTeamDelete";
    }

    /**
     * @param team Team UI entity
     * @return Redirect URL for getting all teams
     */
    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute TeamGuiEntity team) {

        try {
            teamService.delete(team.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the team: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + TEAM_GET_ALL_URL;
    }

}
