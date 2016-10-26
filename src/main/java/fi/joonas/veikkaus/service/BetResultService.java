package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.BetResult;
import fi.joonas.veikkaus.jpaentity.Game;

@Service
public class BetResultService {
	
	@Autowired
	BetResultDao betResultDao;
	
	@Autowired
	BetDao betDao;
	
	@Autowired
	GameDao gameDao;
	
	public Long insert(String betId, String gameId, String homeScore, String awayScore) throws VeikkausServiceException {
		
		Bet bet = betDao.findOne(Long.valueOf(betId));
		if (bet == null) {
			throw new VeikkausServiceException("bet with id: " + betId + " wasn't found, insert failed");
		}
		
		Game game = gameDao.findOne(Long.valueOf(gameId));
		if (game == null) {
			throw new VeikkausServiceException("game with id: " + gameId + " wasn't found, insert failed");
		}

		return betResultDao.save(new BetResult(bet, game, Integer.valueOf(homeScore), Integer.valueOf(awayScore))).getId();
	}
	
	public Long modify(String id, String homeScore, String awayScore) throws VeikkausServiceException {
		BetResult betResult = betResultDao.findOne(Long.valueOf(id));
		
		if (betResult == null) {
			throw new VeikkausServiceException("betResult with id: " + id + " wasn't found, modify failed");
		}

		betResult.setHomeScore(Integer.valueOf(homeScore));
		betResult.setAwayScore(Integer.valueOf(awayScore));
		return betResultDao.save(betResult).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		betResultDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
}
