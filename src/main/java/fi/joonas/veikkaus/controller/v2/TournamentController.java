package fi.joonas.veikkaus.controller.v2;

import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/tournaments")
@Slf4j
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping()
    public List<TournamentGuiEntity> getTournaments() {
        return tournamentService.findAllTournaments();
    }

    @PostMapping()
    public String createTournament(@RequestBody TournamentGuiEntity tournament) {

        Long tournamentId;
        try {
            tournamentId = tournamentService.insert(tournament);
        } catch (Exception ex) {
            String msg = "Error creating the tournament: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Tournament successfully created with id = %s".formatted(tournamentId));
        return "Tournament created";
    }

    @DeleteMapping("/{id}")
    public String deleteTournament(@PathVariable Long id) {
        try {
            tournamentService.delete(id);
        } catch (Exception ex) {
            String msg = "Error deleting the tournament: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return "Tournament deleted";
    }

//    @GetMapping(URL_GET_ALL)
//    public String getAll(Model model) {
//        model.addAttribute("tournaments", tournamentService.findAllTournaments());
//        return "viewTournamentList";
//    }
//
//    @RequestMapping(URL_GET_DETAILS)
//    public String getDetails(@RequestParam(value = "id") String id, Model model) {
//
//        TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
//        model.addAttribute("tournament", tournament);
//        return "viewTournamentDetails";
//    }
//
//    @GetMapping(URL_GET_CREATE)
//    public String getCreate(Model model) {
//
//        model.addAttribute("tournament", new TournamentGuiEntity());
//        return "viewTournamentCreate";
//    }
//
//    /**
//     * POST /postCreate --> Create a new tournament and save it in the database.
//     */
//    @PostMapping(URL_POST_CREATE)
//    public String postCreate(@ModelAttribute TournamentGuiEntity tournament) {
//
//        Long tournamentId;
//        try {
//            tournamentId = tournamentService.insert(tournament);
//        } catch (Exception ex) {
//            String msg = "Error creating the tournament: %s".formatted(ex);
//            log.error(msg);
//            return msg;
//        }
//        log.debug("Tournament successfully created with id = %s".formatted(tournamentId));
//        return REDIRECT + TOURNAMENT_GET_ALL_URL;
//    }
//
//    /**
//     * @param id    tournament Id
//     * @param model
//     * @return Tournament modify view
//     */
//    @RequestMapping(URL_GET_MODIFY)
//    public String getModify(@RequestParam(value = "id") String id, Model model) {
//
//        TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
//        model.addAttribute("tournament", tournament);
//        return "viewTournamentModify";
//    }
//
//    /**
//     * Saves modified tournament data to DB
//     *
//     * @param tournament
//     * @return
//     */
//    @PostMapping(URL_POST_MODIFY)
//    public String postModify(@ModelAttribute TournamentGuiEntity tournament) {
//
//        Long tournamentId;
//        try {
//            tournamentId = tournamentService.modify(tournament);
//        } catch (Exception ex) {
//            String msg = "Error updating the tournament: %s".formatted(ex);
//            log.error(msg);
//            return msg;
//        }
//        log.debug("Tournament successfully updated for id = %s ".formatted(tournamentId));
//        return REDIRECT + TOURNAMENT_GET_ALL_URL;
//    }
//
//    /**
//     * @param id
//     * @param model
//     * @return delete view
//     */
//    @RequestMapping(URL_GET_DELETE)
//    public String getDelete(@RequestParam(value = "id") String id, Model model) {
//
//        TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
//        model.addAttribute("tournament", tournament);
//        return "viewTournamentDelete";
//    }
//
//    @PostMapping(URL_POST_DELETE)
//    public String postDelete(@ModelAttribute TournamentGuiEntity tournament) {
//
//        try {
//            tournamentService.delete(tournament.getId());
//        } catch (Exception ex) {
//            String msg = "Error deleting the tournament: %s".formatted(ex);
//            log.error(msg);
//            return msg;
//        }
//        return REDIRECT + TOURNAMENT_GET_ALL_URL;
//    }

}
