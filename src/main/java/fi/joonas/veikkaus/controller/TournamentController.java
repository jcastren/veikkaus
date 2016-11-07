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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fi.joonas.veikkaus.TournamentGuiEntity;
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
    public String tournamentSubmit(@ModelAttribute TournamentGuiEntity tournament) {
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
	
	@GetMapping("/tournaments")
	public String getTournamentsGET(
			Model model) {
		//model.addAttribute("tournaments", tournamentDao.findAll());
		model.addAttribute("tournaments", tournamentService.findAllTournaments());
		return "tournaments";
	}
	
	@PostMapping("/tournaments")
	public String getTournamentsPOST(
			Model model) {
		//model.addAttribute("tournaments", tournamentDao.findAll());
		model.addAttribute("tournaments", tournamentService.findAllTournaments());
		return "tournaments";
	}

	/**
	 * GET /delete --> Delete the tournament having the passed id.
	 */
	/*
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
	*/
	
	/*
	@RequestMapping(value = URL_DELETE, method = RequestMethod.POST)
    //public String delete(@RequestParam Long id) {
	public String delete(@ModelAttribute(value="tournament") Tournament tournament) {
		
		
        try {
			tournamentService.delete(tournament.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the tournament: ", ex);
			return "Error deleting the tournament:" + ex.toString();
		}
        return "tournaments";
    }
	*/
	
	@PostMapping("/delete")
    public String delete(@ModelAttribute TournamentGuiEntity tournament) {
		try {
			tournamentService.delete(tournament.getId());
		} catch (Exception ex) {
			logger.error("Error deleting the tournament: ", ex);
			return "Error deleting the tournament:" + ex.toString();
		}
        return "redirect:/tournament/tournaments";
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
	
	/*@GetMapping("/tournamentdetails")
    //public String tournamentDetailsForm(Model model) {
	public String tournamentDetailsForm(Model model) {
	
        model.addAttribute("tournament", new Tournament());
        return "tournamentdetails";
    }*/
	
	@RequestMapping("/tournamentdetails")
    public String tournamentDetailForm(@RequestParam(value="id", required=true) String id,
    		//@RequestParam(value="name") String name, @RequestParam(value="year") String year,
    		Model model) {
	//public String tournamentDetailForm(@RequestParam(value="tournament", required=false, defaultValue="World") Tournament tournament, Model model) {
	//public String tournamentDetailForm(@ModelAttribute Tournament tournament, Model model) {
	//public String tournamentDetailForm(@PathVariable(value="id") String id, Model model) {
		
		//Tournament dbTournament = tournamentDao.findOne(Long.valueOf(id));
		TournamentGuiEntity tournament = tournamentService.findOneTournament(id);
		
        //model.addAttribute("id", dbTournament.getId());
        //model.addAttribute("name", dbTournament.getName());
        //model.addAttribute("year", dbTournament.getYear());
        //model.addAttribute("id", tournament.getId());
        //model.addAttribute("name", tournament.getName());
        //model.addAttribute("year", tournament.getYear());
		//model.addAttribute("tournament", tournament);
		//model.addAllAttributes(arg0)
		model.addAttribute("tournament", tournament);
        return "tournamentdetails";
    }
	
    @PostMapping("/tournamentdetails")
    public String tournamentDetailsSubmit(@ModelAttribute Tournament tournament) {
    	/*Long tournamentId = null;
		try {
			tournamentId = tournamentService.insert(tournament);
		} catch (Exception ex) {
			logger.error("Error creating the tournament: ", ex);
			return "Error creating the tournament: " + ex.toString();
		}*/
		logger.debug("Tournament succesfully created with id = " + tournament.getId());
        return "tournamentdetails";
    }
	

    /*@PostMapping("/tournamentdetails")
    public String tournamentDetailsSubmit(@ModelAttribute Tournament tournament) {
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
	*/
	
}
