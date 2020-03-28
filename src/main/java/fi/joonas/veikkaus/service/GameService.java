package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.VeikkausUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static fi.joonas.veikkaus.constants.VeikkausConstants.INT_NOT_DEFINED;

@Service
public class GameService {

	@Autowired
	GameDao gameDao;

	@Autowired
	TournamentTeamDao tournamentTeamDao;
	
	private static final Logger logger = LoggerFactory.getLogger(GameService.class);

	public Long insert(GameGuiEntity gameGe) throws VeikkausServiceException {
		Long retGameId = null;
		String homeTeamId = gameGe.getHomeTeam().getId();
		TournamentTeam homeTeamDb = tournamentTeamDao.findOne(Long.valueOf(homeTeamId));
		if (homeTeamDb == null) {
			throw new VeikkausServiceException(
				String.format("TournamentTeam (homeTeam) with id: %s wasn't found, insert failed", homeTeamId));
		}

		String awayTeamId = gameGe.getAwayTeam().getId();
		TournamentTeam awayTeamDb = tournamentTeamDao.findOne(Long.valueOf(awayTeamId));
		if (awayTeamDb == null) {
			throw new VeikkausServiceException(
				String.format("TournamentTeam (awayTeam) with id: %s wasn't found, insert failed", awayTeamId));
		}

		/** TODO Why converting back ??? */
		gameGe.setHomeTeam(TournamentTeamService.convertDbToGui(homeTeamDb));
		gameGe.setAwayTeam(TournamentTeamService.convertDbToGui(awayTeamDb));

		try {
			retGameId = gameDao.save(convertGuiToDb(gameGe)).getId();
		} catch (VeikkausConversionException vce) {
			throw new VeikkausServiceException("Saving game failed.", vce);
		}
		return retGameId;
	}
	
	/**
	 * 
	 * @param gameGe
	 * @return
	 */
	public Long modify(GameGuiEntity gameGe) throws VeikkausServiceException {
		Long retGameId = null;
		
		String id = gameGe.getId();
		Game gameDb = gameDao.findOne(Long.valueOf(id));
		if (gameDb == null) {
			throw new VeikkausServiceException("Game with id: " + id + " wasn't found, modify failed");
		}
			
		String homeTeamId = gameGe.getHomeTeam().getId();
		TournamentTeam homeTeamDb = tournamentTeamDao.findOne(Long.valueOf(homeTeamId));
		if (homeTeamDb == null) {
			throw new VeikkausServiceException("Home team with id: " + homeTeamId + " wasn't found, modify failed");
		}
		
		String awayTeamId = gameGe.getAwayTeam().getId();
		TournamentTeam awayTeamDb = tournamentTeamDao.findOne(Long.valueOf(awayTeamId));
		if (awayTeamDb == null) {
			throw new VeikkausServiceException("Away team with id: " + awayTeamId + " wasn't found, modify failed");
		}
		
		gameGe.setHomeTeam(TournamentTeamService.convertDbToGui(homeTeamDb));
		gameGe.setAwayTeam(TournamentTeamService.convertDbToGui(awayTeamDb));
		
		try {
			retGameId = gameDao.save(convertGuiToDb(gameGe)).getId();
		} catch (VeikkausConversionException vce) {
			throw new VeikkausServiceException("Saving game failed.", vce);
		}
		return retGameId;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id) throws VeikkausServiceException {
		boolean succeed = false;
		gameDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<GameGuiEntity> findAllGames() {
		List<GameGuiEntity> geList = new ArrayList<>();
		List<Game> dbGames =  ImmutableList.copyOf(gameDao.findAll());
		
		for (Game dbGame : dbGames) {
			geList.add(convertDbToGui(dbGame));
		}
		
		return geList;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public GameGuiEntity findOneGame(String id) {
		GameGuiEntity gameGe = convertDbToGui(gameDao.findOne(Long.valueOf(id)));
		return gameGe;
	}
	
	protected static GameGuiEntity convertDbToGui(Game db) {
		GameGuiEntity ge = new GameGuiEntity();
		
		ge.setId(db.getId().toString());
		ge.setHomeScore(Integer.valueOf(db.getHomeScore()).toString());
		ge.setAwayScore(Integer.valueOf(db.getAwayScore()).toString());
		ge.setGameDate(VeikkausUtil.getDateAsString(db.getGameDate()));
		ge.setHomeTeam(TournamentTeamService.convertDbToGui(db.getHomeTeam()));
		ge.setAwayTeam(TournamentTeamService.convertDbToGui(db.getAwayTeam()));
		return ge;
	}
	
	protected static Game convertGuiToDb(GameGuiEntity ge) throws VeikkausConversionException {
		Game db = new Game();
		
		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		
		try {
			db.setHomeScore(Integer.parseInt(ge.getHomeScore()));
		} catch (NumberFormatException nfe) {
			logger.info(String.format("Error while parsing integer '%s' Using integer %d instead", ge.getHomeScore(), INT_NOT_DEFINED), nfe);
			db.setHomeScore(INT_NOT_DEFINED);
		}
		try {
			db.setAwayScore(Integer.parseInt(ge.getAwayScore()));
		} catch (NumberFormatException nfe) {
			logger.info(String.format("Error while parsing integer '%s' Using integer %d instead", ge.getAwayScore(), INT_NOT_DEFINED), nfe);
			db.setAwayScore(INT_NOT_DEFINED);
		}
		try {
			db.setGameDate(VeikkausUtil.getStringAsDate(ge.getGameDate()));			
		} catch (ParseException pe) {
			logger.error("Error while parsing date: " + ge.getGameDate(), pe);
			throw new VeikkausConversionException("Error while parsing date: " + ge.getGameDate(), pe);
		}
		db.setHomeTeam(TournamentTeamService.convertGuiToDb(ge.getHomeTeam()));
		db.setAwayTeam(TournamentTeamService.convertGuiToDb(ge.getAwayTeam()));
		
		return db;
	}

}
