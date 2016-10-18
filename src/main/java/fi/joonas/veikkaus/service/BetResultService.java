package fi.joonas.veikkaus.service;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.User;

@Service
public class BetResultService {
	
	/*
	@Autowired
	UserDao userDao;
	
	@Autowired
	GameDao gameDao;
	
	@Autowired
	BetResultDao betResultDao;
	
	public String save(String userId, String gameId, String homeScore, String awayScore) {
		String retVal = null;
		
		User userDb = userDao.findOne(Long.valueOf(userId));
		
		return retVal;
	}
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private User user;

    @ManyToOne
    private Game game;

    private int homeScore;
	private int awayScore;*/

}
