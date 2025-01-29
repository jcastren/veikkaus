package fi.joonas.veikkaus.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetDao betDao;

    private final UserDao userDao;

    private final TournamentDao tournamentDao;

    private final StatusDao statusDao;

    public Long insert(BetGuiEntity betGuiEntity) throws VeikkausServiceException {

        String userId = betGuiEntity.getUser().getId();
        User userDb = userDao.findById(Long.valueOf(userId))
                .orElseThrow(() -> new VeikkausServiceException("User with id: %s wasn't found, insert failed".formatted(userId)));

        String tournamentId = betGuiEntity.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId))
                .orElseThrow(() -> new VeikkausServiceException("Tournament with id: %s wasn't found, insert failed".formatted(tournamentId)));

        String statusId = betGuiEntity.getStatus().getId();
        Status statusDb = statusDao.findById(Long.valueOf(statusId))
                .orElseThrow(() -> new VeikkausServiceException("Status with id: %s wasn't found, insert failed".formatted(statusId)));

        betGuiEntity.setUser(userDb.toGuiEntity());
        betGuiEntity.setTournament(tournamentDb.toGuiEntity());
        betGuiEntity.setStatus(statusDb.toGuiEntity());

        return betDao.save(betGuiEntity.toDbEntity()).getId();
    }

    public Long modify(BetGuiEntity betGuiEntity) throws VeikkausServiceException {

        String id = betGuiEntity.getId();
        Optional<Bet> betDb = betDao.findById(Long.valueOf(id));
        if (betDb.isEmpty()) {
            throw new VeikkausServiceException("Bet with id: %s wasn't found, modify failed".formatted(id));
        }

        String userId = betGuiEntity.getUser().getId();
        User userDb = userDao.findById(Long.valueOf(userId))
                .orElseThrow(() -> new VeikkausServiceException("User with id: %s wasn't found, modify failed".formatted(userId)));

        String tournamentId = betGuiEntity.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId))
                .orElseThrow(() -> new VeikkausServiceException("Tournament with id: %s wasn't found, modify failed".formatted(tournamentId)));

        String statusId = betGuiEntity.getStatus().getId();
        Status statusDb = statusDao.findById(Long.valueOf(statusId))
                .orElseThrow(() -> new VeikkausServiceException("Status with id: %s wasn't found, modify failed".formatted(statusId)));

        betGuiEntity.setUser(userDb.toGuiEntity());
        betGuiEntity.setTournament(tournamentDb.toGuiEntity());
        betGuiEntity.setStatus(statusDb.toGuiEntity());

        return betDao.save(betGuiEntity.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        betDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<BetGuiEntity> findAllBets() {

        List<BetGuiEntity> betGuiEntityList = new ArrayList<>();
        betDao.findAll().forEach(dbBet -> betGuiEntityList.add(dbBet.toGuiEntity()));
        return betGuiEntityList;
    }

    public BetGuiEntity findOneBet(String id) {

        return betDao.findById(Long.valueOf(id))
                .map(Bet::toGuiEntity)
                .orElse(BetGuiEntity.builder().build());
    }

}
