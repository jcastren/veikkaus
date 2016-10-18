package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.jpaentity.Player;

@Service
public class PlayerService {
	
	@Autowired
	PlayerDao playerDao;
	
	public Long insert(String firstName, String lastName) {
		Player player = new Player(null, firstName, lastName);
		Player playerDb = playerDao.save(player);
		return playerDb.getId();
	}
	
	public Long modify(String id, String firstName, String lastName) {
		Player player = new Player(Long.valueOf(id), firstName, lastName);
		Player playerDb = playerDao.save(player);
		return playerDb.getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		playerDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	

}
