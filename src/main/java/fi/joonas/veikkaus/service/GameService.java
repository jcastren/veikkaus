package fi.joonas.veikkaus.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.VeikkausUtil;

@Service
public class GameService {

	@Autowired
	GameDao gameDao;

	@Autowired
	TournamentTeamDao tournamentTeamDao;

	public Long insert(String homeTeamId, String awayTeamId, String homeScore, String awayScore, String gameDate)
			throws VeikkausServiceException {

		Long retGameId = null;

		TournamentTeam homeTeam = tournamentTeamDao.findOne(Long.valueOf(homeTeamId));
		if (homeTeam == null) {
			throw new VeikkausServiceException(
					String.format("TournamentTeam (homeTeam) with id: " + homeTeamId + " wasn't found, insert failed"));
		}

		TournamentTeam awayTeam = tournamentTeamDao.findOne(Long.valueOf(awayTeamId));
		if (awayTeam == null) {
			throw new VeikkausServiceException(
					String.format("TournamentTeam (awayTeam) with id: " + awayTeamId + " wasn't found, insert failed"));
		}

		try {
			retGameId = gameDao.save(new Game(homeTeam, awayTeam, Integer.parseInt(homeScore),
					Integer.parseInt(awayScore), VeikkausUtil.getStringAsDate(gameDate))).getId();
		} catch (NumberFormatException | ParseException e) {
			throw new VeikkausServiceException(String.format(
					"Saving game failed. One of parameters: homeScore: %s, awayScore: %s, gameDate: %s had invalid format",
					homeScore, awayScore, gameDate));
		}

		return retGameId;
	}

	public Long modify(String id, String homeTeamId, String awayTeamId, String homeScore, String awayScore,
			String gameDate) throws VeikkausServiceException {
		Game game = gameDao.findOne(Long.valueOf(id));
		if (game == null) {
			throw new VeikkausServiceException(String.format("game with id: %s wasn't found, %s failed", id, "modify"));
		}

		TournamentTeam homeTeam = tournamentTeamDao.findOne(Long.valueOf(homeTeamId));
		if (homeTeam == null) {
			throw new VeikkausServiceException(String
					.format("TournamentTeam (homeTeam) with id: %s wasn't found, %s failed", homeTeamId, "modify"));
		}

		TournamentTeam awayTeam = tournamentTeamDao.findOne(Long.valueOf(awayTeamId));
		if (awayTeam == null) {
			throw new VeikkausServiceException(String
					.format("TournamentTeam (awayTeam) with id: %s wasn't found, %s failed", awayTeamId, "modify"));
		}

		game.setHomeTeam(homeTeam);
		game.setAwayTeam(awayTeam);
		game.setHomeScore(Integer.parseInt(homeScore));
		game.setAwayScore(Integer.parseInt(awayScore));
		try {
			game.setGameDate(VeikkausUtil.getStringAsDate(gameDate));
		} catch (ParseException e) {
			throw new VeikkausServiceException(
					String.format("Saving game failed. Parameter gameDate: %s had invalid format", gameDate));
		}

		return gameDao.save(game).getId();
	}

	public boolean delete(String id) {
		boolean succeed = false;
		gameDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}

}
