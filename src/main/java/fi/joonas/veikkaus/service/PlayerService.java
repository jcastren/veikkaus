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
		return playerDao.save(new Player(firstName, lastName)).getId();
	}
	
	public Long modify(String id, String firstName, String lastName) {
		Player player = playerDao.findOne(Long.valueOf(id));
		player.setFirstName(firstName);
		player.setLastName(lastName);
		return playerDao.save(player).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		playerDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}

}
