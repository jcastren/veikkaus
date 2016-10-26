package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.jpaentity.User;

@Service
public class BetService {
	
	@Autowired
	BetDao betDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	StatusDao statusDao;

	public Long insert(String userId, String statusId) throws VeikkausServiceException {
		User user = userDao.findOne(Long.valueOf(userId));

		if (user == null) {
			throw new VeikkausServiceException("user with id: " + userId + " wasn't found, insert failed");
		}
		
		Status status = statusDao.findOne(Long.valueOf(statusId));

		if (status == null) {
			throw new VeikkausServiceException("status with id: " + statusId + " wasn't found, insert failed");
		}
		
		return betDao.save(new Bet(user, status)).getId();
	}
	
	public Long modify(String id, String statusId, String userId) throws VeikkausServiceException {
		Bet bet = betDao.findOne(Long.valueOf(id));
		if (bet == null) {
			throw new VeikkausServiceException("bet with id: " + id + " wasn't found, modify failed");
		}
			
		User user = userDao.findOne(Long.valueOf(id));
		if (user == null) {
			throw new VeikkausServiceException("user with id: " + id + " wasn't found, modify failed");
		}
		
		Status status = statusDao.findOne(Long.valueOf(id));
		if (status == null) {
			throw new VeikkausServiceException("status with id: " + id + " wasn't found, modify failed");
		}
		
		bet.setUser(user);
		bet.setStatus(status);
		return betDao.save(bet).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		betDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}
	
}
