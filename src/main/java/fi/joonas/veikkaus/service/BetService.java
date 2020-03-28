package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.guientity.BetGuiEntity;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BetService {
	
	@Autowired
	BetDao betDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	StatusDao statusDao;

	public Long insert(BetGuiEntity betGe) throws VeikkausServiceException {
//	public Long insert(String userId, String statusId) throws VeikkausServiceException {
//		User user = userDao.findOne(Long.valueOf(userId));
//
//		if (user == null) {
//			throw new VeikkausServiceException("user with id: " + userId + " wasn't found, insert failed");
//		}
//
//		Status status = statusDao.findOne(Long.valueOf(statusId));
//
//		if (status == null) {
//			throw new VeikkausServiceException("status with id: " + statusId + " wasn't found, insert failed");
//		}
//
//		return betDao.save(new Bet(user, status)).getId();

		String userId = betGe.getUser().getId();
		User userDb = userDao.findOne(Long.valueOf(userId));
		if (userDb == null) {
			throw new VeikkausServiceException(
					"User with id: " + userId + " wasn't found, insert failed");
		}

		String statusId = betGe.getStatus().getId();
		Status statusDb = statusDao.findOne(Long.valueOf(statusId));
		if (statusDb == null) {
			throw new VeikkausServiceException("Status with id: " + statusId + " wasn't found, insert failed");
		} else {
		}

		betGe.setUser(UserService.convertDbToGui(userDb));
		betGe.setStatus(StatusService.convertDbToGui(statusDb));

		return betDao.save(convertGuiToDb(betGe)).getId();
	}
	
//	public Long modify(String id, String statusId, String userId) throws VeikkausServiceException {
//		Bet bet = betDao.findOne(Long.valueOf(id));
//		if (bet == null) {
//			throw new VeikkausServiceException("bet with id: " + id + " wasn't found, modify failed");
//		}
//
//		User user = userDao.findOne(Long.valueOf(id));
//		if (user == null) {
//			throw new VeikkausServiceException("user with id: " + id + " wasn't found, modify failed");
//		}
//
//		Status status = statusDao.findOne(Long.valueOf(id));
//		if (status == null) {
//			throw new VeikkausServiceException("status with id: " + id + " wasn't found, modify failed");
//		}
//
//		bet.setUser(user);
//		bet.setStatus(status);
//		return betDao.save(bet).getId();
//	}
//

	public Long modify(BetGuiEntity betGe) throws VeikkausServiceException {
		String id = betGe.getId();
		Bet betDb = betDao.findOne(Long.valueOf(id));
		if (betDb == null) {
			throw new VeikkausServiceException("Bet with id: " + id + " wasn't found, modify failed");
		}

		String userId = betGe.getUser().getId();
		User userDb = userDao.findOne(Long.valueOf(userId));
		if (userDb == null) {
			throw new VeikkausServiceException("User with id: " + id + " wasn't found, modify failed");
		}

		String statusId = betGe.getStatus().getId();
		Status statusDb = statusDao.findOne(Long.valueOf(statusId));
		if (statusDb == null) {
			throw new VeikkausServiceException("Status with id: " + id + " wasn't found, modify failed");
		}

		betGe.setUser(UserService.convertDbToGui(userDb));
		betGe.setStatus(StatusService.convertDbToGui(statusDb));

		return betDao.save(convertGuiToDb(betGe)).getId();
	}

	public boolean delete(String id) {
		boolean succeed = false;
		betDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}

	public List<BetGuiEntity> findAllBets() {
		List<BetGuiEntity> geList = new ArrayList<>();
		List<Bet> dbBets = ImmutableList.copyOf(betDao.findAll());

		for (Bet dbBet : dbBets) {
			geList.add(convertDbToGui(dbBet));
		}
		return geList;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public BetGuiEntity findOneBet(String id) {
		BetGuiEntity betGe = convertDbToGui(betDao.findOne(Long.valueOf(id)));
		return betGe;
	}

	protected static BetGuiEntity convertDbToGui(Bet db) {
		BetGuiEntity ge = new BetGuiEntity();

		ge.setId(db.getId().toString());
		ge.setUser(UserService.convertDbToGui(db.getUser()));
		ge.setStatus(StatusService.convertDbToGui(db.getStatus()));
		return ge;
	}

	protected static Bet convertGuiToDb(BetGuiEntity ge) {
		Bet db = new Bet();

		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setUser(UserService.convertGuiToDb(ge.getUser()));
		db.setStatus(StatusService.convertGuiToDb(ge.getStatus()));

		return db;
	}
	
}
