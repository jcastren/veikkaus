package fi.joonas.veikkaus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;

@Service
public class TournamentPlayerService {
	
	@Autowired
	TournamentPlayerDao tournamentPlayerDao;

	@Autowired
	TournamentTeamDao tournamentTeamDao;
	
	@Autowired
	PlayerDao playerDao;
	
	public Long insert(String tournamentTeamId, String playerId, String goals) throws VeikkausServiceException {
		TournamentTeam tournamentTeam = tournamentTeamDao.findOne(Long.valueOf(tournamentTeamId));
		if (tournamentTeam == null) {
			throw new VeikkausServiceException(
					"Tournament team with id: " + tournamentTeamId + " wasn't found, insert failed");
		}

		Player player = playerDao.findOne(Long.valueOf(playerId));
		if (player == null) {
			throw new VeikkausServiceException("Player with id: " + playerId + " wasn't found, insert failed");
		}

		return tournamentPlayerDao.save(new TournamentPlayer(tournamentTeam, player, Integer.parseInt(goals))).getId();
	}
	
	public Long modify(String id, String tournamentTeamId, String playerId, String goals) throws VeikkausServiceException {
		TournamentPlayer tournamentPlayer = tournamentPlayerDao.findOne(Long.valueOf(id));
		if (tournamentPlayer == null) {
			throw new VeikkausServiceException("tournamentPlayer with id: " + id + " wasn't found, modify failed");
		}
			
		TournamentTeam tournamentTeam = tournamentTeamDao.findOne(Long.valueOf(tournamentTeamId));
		if (tournamentTeam == null) {
			throw new VeikkausServiceException(
					"Tournament team with id: " + tournamentTeamId + " wasn't found, modify failed");
		}

		Player player = playerDao.findOne(Long.valueOf(playerId));
		if (player == null) {
			throw new VeikkausServiceException("Player with id: " + playerId + " wasn't found, modify failed");
		}
		
		tournamentPlayer.setTournamentTeam(tournamentTeam);
		tournamentPlayer.setPlayer(player);
		tournamentPlayer.setGoals(Integer.parseInt(goals));
		return tournamentPlayerDao.save(tournamentPlayer).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		tournamentPlayerDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
}