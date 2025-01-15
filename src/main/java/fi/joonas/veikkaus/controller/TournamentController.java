package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(TOURNAMENT_URL)
@Slf4j
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {
        model.addAttribute("tournaments", tournamentService.findAllTournaments());
        return "viewTournamentList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

        TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
        model.addAttribute("tournament", tournament);
        return "viewTournamentDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("tournament", new TournamentGuiEntity());
        return "viewTournamentCreate";
    }

    /**
     * POST /postCreate --> Create a new tournament and save it in the database.
     */
    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute TournamentGuiEntity tournament) {

        Long tournamentId;
        try {
            tournamentId = tournamentService.insert(tournament);
        } catch (Exception ex) {
            String msg = "Error creating the tournament: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Tournament successfully created with id = %s".formatted(tournamentId));
        return REDIRECT + TOURNAMENT_GET_ALL_URL;
    }

    /**
     * @param id    tournament Id
     * @param model
     * @return Tournament modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

        TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
        model.addAttribute("tournament", tournament);
        return "viewTournamentModify";
    }

    /**
     * Saves modified tournament data to DB
     *
     * @param tournament
     * @return
     */
    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute TournamentGuiEntity tournament) {

        Long tournamentId;
        try {
            tournamentId = tournamentService.modify(tournament);
        } catch (Exception ex) {
            String msg = "Error updating the tournament: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Tournament successfully updated for id = %s ".formatted(tournamentId));
        return REDIRECT + TOURNAMENT_GET_ALL_URL;
    }

    /**
     * @param id
     * @param model
     * @return delete view
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
        model.addAttribute("tournament", tournament);
        return "viewTournamentDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute TournamentGuiEntity tournament) {

        try {
            tournamentService.delete(tournament.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the tournament: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + TOURNAMENT_GET_ALL_URL;
    }

}
