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

    protected static BetGuiEntity convertDbToGui(Bet db) {
        BetGuiEntity ge = new BetGuiEntity();

        ge.setId(db.getId().toString());
        ge.setUser(UserService.convertDbToGui(db.getUser()));
        ge.setTournament(TournamentService.convertDbToGui(db.getTournament()));
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
        db.setTournament(TournamentService.convertGuiToDb(ge.getTournament()));
        db.setStatus(StatusService.convertGuiToDb(ge.getStatus()));

        return db;
    }

    public Long insert(BetGuiEntity betGe) throws VeikkausServiceException {
        String userId = betGe.getUser().getId();
        Optional<User> userDb = userDao.findById(Long.valueOf(userId));
        if (!userDb.isPresent()) {
            throw new VeikkausServiceException("User with id: " + userId + " wasn't found, insert failed");
        }

        String tournamentId = betGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException(
                    "Tournament with id: " + tournamentId + " wasn't found, insert failed");
        }

        String statusId = betGe.getStatus().getId();
        Optional<Status> statusDb = statusDao.findById(Long.valueOf(statusId));
        if (!statusDb.isPresent()) {
            throw new VeikkausServiceException("Status with id: " + statusId + " wasn't found, insert failed");
        }

        betGe.setUser(UserService.convertDbToGui(userDb.get()));
        betGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        betGe.setStatus(StatusService.convertDbToGui(statusDb.get()));

        return betDao.save(convertGuiToDb(betGe)).getId();
    }

    public Long modify(BetGuiEntity betGe) throws VeikkausServiceException {
        String id = betGe.getId();
        Optional<Bet> betDb = betDao.findById(Long.valueOf(id));
        if (!betDb.isPresent()) {
            throw new VeikkausServiceException("Bet with id: " + id + " wasn't found, modify failed");
        }

        String userId = betGe.getUser().getId();
        Optional<User> userDb = userDao.findById(Long.valueOf(userId));
        if (!userDb.isPresent()) {
            throw new VeikkausServiceException("User with id: " + id + " wasn't found, modify failed");
        }

        String tournamentId = betGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException("Tournament with id: " + tournamentId + " wasn't found, modify failed");
        }

        String statusId = betGe.getStatus().getId();
        Optional<Status> statusDb = statusDao.findById(Long.valueOf(statusId));
        if (!statusDb.isPresent()) {
            throw new VeikkausServiceException("Status with id: " + id + " wasn't found, modify failed");
        }

        betGe.setUser(UserService.convertDbToGui(userDb.get()));
        betGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        betGe.setStatus(StatusService.convertDbToGui(statusDb.get()));

        return betDao.save(convertGuiToDb(betGe)).getId();
    }

    public boolean delete(String id) {
        boolean succeed = false;
        betDao.deleteById(Long.valueOf(id));
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

    public BetGuiEntity findOneBet(String id) {
        BetGuiEntity betGe = convertDbToGui(betDao.findById(Long.valueOf(id)).get());
        return betGe;
    }

}
