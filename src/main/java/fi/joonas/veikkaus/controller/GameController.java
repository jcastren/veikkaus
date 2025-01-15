package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.service.GameService;
import fi.joonas.veikkaus.service.TournamentService;
import fi.joonas.veikkaus.service.TournamentTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(GAME_URL)
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TournamentTeamService tournamentTeamService;

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @ModelAttribute(ALL_TOURNAMENTS)
    public List<TournamentGuiEntity> populateTournaments() {
        List<TournamentGuiEntity> tournamentList = new ArrayList<TournamentGuiEntity>();
        TournamentGuiEntity emptyEntry = TournamentGuiEntity.builder()
                .id(STRING_NOT_DEFINED)
                .name("-- empty choice --")
                .year("")
                .build();
        tournamentList.add(emptyEntry);
        tournamentList.addAll(tournamentService.findAllTournaments());
        return tournamentList;
    }

    @RequestMapping(value = "/getFragHomeTeams/{tournamentId}", method = RequestMethod.GET)
    public String getFragHomeTeams(Model model, @PathVariable("tournamentId") String tournamentId) {
        List<TournamentTeamGuiEntity> teamList = new ArrayList<TournamentTeamGuiEntity>();
        if (Long.valueOf(tournamentId) > 0) {
            teamList = tournamentTeamService.findTournamentTeamsByTournamentId(tournamentId);
        }
        model.addAttribute("homeTeamList", teamList);
        model.addAttribute("homeTeam", new TournamentTeamGuiEntity());
        return "fragments/fragHomeTeams :: homeTeamFragment";
    }

    @RequestMapping(value = "/getFragAwayTeams/{tournamentId}", method = RequestMethod.GET)
    public String getFragAwayTeams(Model model, @PathVariable("tournamentId") String tournamentId) {
        List<TournamentTeamGuiEntity> teamList = new ArrayList<TournamentTeamGuiEntity>();
        if (Long.valueOf(tournamentId) > 0) {
            teamList = tournamentTeamService.findTournamentTeamsByTournamentId(tournamentId);
        }
        model.addAttribute("awayTeamList", teamList);
        model.addAttribute("awayTeam", new TournamentTeamGuiEntity());
        return "fragments/fragAwayTeams :: awayTeamFragment";
    }

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {
        model.addAttribute("games", gameService.findAllGames());
        return "viewGameList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {
        GameGuiEntity game = gameService.findOneGame(id);
        model.addAttribute("game", game);
        return "viewGameDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {
        model.addAttribute("game", new GameGuiEntity());
        return "viewGameCreate";
    }

    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute GameGuiEntity game) {
        Long gameId = null;
        try {
            game.setTournament(new TournamentGuiEntity());
            game.setHomeTeam(new TournamentTeamGuiEntity());
            game.setAwayTeam(new TournamentTeamGuiEntity());
            String[] split = game.getId().split(",");
            game.getTournament().setId(split[0]);
            game.getHomeTeam().setId(split[1]);
            game.getAwayTeam().setId(split[2]);
            game.setId(null);
            gameId = gameService.insert(game);
        } catch (Exception ex) {
            logger.error("Error creating the game: ", ex);
            return "Error creating the game: " + ex;
        }
        logger.debug("Game successfully created with id = " + gameId);
        return REDIRECT + GAME_GET_ALL_URL;
    }

    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id", required = true) String id, Model model) {
        GameGuiEntity game = gameService.findOneGame(id);
        model.addAttribute("game", game);
        return "viewGameModify";
    }

    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute GameGuiEntity game) {
        String gameId = game.getId();
        try {
            game.setTournament(new TournamentGuiEntity());
            game.setHomeTeam(new TournamentTeamGuiEntity());
            game.setAwayTeam(new TournamentTeamGuiEntity());
            String[] split = game.getId().split(",");
            game.getTournament().setId(split[0]);
            game.getHomeTeam().setId(split[1]);
            game.getAwayTeam().setId(split[2]);
            game.setId(gameId);
            gameId = gameService.modify(game).toString();
        } catch (Exception ex) {
            logger.error("Error updating the game: ", ex);
            return "Error updating the game: " + ex.toString();
        }
        logger.debug("Game successfully updated for id = " + gameId);
        return REDIRECT + GAME_GET_ALL_URL;
    }

    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id", required = true) String id, Model model) {
        GameGuiEntity game = gameService.findOneGame(id);
        model.addAttribute("game", game);
        return "viewGameDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute GameGuiEntity game) {
        try {
            gameService.delete(game.getId());
        } catch (VeikkausServiceException vse) {
            logger.error("Error deleting the game: ", vse);
            return "Error deleting the game:" + vse.toString();
        }
        return REDIRECT + GAME_GET_ALL_URL;
    }

}
