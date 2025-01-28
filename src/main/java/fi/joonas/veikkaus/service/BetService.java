package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.BetGuiEntity;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BetService {

    @Autowired
    BetDao betDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    StatusDao statusDao;

    protected static Bet convertGuiToDb(BetGuiEntity betGuiEntity) {

        Bet db = new Bet();

        if (betGuiEntity.getId() != null && !betGuiEntity.getId().isEmpty()) {
            db.setId(Long.valueOf(betGuiEntity.getId()));
        } else {
            db.setId(null);
        }
        db.setUser(UserService.convertGuiToDb(betGuiEntity.getUser()));
        db.setTournament(TournamentService.convertGuiToDb(betGuiEntity.getTournament()));
        db.setStatus(StatusService.convertGuiToDb(betGuiEntity.getStatus()));

        return db;
    }

    public Long insert(BetGuiEntity betGuiEntity) throws VeikkausServiceException {

        String userId = betGuiEntity.getUser().getId();
        Optional<User> userDb = userDao.findById(Long.valueOf(userId));
        if (!userDb.isPresent()) {
            throw new VeikkausServiceException("User with id: " + userId + " wasn't found, insert failed");
        }

        String tournamentId = betGuiEntity.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException(
                    "Tournament with id: " + tournamentId + " wasn't found, insert failed");
        }

        String statusId = betGuiEntity.getStatus().getId();
        Optional<Status> statusDb = statusDao.findById(Long.valueOf(statusId));
        if (!statusDb.isPresent()) {
            throw new VeikkausServiceException("Status with id: " + statusId + " wasn't found, insert failed");
        }

        betGuiEntity.setUser(userDb.get().toGuiEntity());
        betGuiEntity.setTournament(tournamentDb.get().toGuiEntity());
        betGuiEntity.setStatus(statusDb.get().toGuiEntity());

        return betDao.save(convertGuiToDb(betGuiEntity)).getId();
    }

    public Long modify(BetGuiEntity betGuiEntity) throws VeikkausServiceException {

        String id = betGuiEntity.getId();
        Optional<Bet> betDb = betDao.findById(Long.valueOf(id));
        if (!betDb.isPresent()) {
            throw new VeikkausServiceException("Bet with id: " + id + " wasn't found, modify failed");
        }

        String userId = betGuiEntity.getUser().getId();
        Optional<User> userDb = userDao.findById(Long.valueOf(userId));
        if (!userDb.isPresent()) {
            throw new VeikkausServiceException("User with id: " + id + " wasn't found, modify failed");
        }

        String tournamentId = betGuiEntity.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException("Tournament with id: " + tournamentId + " wasn't found, modify failed");
        }

        String statusId = betGuiEntity.getStatus().getId();
        Optional<Status> statusDb = statusDao.findById(Long.valueOf(statusId));
        if (!statusDb.isPresent()) {
            throw new VeikkausServiceException("Status with id: " + id + " wasn't found, modify failed");
        }

        betGuiEntity.setUser(userDb.get().toGuiEntity());
        betGuiEntity.setTournament(tournamentDb.get().toGuiEntity());
        betGuiEntity.setStatus(statusDb.get().toGuiEntity());

        return betDao.save(convertGuiToDb(betGuiEntity)).getId();
    }

    public boolean delete(String id) {

        boolean succeed;
        betDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    public List<BetGuiEntity> findAllBets() {

        List<BetGuiEntity> betGuiEntityList = new ArrayList<>();
        List<Bet> dbBets = ImmutableList.copyOf(betDao.findAll());

        for (Bet dbBet : dbBets) {
            betGuiEntityList.add(dbBet.toGuiEntity());
        }
        return betGuiEntityList;
    }

    public BetGuiEntity findOneBet(String id) {

        return betDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}
