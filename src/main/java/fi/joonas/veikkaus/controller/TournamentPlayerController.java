package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.service.PlayerService;
import fi.joonas.veikkaus.service.TournamentPlayerService;
import fi.joonas.veikkaus.service.TournamentTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(TOURNAMENT_PLAYER_URL)
@Slf4j
public class TournamentPlayerController {

    @Autowired
    private TournamentPlayerService tournamentPlayerService;

    @Autowired
    private TournamentTeamService tournamentTeamService;

    @Autowired
    private PlayerService playerService;

    @ModelAttribute(ALL_TOURNAMENT_TEAMS)
    public List<TournamentTeamGuiEntity> populateTournamentTeams() {

        return tournamentTeamService.findAllTournamentTeams();
    }

    @ModelAttribute(ALL_PLAYERS)
    public List<PlayerGuiEntity> populatePlayers() {

        return playerService.findAllPlayers();
    }

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("tournamentPlayers", tournamentPlayerService.findAllTournamentPlayers());
        return "viewTournamentPlayerList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

        TournamentPlayerGuiEntity tournamentPlayer = tournamentPlayerService.findOneTournamentPlayer(id);
        model.addAttribute("tournamentPlayer", tournamentPlayer);
        return "viewTournamentPlayerDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("tournamentPlayer", new TournamentPlayerGuiEntity());
        return "viewTournamentPlayerCreate";
    }

    /**
     * POST /postCreate --> Create a new tournamentPlayer and save it in the database.
     */
    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute TournamentPlayerGuiEntity tournamentPlayer) {

        Long tournamentPlayerId;
        try {
            tournamentPlayerId = tournamentPlayerService.insert(tournamentPlayer);
        } catch (Exception ex) {
            String msg = "Error creating the tournamentPlayer: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Tournament player successfully created with id = %s".formatted(tournamentPlayerId));
        return REDIRECT + TOURNAMENT_PLAYER_GET_ALL_URL;
    }

    /**
     * @param id    tournamentPlayer Id
     * @param model
     * @return Tournament player modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

        TournamentPlayerGuiEntity tournamentPlayer = tournamentPlayerService.findOneTournamentPlayer(id);
        model.addAttribute("tournamentPlayer", tournamentPlayer);
        return "viewTournamentPlayerModify";
    }

    /**
     * Saves modified tournamentPlayer data to DB
     *
     * @param tournamentPlayer
     * @return
     */
    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute TournamentPlayerGuiEntity tournamentPlayer) {

        Long tournamentPlayerId;
        try {
            tournamentPlayerId = tournamentPlayerService.modify(tournamentPlayer);
        } catch (Exception ex) {
            String msg = "Error updating the tournamentPlayer: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Tournament player successfully updated for id = %s".formatted(tournamentPlayerId));
        return REDIRECT + TOURNAMENT_PLAYER_GET_ALL_URL;
    }

    /**
     * @param id
     * @param model
     * @return Tournament player modify view
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        TournamentPlayerGuiEntity tournamentPlayer = tournamentPlayerService.findOneTournamentPlayer(id);
        model.addAttribute("tournamentPlayer", tournamentPlayer);
        return "viewTournamentPlayerDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute TournamentPlayerGuiEntity tournamentPlayer) {

        try {
            tournamentPlayerService.delete(tournamentPlayer.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the tournamentPlayer: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + TOURNAMENT_PLAYER_GET_ALL_URL;
    }
}