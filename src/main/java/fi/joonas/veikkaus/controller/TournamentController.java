package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.TOURNAMENT_URL;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_DELETE;
import static fi.joonas.veikkaus.constants.VeikkausConstants.URL_MODIFY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.service.TournamentService;

@Controller
@RequestMapping(TOURNAMENT_URL)
public class TournamentController extends WebMvcConfigurerAdapter {

	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private TournamentDao tournamentDao;
	
	private static final Logger logger = LoggerFactory.getLogger(TournamentController.class);
	
	@GetMapping("/create")
    public String tournamentForm(Model model) {
        model.addAttribute("tournament", new Tournament());
        return "createTournament";
    }

	/**
	 * POST /create --> Create a new tournament and save it in the database.
	 */
    @PostMapping("/create")
    public String tournamentSubmit(@ModelAttribute Tournament tournament) {
    	Long tournamentId = null;
		try {
			tournamentId = tournamentService.insert(tournament);
		} catch (Exception ex) {
			logger.error("Error creating the tournament: ", ex);
			return "Error creating the tournament: " + ex.toString();
		}
		logger.debug("Tournament succesfully created with id = " + tournamentId);
        return "tournamentresult";
    }
	
	@RequestMapping("/tournaments")
	public String getTournaments(
			Model model) {
		model.addAttribute("tournaments", tournamentDao.findAll());
		return "tournaments";
	}

	/**
	 * GET /delete --> Delete the tournament having the passed id.
	 */
	@RequestMapping(URL_DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			tournamentService.delete(id);
		} catch (Exception ex) {
			logger.error("Error deleting the tournament: ", ex);
			return "Error deleting the tournament:" + ex.toString();
		}
		return "Tournament succesfully deleted!";
	}
	
	/**
	 * GET /modify --> Update the name and year for the
	 * tournament in the database having the passed id.
	 */
	@RequestMapping(URL_MODIFY)
	@ResponseBody
	public String updateTournament(String id, String name, String year) {
		try {
			tournamentService.modify(id, name, year);
		} catch (Exception ex) {
			logger.error("Error updating the tournament: ", ex);
			return "Error updating the tournament: " + ex.toString();
		}
		return "Tournament succesfully updated for id = " + id;
	}

}
