package fi.joonas.veikkaus.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausDaoException;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;

@Service
public class GameService {
	
	@Autowired
	GameDao gameDao;

	@Autowired
	TournamentTeamDao tournamentTeamDao;
	
	public Long insert(String homeTeamId, String awayTeamId, String homeScore, String awayScore, String gameDate)
			throws VeikkausDaoException {
		TournamentTeam homeTeam = tournamentTeamDao.findOne(Long.valueOf(homeTeamId));
		if (homeTeam == null) {
			throw new VeikkausDaoException(
					String.format("TournamentTeam (homeTeam) with id: " + homeTeamId + " wasn't found, insert failed"));
		}

		TournamentTeam awayTeam = tournamentTeamDao.findOne(Long.valueOf(awayTeamId));
		if (awayTeam == null) {
			throw new VeikkausDaoException(
					String.format("TournamentTeam (awayTeam) with id: " + awayTeamId + " wasn't found, insert failed"));
		}

		return gameDao.save(new Game(homeTeam, awayTeam, Integer.parseInt(homeScore), Integer.parseInt(awayScore),
				Date.valueOf(gameDate))).getId();
	}
	
	public Long modify(String id, String homeTeamId, String awayTeamId, String homeScore, String awayScore,
			String gameDate) throws VeikkausDaoException {
		Game game = gameDao.findOne(Long.valueOf(id));
		if (game == null) {
			throw new VeikkausDaoException(String.format("game with id: %s wasn't found, %s failed", id, "modify"));
		}
		
		TournamentTeam homeTeam = tournamentTeamDao.findOne(Long.valueOf(homeTeamId));
		if (homeTeam == null) {
			throw new VeikkausDaoException(String.format("TournamentTeam (homeTeam) with id: %s wasn't found, %s failed", homeTeamId, "modify"));
		}
		
		TournamentTeam awayTeam = tournamentTeamDao.findOne(Long.valueOf(awayTeamId));
		if (awayTeam == null) {
			throw new VeikkausDaoException(String.format("TournamentTeam (awayTeam) with id: %s wasn't found, %s failed", awayTeamId, "modify"));
		}
		
		game.setHomeTeam(homeTeam);
		game.setAwayTeam(awayTeam);
		game.setHomeScore(Integer.parseInt(homeScore));
		game.setAwayScore(Integer.parseInt(awayScore));
		game.setGameDate(Date.valueOf(gameDate));
		
		return gameDao.save(game).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		gameDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
}
