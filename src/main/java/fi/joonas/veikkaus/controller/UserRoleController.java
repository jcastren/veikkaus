package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(USER_ROLE_URL)
@Slf4j
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("userRoles", userRoleService.findAllUserRoles());
        return "viewUserRoleList";
    }

    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

        UserRoleGuiEntity userRole = userRoleService.findOneUserRole(id);
        model.addAttribute("userRole", userRole);
        return "viewUserRoleDetails";
    }

    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("userRole", new UserRoleGuiEntity());
        return "viewUserRoleCreate";
    }

    /**
     * POST /postCreate --> Create a new userRole and save it in the database.
     */
    @PostMapping(URL_POST_CREATE)
    public String postCreate(@ModelAttribute UserRoleGuiEntity userRole) {

        Long userRoleId;
        try {
            userRoleId = userRoleService.insert(userRole);
        } catch (Exception ex) {
            String msg = "Error creating the userRole: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("UserRole successfully created with id = %s".formatted(userRoleId));
        return REDIRECT + USER_ROLE_GET_ALL_URL;
    }

    /**
     * @param id    userRole Id
     * @param model
     * @return UserRole modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

        UserRoleGuiEntity userRole = userRoleService.findOneUserRole(id);
        model.addAttribute("userRole", userRole);
        return "viewUserRoleModify";
    }

    /**
     * Saves modified userRole data to DB
     *
     * @param userRole
     * @return
     */
    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute UserRoleGuiEntity userRole) {

        Long userRoleId;
        try {
            userRoleId = userRoleService.modify(userRole);
        } catch (Exception ex) {
            String msg = "Error updating the userRole: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("UserRole successfully updated for id = %s".formatted(userRoleId));
        return REDIRECT + USER_ROLE_GET_ALL_URL;
    }

    /**
     * @param id    userRole Id
     * @param model
     * @return UserRole modify view
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        UserRoleGuiEntity userRole = userRoleService.findOneUserRole(id);
        model.addAttribute("userRole", userRole);
        return "viewUserRoleDelete";
    }

    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute UserRoleGuiEntity userRole) {

        try {
            userRoleService.delete(userRole.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the userRole: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + USER_ROLE_GET_ALL_URL;
    }

}
