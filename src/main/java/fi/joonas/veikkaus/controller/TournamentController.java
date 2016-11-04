package fi.joonas.veikkaus.controller;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fi.joonas.veikkaus.Greeting;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.guientity.TournamentGUI;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.service.TournamentService;

@Controller
@RequestMapping(TOURNAMENT_URL)
public class TournamentController extends WebMvcConfigurerAdapter {

	    @GetMapping("/createPost")
	    public String tournamentForm(Model model) {
	        model.addAttribute("tournamentGUI", new TournamentGUI());
	        return "tournament";
	    }

	    @PostMapping("/createPost")
	    public String tournamentSubmit(@ModelAttribute TournamentGUI tournamentGUI) {
	    	Long tournamentId = null;
			try {
				tournamentId = tournamentService.insert(tournamentGUI);
			} catch (Exception ex) {
				logger.error("Error creating the tournament: ", ex);
				return "Error creating the tournament: " + ex.toString();
			}
			logger.debug("Tournament succesfully created with id = " + tournamentId);
	        return "tournamentresult";
	    }
	    
	    /*
		// Create tournament
	    @RequestMapping(value="/createPost", method = RequestMethod.POST)
	    //public String createTournament (@RequestParam String name, @RequestParam String year, Model model) {
	    public String createTournament(@ModelAttribute(value="tournament") Tournament tournament, Model model) {
	        Long tournamentId = null;
			try {
				tournamentId = tournamentService.insert(tournament);
			} catch (Exception ex) {
				logger.error("Error creating the tournament: ", ex);
				return "Error creating the tournament: " + ex.toString();
			}
			logger.debug("Tournament succesfully created with id = " + tournamentId);
	        
	        return "redirect:/veikkaus/tournament/tournaments";
	    }
	    */
	    
	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private TournamentDao tournamentDao;
	
	private static final Logger logger = LoggerFactory.getLogger(TournamentController.class);
	
	@RequestMapping("/tournaments")
	public String getTournaments(
			//@RequestParam(value = "tournament", required = false, defaultValue = "Gothia Cup") String tournament,
			Model model) {
		
		/*
		Tournament tourn = tournamentDao.findOne(Long.valueOf(1));		
		String tournament = tourn == null ? "Tournament not found" : tourn.getName();
		model.addAttribute("tournament", tournament);
		*/
		
		/*List<Tournament> tournList = new ArrayList<Tournament>();
		tournamentDao.findAll();
		*/
		
		model.addAttribute("tournaments", tournamentDao.findAll());
		
		
		/*
		Tournament tourn1 = new Tournament("Italy", 1990);
		Tournament tourn2 = new Tournament("USA", 1994);
		
		List<Tournament> tournamentList = new ArrayList<Tournament>();
		tournamentList.add(tourn1);
		tournamentList.add(tourn2);
		
		model.addAttribute("tournaments", tournamentList);
		*/
		
		return "tournaments";
	}

	/**
	 * GET /create --> Create a new tournament and save it in the database.
	 */
	@RequestMapping(URL_CREATE)
	//@ResponseBody
	public String create(@RequestParam String name, @RequestParam String year) {
		Long tournamentId = null;
		try {
			tournamentId = tournamentService.insert(name, year);
		} catch (Exception ex) {
			logger.error("Error creating the tournament: ", ex);
			return "Error creating the tournament: " + ex.toString();
		}
		logger.debug("Tournament succesfully created with id = " + tournamentId);
		//return "Tournament succesfully created with id = " + tournamentId;
		
		return "redirect:/tournament/tournaments";
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
