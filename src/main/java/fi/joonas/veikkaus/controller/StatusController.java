package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.StatusGuiEntity;
import fi.joonas.veikkaus.service.StatusService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

@Controller
@RequestMapping(STATUS_URL)
@Slf4j
public class StatusController {

    @Autowired
    private StatusService statusService;

    /**
     * Method is called by Thymeleaf view to get status list view
     *
     * @param model UI model
     * @return Status list view
     */
    @GetMapping(URL_GET_ALL)
    public String getAll(Model model) {

        model.addAttribute("statuses", statusService.findAllStatuses());
        return "viewStatusList";
    }

    /**
     * Method is called by Thymeleaf view to get status details view
     *
     * @param model UI model
     * @return Status details view
     */
    @RequestMapping(URL_GET_DETAILS)
    public String getDetails(@RequestParam(value = "id") String id, Model model) {

        StatusGuiEntity status = statusService.findOneStatus(id);
        model.addAttribute("status", status);
        return "viewStatusDetails";
    }

    /**
     * Method is called by Thymeleaf template to get create status view
     *
     * @return Status create view
     */
    @GetMapping(URL_GET_CREATE)
    public String getCreate(Model model) {

        model.addAttribute("status", new StatusGuiEntity());
        return "viewStatusCreate";
    }

    /**
     * Saves created status data to DB
     *
     * @param status        Status UI entity
     * @param bindingResult Used for checking validation errors
     * @return Redirect URL for getting all statuses
     */
    @PostMapping(URL_POST_CREATE)
    public String postCreate(@Valid @ModelAttribute(value = "status") StatusGuiEntity status, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "viewStatusCreate";
        }

        Long statusId;
        try {
            statusId = statusService.insert(status);
        } catch (Exception ex) {
            String msg = "Error creating the status: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Status successfully created with id = %s".formatted(statusId));
        return REDIRECT + STATUS_GET_ALL_URL;
    }

    /**
     * Method is called by Thymeleaf view to get modify status view
     *
     * @param id    Status id
     * @param model UI model
     * @return Status modify view
     */
    @RequestMapping(URL_GET_MODIFY)
    public String getModify(@RequestParam(value = "id") String id, Model model) {

        StatusGuiEntity status = statusService.findOneStatus(id);
        model.addAttribute("status", status);
        return "viewStatusModify";
    }

    /**
     * Saves modified status data to DB
     *
     * @param status Status UI entity
     * @return Redirect URL for getting all statuses
     */
    @PostMapping(URL_POST_MODIFY)
    public String postModify(@ModelAttribute StatusGuiEntity status) {

        Long statusId;
        try {
            statusId = statusService.modify(status);
        } catch (Exception ex) {
            String msg = "Error updating the status: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        log.debug("Status successfully updated for id = " + statusId);
        return REDIRECT + STATUS_GET_ALL_URL;
    }

    /**
     * Method is called by Thymeleaf view to get delete status view
     *
     * @param id    Status id
     * @param model UI model
     * @return Status delete view
     */
    @RequestMapping(URL_GET_DELETE)
    public String getDelete(@RequestParam(value = "id") String id, Model model) {

        StatusGuiEntity status = statusService.findOneStatus(id);
        model.addAttribute("status", status);
        return "viewStatusDelete";
    }

    /**
     * Deletes status from DB
     *
     * @param status Status UI entity
     * @return Redirect URL for getting all statuses
     */
    @PostMapping(URL_POST_DELETE)
    public String postDelete(@ModelAttribute StatusGuiEntity status) {

        try {
            statusService.delete(status.getId());
        } catch (Exception ex) {
            String msg = "Error deleting the status: %s".formatted(ex);
            log.error(msg);
            return msg;
        }
        return REDIRECT + STATUS_GET_ALL_URL;
    }

}
