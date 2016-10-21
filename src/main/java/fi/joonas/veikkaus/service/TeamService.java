package fi.joonas.veikkaus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.jpaentity.Team;

@Service
public class TeamService {
	
	@Autowired
	TeamDao teamDao;
	
	public Long insert(String name) {
		return teamDao.save(new Team(name)).getId();
	}
	
	public Long modify(String id, String name) {
		Team team = teamDao.findOne(Long.valueOf(id)); 
		team.setName(name);
		return teamDao.save(team).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		teamDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
}