package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.*;
import fi.joonas.veikkaus.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(BET_URL)
@Slf4j
public class BetController {

    @Autowired
    private BetService betService;

    @Autowired
    private UserService userService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private BetResultService betResultService;

    @Autowired
    private GameService gameService;

    @ModelAttribute(ALL_USERS)
    public List<UserGuiEntity> populateUsers() {

        return userService.findAllUsers();
    }

    @ModelAttribute(ALL_TOURNAMENTS)
    public List<TournamentGuiEntity> populateTournaments() {

        return tournamentService.findAllTournaments();
    }

    @ModelAttribute(ALL_STATUSES)
    public List<StatusGuiEntity> populateStatuses() {

        return statusService.findAllStatuses();
    }

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("bets", betService.findAllBets());
        return "viewBetList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

        BetGuiEntity bet = betService.findOneBet(id);
        model.addAttribute("bet", bet);

        List<BetResultGuiEntity> betResults = betResultService.findBetGamesAndBetResults(id);
        model.addAttribute("betResults", betResults);
//		List<ModelAttribute> betResultAttributes = new ArrayList<>();
//		betResults.forEach(betResult ->
////				betResultAttributes.add(new ModelAttribute(value = "betResult" BetResultGuiEntity betResult))
//				ModelAttribute attr = new ModelAttribute();
//				);

//		for (BetResultGuiEntity betResult : betResults) {
//			ModelAttribute attr = new ModelAttribute(betResult);
//		}

        List<GameGuiEntity> tournamentGames = gameService.findTournamentGames(bet.getTournament().getId());
        model.addAttribute("tournamentGames", tournamentGames);

        log.debug("Bet successfully retrieved for id = %s".formatted(id));

        return "viewBetDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("bet", new BetGuiEntity());
        return "viewBetCreate";
    }

    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute BetGuiEntity bet) {

        Long betId;
        try {
            betId = betService.insert(bet);
        } catch (Exception ex) {
            String msg = "Error creating the bet: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Bet successfully created with id = %s".formatted(betId));
        return REDIRECT + BET_GET_ALL_URL;
    }

    /**
     * Method creates a new bet result / modifies an existing one
     *
     * @param betResult
     * @return
     */
    @PostMapping(BET_POST_BET_RESULT_SAVE)
//	public String postBetResultSave(@ModelAttribute(value="betResult") BetResultGuiEntity betResult, BindingResult bindingResult) {
    public String postBetResultSave(@ModelAttribute BetResultGuiEntity betResult) {
//		if (bindingResult.hasErrors()) {
//			return "viewBetDetails";
//		}

        Long betResultId;
        try {
            if (betResult.getId().equals(STRING_NOT_DEFINED)) {
                betResultId = betResultService.insert(betResult);
            } else {
                betResultId = betResultService.modify(betResult);
            }
        } catch (Exception ex) {
            String msg = "Error updating the betResult: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Bet result successfully saved with id = %s".formatted(betResultId));
        return REDIRECT + BET_GET_DETAILS_URL + betResult.getBet().getId();
    }

    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

        BetGuiEntity bet = betService.findOneBet(id);
        model.addAttribute("bet", bet);
        return "viewBetModify";
    }

    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute BetGuiEntity bet) {

        Long betId;
        try {
            betId = betService.modify(bet);
        } catch (Exception ex) {
            String msg = "Error updating the bet: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Bet successfully updated for id = %s".formatted(betId));
        return REDIRECT + BET_GET_ALL_URL;
    }

    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        BetGuiEntity bet = betService.findOneBet(id);
        model.addAttribute("bet", bet);
        return "viewBetDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute BetGuiEntity bet) {

        try {
            betService.delete(bet.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the bet: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + BET_GET_ALL_URL;
    }
}
