package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.jpaentity.Tournament;

@Service
public class TournamentService {
	
	@Autowired
	TournamentDao tournamentDao;
	
	public Long insert(String name, String year) {
		return tournamentDao.save(new Tournament(name, Integer.parseInt(year))).getId();
	}
	
	public Long insert(Tournament tournament) {
		return tournamentDao.save(tournament).getId();
	}
	
	public Long modify(String id, String name, String year) {
		Tournament tournament = tournamentDao.findOne(Long.valueOf(id)); 
		tournament.setName(name);
		tournament.setYear(Integer.parseInt(year));
		return tournamentDao.save(tournament).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		tournamentDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
}
