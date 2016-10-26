package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Scorer;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;

@Service
public class ScorerService {
	
	@Autowired
	ScorerDao scorerDao;
	
	@Autowired
	TournamentPlayerDao tournamentPlayerDao;
	
	@Autowired
	GameDao gameDao;
	
	public Long insert(String tournamentPlayerId, String gameId) throws VeikkausServiceException {
		TournamentPlayer tournamentPlayer = tournamentPlayerDao.findOne(Long.valueOf(tournamentPlayerId));
		if (tournamentPlayer == null) {
			throw new VeikkausServiceException("tournamentPlayer with id: " + tournamentPlayerId + " wasn't found, insert failed");
		}
		
		Game game = gameDao.findOne(Long.valueOf(gameId));
		if (game == null) {
			throw new VeikkausServiceException("game with id: " + gameId + " wasn't found, insert failed");
		}

		return scorerDao.save(new Scorer(tournamentPlayer, game)).getId();
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
	
}
