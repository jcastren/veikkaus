package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.guientity.BetGuiEntity;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BetService {

    private final BetDao betDao;
    private final UserService userService;
    private final TournamentService tournamentService;
    private final StatusService statusService;

    @Autowired
    public BetService(BetDao betDao, UserService userService, TournamentService tournamentService, StatusService statusService) {
        this.betDao = betDao;
        this.userService = userService;
        this.tournamentService = tournamentService;
        this.statusService = statusService;
    }

    public List<BetGuiEntity> findAllBets() {
        List<BetGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(betDao.findAll()).forEach(bet -> geList.add(convertDbToGui(bet)));
        return geList;
    }

    public BetGuiEntity findOneBet(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public BetGuiEntity insert(BetGuiEntity bet) {
        return save(bet);
    }

    public BetGuiEntity update(BetGuiEntity bet) {
        getFromDb(bet.getId());
        return save(bet);
    }

    public void delete(Long id) {
        betDao.delete(getFromDb(id));
    }

    public Bet getFromDb(Long id) {
        return betDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Bet.class, id));
    }

    private BetGuiEntity save(BetGuiEntity bet) {
        User userDb = userService.getFromDb(bet.getUser().getId());
        Tournament tournamentDb = tournamentService.getFromDb(bet.getTournament().getId());
        Status statusDb = statusService.getFromDb(bet.getStatus().getId());
        return convertDbToGui(betDao.save(convertGuiToDb(bet, userDb, tournamentDb, statusDb)));
    }

    protected static BetGuiEntity convertDbToGui(Bet db) {
        BetGuiEntity ge = new BetGuiEntity();

        ge.setId(db.getId());
        ge.setUser(UserService.convertDbToGui(db.getUser()));
        ge.setTournament(TournamentService.convertDbToGui(db.getTournament()));
        ge.setStatus(StatusService.convertDbToGui(db.getStatus()));
        return ge;
    }

    protected static Bet convertGuiToDb(BetGuiEntity ge, User userDb, Tournament tournamentDb, Status statusDb) {
        Bet db = new Bet();

        db.setId(ge.getId());
        db.setUser(userDb);
        db.setTournament(tournamentDb);
        db.setStatus(statusDb);
        return db;
    }
}
