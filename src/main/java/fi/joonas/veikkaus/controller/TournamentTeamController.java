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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.ALL_TEAMS;
import static fi.joonas.veikkaus.constants.VeikkausConstants.ALL_TOURNAMENTS;
import static fi.joonas.veikkaus.constants.VeikkausConstants.REDIRECT;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_TEAM_GET_ALL_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_TEAM_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_ALL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_DETAILS;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_GET_MODIFY;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_CREATE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_POST_MODIFY;

@Controller
@RequestMapping(TOURNAMENT_TEAM_URL)
@Slf4j
public class TournamentTeamController {

    private final TournamentTeamService tournamentTeamService;
    private final TournamentService tournamentService;
    private final TeamService teamService;

    @Autowired
    public TournamentTeamController(TournamentTeamService tournamentTeamService, TournamentService tournamentService, TeamService teamService) {
        this.tournamentTeamService = tournamentTeamService;
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

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
