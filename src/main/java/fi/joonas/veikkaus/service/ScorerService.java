package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Scorer;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public Long insert(String tournamentPlayerId, String gameId) throws VeikkausServiceException {
		Optional<TournamentPlayer> tournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId));
		if (!tournamentPlayer.isPresent()) {
			throw new VeikkausServiceException("tournamentPlayer with id: " + tournamentPlayerId + " wasn't found, insert failed");
		}

		Optional<Game> game = gameDao.findById(Long.valueOf(gameId));
		if (!game.isPresent()) {
			throw new VeikkausServiceException("game with id: " + gameId + " wasn't found, insert failed");
		}

		return scorerDao.save(new Scorer(tournamentPlayer.get(), game.get())).getId();
	}
	
	/**
	 * 
	 * @param scorerGe
	 * @return
	 */
	public Long insert(ScorerGuiEntity scorerGe) throws VeikkausServiceException {
		String tournamentPlayerId = scorerGe.getTournamentPlayer().getId();
		Optional<TournamentPlayer> tournamentPlayerDb = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId));
		if (!tournamentPlayerDb.isPresent()) {
			throw new VeikkausServiceException(
					"Tournament player with id: " + tournamentPlayerId + " wasn't found, insert failed");
		}

		String gameId = scorerGe.getGame().getId();
		Optional<Game> gameDb = gameDao.findById(Long.valueOf(gameId));
		if (!gameDb.isPresent()) {
			throw new VeikkausServiceException("Game with id: " + gameId + " wasn't found, insert failed");
		} else {
		}

		scorerGe.setTournamentPlayer(TournamentPlayerService.convertDbToGui(tournamentPlayerDb.get()));
		scorerGe.setGame(GameService.convertDbToGui(gameDb.get()));

		return scorerDao.save(convertGuiToDb(scorerGe)).getId();
	}

	
	public Long modify(String id, String tournamentPlayerId, String gameId) throws VeikkausServiceException {
		Optional<TournamentPlayer> tournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId));
		if (!tournamentPlayer.isPresent()) {
			throw new VeikkausServiceException("TournamentPlayer with id: " + tournamentPlayerId + " wasn't found, modify failed");
		}

		Optional<Game> game = gameDao.findById(Long.valueOf(gameId));
		if (!game.isPresent()) {
			throw new VeikkausServiceException("Game with id: " + gameId + " wasn't found, insert failed");
		}

		Optional<Scorer> scorer = scorerDao.findById(Long.valueOf(id));
		if (!scorer.isPresent()) {
			throw new VeikkausServiceException("Scorer with id: " + id + " wasn't found, modify failed");
		}

		scorer.get().setTournamentPlayer(tournamentPlayer.get());
		scorer.get().setGame(game.get());
		return scorerDao.save(scorer.get()).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed;
		scorerDao.deleteById(Long.valueOf(id));
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
