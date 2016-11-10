package fi.joonas.veikkaus.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
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
	
	/**
	 * 
	 * @param tournamentPlayerGe
	 * @return
	 */
	public Long insert(TournamentPlayerGuiEntity tournamentPlayerGe) throws VeikkausServiceException {
		String tournamentTeamId = tournamentPlayerGe.getTournamentTeam().getId();
		TournamentTeam tournamentTeamDb = tournamentTeamDao.findOne(Long.valueOf(tournamentTeamId));
		if (tournamentTeamDb == null) {
			throw new VeikkausServiceException("Tournament team with id: " + tournamentTeamId + " wasn't found, insert failed");
		}

		String playerId = tournamentPlayerGe.getPlayer().getId();
		Player playerDb = playerDao.findOne(Long.valueOf(playerId));
		if (playerDb == null) {
			throw new VeikkausServiceException("Player with id: " + playerId + " wasn't found, insert failed");
		} else {
		}

		tournamentPlayerGe.setTournamentTeam(TournamentTeamService.convertDbToGui(tournamentTeamDb));
		tournamentPlayerGe.setPlayer(PlayerService.convertDbToGui(playerDb));

		return tournamentPlayerDao.save(convertGuiToDb(tournamentPlayerGe)).getId();
	}
	
	/**
	 * 
	 * @param tournamentPlayerGe
	 * @return
	 */
	public Long modify(TournamentPlayerGuiEntity tournamentPlayerGe) throws VeikkausServiceException {
		String id = tournamentPlayerGe.getId();
		TournamentPlayer tournamentPlayerDb = tournamentPlayerDao.findOne(Long.valueOf(id));
		if (tournamentPlayerDb == null) {
			throw new VeikkausServiceException("TournamentPlayer with id: " + id + " wasn't found, modify failed");
		}
			
		String tournamentTeamId = tournamentPlayerGe.getTournamentTeam().getId();
		TournamentTeam tournamentTeamDb = tournamentTeamDao.findOne(Long.valueOf(tournamentTeamId));
		if (tournamentTeamDb == null) {
			throw new VeikkausServiceException("Tournament team with id: " + id + " wasn't found, modify failed");
		}
		
		String playerId = tournamentPlayerGe.getPlayer().getId();
		Player playerDb = playerDao.findOne(Long.valueOf(playerId));
		if (playerDb == null) {
			throw new VeikkausServiceException("Player with id: " + id + " wasn't found, modify failed");
		}
		
		tournamentPlayerGe.setTournamentTeam(TournamentTeamService.convertDbToGui(tournamentTeamDb));
		tournamentPlayerGe.setPlayer(PlayerService.convertDbToGui(playerDb));
		
		return tournamentPlayerDao.save(convertGuiToDb(tournamentPlayerGe)).getId();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id) throws VeikkausServiceException {
		boolean succeed = false;
		tournamentPlayerDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TournamentPlayerGuiEntity> findAllTournamentPlayers() {
		List<TournamentPlayerGuiEntity> geList = new ArrayList<>();
		List<TournamentPlayer> dbTournPlayers =  ImmutableList.copyOf(tournamentPlayerDao.findAll());
		
		for (TournamentPlayer dbTournPlayer : dbTournPlayers) {
			geList.add(convertDbToGui(dbTournPlayer));
		}
		
		return geList;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TournamentPlayerGuiEntity findOneTournamentPlayer(String id) {
		TournamentPlayerGuiEntity tournPlayerGe = convertDbToGui(tournamentPlayerDao.findOne(Long.valueOf(id)));
		return tournPlayerGe;
	}
	
	protected static TournamentPlayerGuiEntity convertDbToGui(TournamentPlayer db) {
		TournamentPlayerGuiEntity ge = new TournamentPlayerGuiEntity();
		
		ge.setId(db.getId().toString());
		ge.setTournamentTeam(TournamentTeamService.convertDbToGui(db.getTournamentTeam()));
		ge.setPlayer(PlayerService.convertDbToGui(db.getPlayer()));
		ge.setGoals(Integer.valueOf(db.getGoals()).toString());
		return ge;
	}
	
	protected static TournamentPlayer convertGuiToDb(TournamentPlayerGuiEntity ge) {
		TournamentPlayer db = new TournamentPlayer();
		
		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setTournamentTeam(TournamentTeamService.convertGuiToDb(ge.getTournamentTeam()));
		db.setPlayer(PlayerService.convertGuiToDb(ge.getPlayer()));
		db.setGoals(Integer.valueOf(ge.getGoals()));
		
		return db;
	}

	
}