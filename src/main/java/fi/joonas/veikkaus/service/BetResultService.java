package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.guientity.BetResultGuiEntity;
import fi.joonas.veikkaus.jpaentity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BetResultService {
	
	@Autowired
	BetResultDao betResultDao;
	
	@Autowired
	BetDao betDao;
	
	@Autowired
	GameDao gameDao;

	public Long insert(BetResultGuiEntity betResultGe) throws VeikkausServiceException {
		String betId = betResultGe.getBet().getId();
		Bet betDb = betDao.findOne(Long.valueOf(betId));
		if (betDb == null) {
			throw new VeikkausServiceException("Bet with id: " + betId + " wasn't found, insert failed");
		}

		String gameId = betResultGe.getGame().getId();
		Game gameDb = gameDao.findOne(Long.valueOf(gameId));
		if (gameDb == null) {
			throw new VeikkausServiceException("Game with id: " + gameId + " wasn't found, insert failed");
		}

		betResultGe.setBet(BetService.convertDbToGui(betDb));
		betResultGe.setGame(GameService.convertDbToGui(gameDb));

		return betResultDao.save(convertGuiToDb(betResultGe)).getId();
	}

	public Long modify(BetResultGuiEntity betResultGe) throws VeikkausServiceException {
		String id = betResultGe.getId();
		BetResult betResultDb = betResultDao.findOne(Long.valueOf(id));
		if (betResultDb == null) {
			throw new VeikkausServiceException("Bet result with id: " + id + " wasn't found, modify failed");
		}

		String betId = betResultGe.getBet().getId();
		Bet betDb = betDao.findOne(Long.valueOf(betId));
		if (betDb == null) {
			throw new VeikkausServiceException("Bet with id: " + id + " wasn't found, modify failed");
		}

		String gameId = betResultGe.getGame().getId();
		Game gameDb = gameDao.findOne(Long.valueOf(gameId));
		if (gameDb == null) {
			throw new VeikkausServiceException("Game with id: " + id + " wasn't found, modify failed");
		}

		betResultGe.setBet(BetService.convertDbToGui(betDb));
		betResultGe.setGame(GameService.convertDbToGui(gameDb));

		return betResultDao.save(convertGuiToDb(betResultGe)).getId();
	}

	public boolean delete(String id) {
		boolean succeed = false;
		betResultDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}

	public List<BetResultGuiEntity> findAllBetResults() {
		List<BetResultGuiEntity> geList = new ArrayList<>();
		List<BetResult> dbBetResults = ImmutableList.copyOf(betResultDao.findAll());

		for (BetResult dbBetResult : dbBetResults) {
			geList.add(convertDbToGui(dbBetResult));
		}
		return geList;
	}

	public List<BetResultGuiEntity> findBetBetResults(String betId) {
		Bet dbBet = betDao.findOne(Long.valueOf(betId));

		List<BetResultGuiEntity> geList = new ArrayList<>();
		List<BetResult> dbBetResults = ImmutableList.copyOf(betResultDao.findByBet(dbBet));

		for (BetResult dbBetResult : dbBetResults) {
			geList.add(convertDbToGui(dbBetResult));
		}
		return geList;
	}

	public BetResultGuiEntity findOneBetResult(String id) {
		BetResultGuiEntity betResultGe = convertDbToGui(betResultDao.findOne(Long.valueOf(id)));
		return betResultGe;
	}

	protected static BetResultGuiEntity convertDbToGui(BetResult db) {
		BetResultGuiEntity ge = new BetResultGuiEntity();

		ge.setId(db.getId().toString());
		ge.setBet(BetService.convertDbToGui(db.getBet()));
		ge.setGame(GameService.convertDbToGui(db.getGame()));
		ge.setHomeScore(db.getHomeScore());
		ge.setAwayScore(db.getAwayScore());
		return ge;
	}

	protected static BetResult convertGuiToDb(BetResultGuiEntity ge) {
		BetResult db = new BetResult();

		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setBet(BetService.convertGuiToDb(ge.getBet()));
		try {
			db.setGame(GameService.convertGuiToDb(ge.getGame()));
		} catch (VeikkausConversionException e) {
			e.printStackTrace();
		}
		db.setHomeScore(ge.getHomeScore());
		db.setAwayScore(ge.getAwayScore());

		return db;
	}
	
}
