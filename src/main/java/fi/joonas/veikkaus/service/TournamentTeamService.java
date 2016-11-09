package fi.joonas.veikkaus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;

/**
 * Business logic level class for DB handling of Tournament
 * @author joona
 *
 */
@Service
public class TournamentTeamService {
	
	@Autowired
	TournamentTeamDao tournamentTeamDao;

	@Autowired
	TournamentDao tournamentDao;
	
	@Autowired
	TeamDao teamDao;

	/**
	 * 
	 * @param tournamentTeamGe
	 * @return
	 */
	public Long insert(TournamentTeamGuiEntity tournamentTeamGe) throws VeikkausServiceException {
		String tournamentId = tournamentTeamGe.getTournament().getId();
		Tournament tournamentDb = tournamentDao.findOne(Long.valueOf(tournamentId));
		if (tournamentDb == null) {
			throw new VeikkausServiceException("Tournament with id: " + tournamentId + " wasn't found, insert failed");
		}

		String teamId = tournamentTeamGe.getTeam().getId();
		Team teamDb = teamDao.findOne(Long.valueOf(teamId));
		if (teamDb == null) {
			throw new VeikkausServiceException("Team with id: " + teamId + " wasn't found, insert failed");
		} else {
		}

		tournamentTeamGe.setTournament(TournamentService.convertDbToGui(tournamentDb));
		tournamentTeamGe.setTeam(TeamService.convertDbToGui(teamDb));

		return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGe)).getId();
	}
	
	/**
	 * 
	 * @param tournamentTeamGe
	 * @return
	 */
	public Long modify(TournamentTeamGuiEntity tournamentTeamGe) throws VeikkausServiceException {
		String id = tournamentTeamGe.getId();
		TournamentTeam tournamentTeamDb = tournamentTeamDao.findOne(Long.valueOf(id));
		if (tournamentTeamDb == null) {
			throw new VeikkausServiceException("TournamentTeam with id: " + id + " wasn't found, modify failed");
		}
			
		String tournamentId = tournamentTeamGe.getTournament().getId();
		Tournament tournamentDb = tournamentDao.findOne(Long.valueOf(tournamentId));
		if (tournamentDb == null) {
			throw new VeikkausServiceException("Tournament with id: " + id + " wasn't found, modify failed");
		}
		
		String teamId = tournamentTeamGe.getTeam().getId();
		Team teamDb = teamDao.findOne(Long.valueOf(teamId));
		if (teamDb == null) {
			throw new VeikkausServiceException("Team with id: " + id + " wasn't found, modify failed");
		}
		
		tournamentTeamGe.setTournament(TournamentService.convertDbToGui(tournamentDb));
		tournamentTeamGe.setTeam(TeamService.convertDbToGui(teamDb));
		
		return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGe)).getId();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id) throws VeikkausServiceException {
		boolean succeed = false;
		tournamentTeamDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TournamentTeamGuiEntity> findAllTournamentTeams() {
		List<TournamentTeamGuiEntity> geList = new ArrayList<>();
		List<TournamentTeam> dbTournTeams =  ImmutableList.copyOf(tournamentTeamDao.findAll());
		
		for (TournamentTeam dbTournTeam : dbTournTeams) {
			geList.add(convertDbToGui(dbTournTeam));
		}
		
		return geList;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TournamentTeamGuiEntity findOneTournamentTeam(String id) {
		TournamentTeamGuiEntity tournGe = convertDbToGui(tournamentTeamDao.findOne(Long.valueOf(id)));
		return tournGe;
	}
	
	protected static TournamentTeamGuiEntity convertDbToGui(TournamentTeam db) {
		TournamentTeamGuiEntity ge = new TournamentTeamGuiEntity();
		
		ge.setId(db.getId().toString());
		ge.setTournament(TournamentService.convertDbToGui(db.getTournament()));
		ge.setTeam(TeamService.convertDbToGui(db.getTeam()));
		return ge;
	}
	
	protected static TournamentTeam convertGuiToDb(TournamentTeamGuiEntity ge) {
		TournamentTeam db = new TournamentTeam();
		
		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setTournament(TournamentService.convertGuiToDb(ge.getTournament()));
		db.setTeam(TeamService.convertGuiToDb(ge.getTeam()));
		
		return db;
	}
	
}