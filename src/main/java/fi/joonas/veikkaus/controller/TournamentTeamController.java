package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.service.TeamService;
import fi.joonas.veikkaus.service.TournamentService;
import fi.joonas.veikkaus.service.TournamentTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(TOURNAMENT_TEAM_URL)
@Slf4j
public class TournamentTeamController {

    @Autowired
    private TournamentTeamService tournamentTeamService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TeamService teamService;

    @ModelAttribute(ALL_TOURNAMENTS)
    public List<TournamentGuiEntity> populateTournaments() {

        return tournamentService.findAllTournaments();
    }

    @ModelAttribute(ALL_TEAMS)
    public List<TeamGuiEntity> populateTeams() {

        return teamService.findAllTeams();
    }

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("tournamentTeams", tournamentTeamService.findAllTournamentTeams());
        return "viewTournamentTeamList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

        TournamentTeamGuiEntity tournamentTeam = tournamentTeamService.findOneTournamentTeam(id);
        model.addAttribute("tournamentTeam", tournamentTeam);
        return "viewTournamentTeamDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("tournamentTeam", new TournamentTeamGuiEntity());
        return "viewTournamentTeamCreate";
    }

    /**
     * POST /postCreate --> Create a new tournamentTeam and save it in the database.
     */
    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute TournamentTeamGuiEntity tournamentTeam) {

        Long tournamentTeamId;
        try {
            tournamentTeamId = tournamentTeamService.insert(tournamentTeam);
        } catch (Exception ex) {
            String msg = "Error creating the tournamentTeam: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Tournament team successfully created with id = %s".formatted(tournamentTeamId));
        return REDIRECT + TOURNAMENT_TEAM_GET_ALL_URL;
    }

    /**
     * @param id    tournamentTeam Id
     * @param model
     * @return Tournament modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

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

        Long tournamentTeamId;
        try {
            tournamentTeamId = tournamentTeamService.modify(tournamentTeam);
        } catch (Exception ex) {
            String msg = "Error updating the tournamentTeam: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Tournament team successfully updated for id = %s".formatted(tournamentTeamId));
        return REDIRECT + TOURNAMENT_TEAM_GET_ALL_URL;
    }

    /**
     * @param id
     * @param model
     * @return Tournament team modify view
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        TournamentTeamGuiEntity tournamentTeam = tournamentTeamService.findOneTournamentTeam(id);
        model.addAttribute("tournamentTeam", tournamentTeam);
        return "viewTournamentTeamDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute TournamentTeamGuiEntity tournamentTeam) {

        try {
            tournamentTeamService.delete(tournamentTeam.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the tournamentTeam: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + TOURNAMENT_TEAM_GET_ALL_URL;
    }

}
