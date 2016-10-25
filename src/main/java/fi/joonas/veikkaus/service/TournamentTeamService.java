package fi.joonas.veikkaus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausDaoException;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;

@Service
public class TournamentTeamService {
	
	@Autowired
	TournamentTeamDao tournamentTeamDao;

	@Autowired
	TournamentDao tournamentDao;
	
	@Autowired
	TeamDao teamDao;
	
	public Long insert(String tournamentId, String teamId) throws VeikkausDaoException {
		Tournament tournament = tournamentDao.findOne(Long.valueOf(tournamentId));
		if (tournament == null) {
			throw new VeikkausDaoException("Tournament with id: " + tournamentId + " wasn't found, insert failed");
		}
		
		Team team = teamDao.findOne(Long.valueOf(teamId));
		if (team == null) {
			throw new VeikkausDaoException("Team with id: " + teamId + " wasn't found, insert failed");
		}
		
		return tournamentTeamDao.save(new TournamentTeam(tournament, team)).getId();
	}
	
	public Long modify(String id, String tournamentId, String teamId) throws VeikkausDaoException {
		TournamentTeam tournamentTeam = tournamentTeamDao.findOne(Long.valueOf(id));
		if (tournamentTeam == null) {
			throw new VeikkausDaoException("TournamentTeam with id: " + id + " wasn't found, modify failed");
		}
			
		Tournament tournament = tournamentDao.findOne(Long.valueOf(tournamentId));
		if (tournament == null) {
			throw new VeikkausDaoException("Tournament with id: " + id + " wasn't found, modify failed");
		}
		
		Team team = teamDao.findOne(Long.valueOf(teamId));
		if (team == null) {
			throw new VeikkausDaoException("Team with id: " + id + " wasn't found, modify failed");
		}
		
		tournamentTeam.setTournament(tournament);
		tournamentTeam.setTeam(team);
		return tournamentTeamDao.save(tournamentTeam).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		tournamentTeamDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
}
