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
        if (userDb.isEmpty()) {
            throw new VeikkausServiceException("User with id: %s wasn't found, insert failed".formatted(userId));
        }

        String tournamentId = betGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (tournamentDb.isEmpty()) {
            throw new VeikkausServiceException(
                    "Tournament with id: %s wasn't found, insert failed".formatted(tournamentId));
        }

        String statusId = betGe.getStatus().getId();
        Optional<Status> statusDb = statusDao.findById(Long.valueOf(statusId));
        if (statusDb.isEmpty()) {
            throw new VeikkausServiceException("Status with id: %s wasn't found, insert failed".formatted(statusId));
        }

        betGe.setUser(UserService.convertDbToGui(userDb.get()));
        betGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        betGe.setStatus(StatusService.convertDbToGui(statusDb.get()));

        return betDao.save(convertGuiToDb(betGe)).getId();
    }

    public Long modify(BetGuiEntity betGe) throws VeikkausServiceException {
        String id = betGe.getId();
        Optional<Bet> betDb = betDao.findById(Long.valueOf(id));
        if (betDb.isEmpty()) {
            throw new VeikkausServiceException("Bet with id: %s wasn't found, modify failed".formatted(id));
        }

        String userId = betGe.getUser().getId();
        Optional<User> userDb = userDao.findById(Long.valueOf(userId));
        if (userDb.isEmpty()) {
            throw new VeikkausServiceException("User with id: %s wasn't found, modify failed".formatted(userId));
        }

        String tournamentId = betGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (tournamentDb.isEmpty()) {
            throw new VeikkausServiceException("Tournament with id: %s wasn't found, modify failed".formatted(tournamentId));
        }

        String statusId = betGe.getStatus().getId();
        Optional<Status> statusDb = statusDao.findById(Long.valueOf(statusId));
        if (statusDb.isEmpty()) {
            throw new VeikkausServiceException("Status with id: %s wasn't found, modify failed".formatted(statusId));
        }

        betGe.setUser(UserService.convertDbToGui(userDb.get()));
        betGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        betGe.setStatus(StatusService.convertDbToGui(statusDb.get()));

        return betDao.save(convertGuiToDb(betGe)).getId();
    }

    public boolean delete(String id) {
        betDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<BetGuiEntity> findAllBets() {
        List<BetGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(betDao.findAll()).forEach(bet -> geList.add(convertDbToGui(bet)));
        return geList;
    }

    public BetGuiEntity findOneBet(String id) {
        return convertDbToGui(betDao.findById(Long.valueOf(id)).get());
    }

}
