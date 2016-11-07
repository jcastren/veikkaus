package fi.joonas.veikkaus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import fi.joonas.veikkaus.TournamentGuiEntity;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.jpaentity.Tournament;

@Service
public class TournamentService {
	
	@Autowired
	TournamentDao tournamentDao;
	
	/*public Long insert(String name, String year) {
		return tournamentDao.save(new Tournament(name, Integer.parseInt(year))).getId();
	}*/
	
	public Long insert(TournamentGuiEntity tournament) {
		return tournamentDao.save(convertGuiToDb(tournament)).getId();
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
	
	/*public boolean delete(Long id) {
		boolean succeed = false;
		tournamentDao.delete(id);
		succeed = true;
		return succeed;
	}*/
	
	public List<TournamentGuiEntity> findAllTournaments() {
		List<TournamentGuiEntity> geList = new ArrayList<>();
		List<Tournament> dbTourns =  ImmutableList.copyOf(tournamentDao.findAll());
		
		for (Tournament dbTourn : dbTourns) {
			geList.add(convertDbToGui(dbTourn));
		}
		
		return geList;
	}
	
	public TournamentGuiEntity findOneTournament(String id) {
		TournamentGuiEntity tournGe = convertDbToGui(tournamentDao.findOne(Long.valueOf(id)));
		return tournGe;
	}
	
	private TournamentGuiEntity convertDbToGui(Tournament db) {
		TournamentGuiEntity ge = new TournamentGuiEntity();
		
		ge.setId(db.getId().toString());
		ge.setName(db.getName());
		ge.setYear(Integer.valueOf(db.getYear()).toString());
		
		return ge;
	}
	
	private Tournament convertGuiToDb(TournamentGuiEntity ge) {
		Tournament db = new Tournament();
		
		db.setId(Long.valueOf(ge.getId()));
		db.setName(ge.getName());
		db.setYear(Integer.valueOf(ge.getYear()));
		
		return db;
	}
	
}
