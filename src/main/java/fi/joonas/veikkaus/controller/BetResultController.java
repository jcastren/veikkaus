package fi.joonas.veikkaus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.jpaentity.BetResult;
import fi.joonas.veikkaus.jpaentity.User;

@Controller
@RequestMapping("/betresult")
public class BetResultController {

	@Autowired
	private BetResultDao betResultDao;
	
	private static final Logger logger = LoggerFactory.getLogger(BetResultController.class);

	/**
	 * GET /create --> Create a new bet result and save it in the database.
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String create(String email, String name, String password) {
		String userId = "";
		try {
			BetResult betResult = new BetResult();
			betResultDao.save(betResult);
			userId = String.valueOf(betResult.getId());
		} catch (Exception ex) {
			logger.error("Error creating the user: ", ex);
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created with id = " + userId;
	}

	/**
	 * GET /delete --> Delete the user having the passed id.
	 */
	
	/*
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			User user = new User(id);
			betResultDao.delete(user);
		} catch (Exception ex) {
			logger.error("Error deleting the user: ", ex);
			return "Error deleting the user:" + ex.toString();
		}
		return "User succesfully deleted!";
	}
	*/

}
