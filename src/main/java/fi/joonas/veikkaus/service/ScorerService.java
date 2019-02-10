package fi.joonas.veikkaus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.Scorer;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;

@Service
public class ScorerService {
	
	@Autowired
	ScorerDao scorerDao;
	
	@Autowired
	TournamentPlayerDao tournamentPlayerDao;
	
	@Autowired
	GameDao gameDao;
	
	public List<ScorerGuiEntity> findAllScorers() {
		List<ScorerGuiEntity> geList = new ArrayList<>();
		List<Scorer> dbScorers =  ImmutableList.copyOf(scorerDao.findAll());
		dbScorers.forEach(dbScorer -> geList.add(convertDbToGui(dbScorer)));
		return geList;
	}
	
//	public Long insert(String tournamentPlayerId, String gameId) throws VeikkausServiceException {
//		TournamentPlayer tournamentPlayer = tournamentPlayerDao.findOne(Long.valueOf(tournamentPlayerId));
//		if (tournamentPlayer == null) {
//			throw new VeikkausServiceException("tournamentPlayer with id: " + tournamentPlayerId + " wasn't found, insert failed");
//		}
//		
//		Game game = gameDao.findOne(Long.valueOf(gameId));
//		if (game == null) {
//			throw new VeikkausServiceException("game with id: " + gameId + " wasn't found, insert failed");
//		}
//
//		return scorerDao.save(new Scorer(tournamentPlayer, game)).getId();
//	}
	
	/**
	 * 
	 * @param tournamentPlayerGe
	 * @return
	 */
	public Long insert(ScorerGuiEntity scorerGe) throws VeikkausServiceException {
		String tournamentPlayerId = scorerGe.getTournamentPlayer().getId();
		TournamentPlayer tournamentPlayerDb = tournamentPlayerDao.findOne(Long.valueOf(tournamentPlayerId));
		if (tournamentPlayerDb == null) {
			throw new VeikkausServiceException(
					"Tournament player with id: " + tournamentPlayerId + " wasn't found, insert failed");
		}

		String gameId = scorerGe.getGame().getId();
		Game gameDb = gameDao.findOne(Long.valueOf(gameId));
		if (gameDb == null) {
			throw new VeikkausServiceException("Game with id: " + gameId + " wasn't found, insert failed");
		} else {
		}

		scorerGe.setTournamentPlayer(TournamentPlayerService.convertDbToGui(tournamentPlayerDb));
		scorerGe.setGame(GameService.convertDbToGui(gameDb));

		return scorerDao.save(convertGuiToDb(scorerGe)).getId();
	}

	
	public Long modify(String id, String tournamentPlayerId, String gameId) throws VeikkausServiceException {
		TournamentPlayer tournamentPlayer = tournamentPlayerDao.findOne(Long.valueOf(tournamentPlayerId));
		if (tournamentPlayer == null) {
			throw new VeikkausServiceException("TournamentPlayer with id: " + tournamentPlayerId + " wasn't found, modify failed");
		}
		
		Game game = gameDao.findOne(Long.valueOf(gameId));
		if (game == null) {
			throw new VeikkausServiceException("Game with id: " + gameId + " wasn't found, insert failed");
		}
		
		Scorer scorer = scorerDao.findOne(Long.valueOf(id));		
		if (scorer == null) {
			throw new VeikkausServiceException("Scorer with id: " + id + " wasn't found, modify failed");
		}

		scorer.setTournamentPlayer(tournamentPlayer);
		scorer.setGame(game);
		return scorerDao.save(scorer).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		scorerDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
	protected static ScorerGuiEntity convertDbToGui(Scorer db) {
		ScorerGuiEntity ge = new ScorerGuiEntity();
		ge.setId(db.getId().toString());
		ge.setTournamentPlayer(TournamentPlayerService.convertDbToGui(db.getTournamentPlayer()));
		ge.setGame(GameService.convertDbToGui(db.getGame()));
		return ge;
	}
	
	protected static Scorer convertGuiToDb(ScorerGuiEntity ge) {
		Scorer db = new Scorer();

		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setTournamentPlayer(TournamentPlayerService.convertGuiToDb(ge.getTournamentPlayer()));
		try {
			db.setGame(GameService.convertGuiToDb(ge.getGame()));
		} catch (VeikkausConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return db;
	}


}
