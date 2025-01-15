package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
import fi.joonas.veikkaus.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(PLAYER_URL)
@Slf4j
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("players", playerService.findAllPlayers());
        return "viewPlayerList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id", required = true) String id, Model model) {

        PlayerGuiEntity player = playerService.findOnePlayer(id);
        model.addAttribute("player", player);
        return "viewPlayerDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("player", new PlayerGuiEntity());
        return "viewPlayerCreate";
    }

    /**
     * POST /postCreate --> Create a new player and save it in the database.
     */
    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute PlayerGuiEntity player) {

        Long playerId;
        try {
            playerId = playerService.insert(player);
        } catch (Exception ex) {
            String msg = "Error creating the player: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Player successfully created with id = %s".formatted(playerId));
        return REDIRECT + PLAYER_GET_ALL_URL;
    }

    /**
     * @param id    player Id
     * @param model
     * @return Player modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

        PlayerGuiEntity player = playerService.findOnePlayer(id);
        model.addAttribute("player", player);
        return "viewPlayerModify";
    }

    /**
     * Saves modified player data to DB
     *
     * @param player
     * @return
     */
    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute PlayerGuiEntity player) {

        Long playerId;
        try {
            playerId = playerService.modify(player);
        } catch (Exception ex) {
            String msg = "Error updating the player: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Player successfully updated for id = %s".formatted(playerId));
        return REDIRECT + PLAYER_GET_ALL_URL;
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        PlayerGuiEntity player = playerService.findOnePlayer(id);
        model.addAttribute("player", player);
        return "viewPlayerDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute PlayerGuiEntity player) {

        try {
            playerService.delete(player.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the player: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + PLAYER_GET_ALL_URL;
    }

}
