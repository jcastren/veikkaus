package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.UserGuiEntity;
import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.service.UserRoleService;
import fi.joonas.veikkaus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(USER_URL)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @ModelAttribute(ALL_USER_ROLES)
    public List<UserRoleGuiEntity> populateTournaments() {

        return userRoleService.findAllUserRoles();
    }

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("users", userService.findAllUsers());
        return "viewUserList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

        UserGuiEntity user = userService.findOneUser(id);
        model.addAttribute("user", user);
        return "viewUserDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("user", new UserGuiEntity());
        return "viewUserCreate";
    }

    /**
     * POST /postCreate --> Create a new user and save it in the database.
     */
    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute UserGuiEntity user) {

        Long userId;
        try {
            userId = userService.insert(user);
        } catch (Exception ex) {
            String msg = "Error creating the user: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("User successfully created with id = %s".formatted(userId));
        return REDIRECT + USER_GET_ALL_URL;
    }

    /**
     * @param id    user Id
     * @param model
     * @return User modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

        UserGuiEntity user = userService.findOneUser(id);
        model.addAttribute("user", user);
        return "viewUserModify";
    }

    /**
     * Saves modified user data to DB
     *
     * @param user
     * @return
     */
    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute UserGuiEntity user) {
        Long userId = null;
        try {
            userId = userService.modify(user);
        } catch (Exception ex) {
            String msg = "Error updating the user: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("User successfully updated for id = %s".formatted(userId));
        return REDIRECT + USER_GET_ALL_URL;
    }

    /**
     * @param id
     * @param model
     * @return User modify view
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        UserGuiEntity user = userService.findOneUser(id);
        model.addAttribute("user", user);
        return "viewUserDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute UserGuiEntity user) {
        try {
            userService.delete(user.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the user: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + USER_GET_ALL_URL;
    }
}
