package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.BetResultGuiEntity;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.BetResult;
import fi.joonas.veikkaus.jpaentity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static fi.joonas.veikkaus.constants.VeikkausConstants.INT_NOT_DEFINED;
import static fi.joonas.veikkaus.constants.VeikkausConstants.LONG_NOT_DEFINED;
import static java.lang.Long.valueOf;

@Service
@RequiredArgsConstructor
public class BetResultService {

    private final BetResultDao betResultDao;

    private final BetDao betDao;

    private final GameDao gameDao;

    public Long insert(BetResultGuiEntity betResultGuiEntity) throws VeikkausServiceException {

        String betId = betResultGuiEntity.getBet().getId();
        Optional<Bet> betDb = betDao.findById(valueOf(betId));
        if (betDb.isEmpty()) {
            throw new VeikkausServiceException("Bet with id: " + betId + " wasn't found, insert failed");
        }

        String gameId = betResultGuiEntity.getGame().getId();
        Optional<Game> gameDb = gameDao.findById(valueOf(gameId));
        if (gameDb.isEmpty()) {
            throw new VeikkausServiceException("Game with id: " + gameId + " wasn't found, insert failed");
        }

        betResultGuiEntity.setBet(betDb.get().toGuiEntity());
        betResultGuiEntity.setGame(gameDb.get().toGuiEntity());

        return betResultDao.save(betResultGuiEntity.toDbEntity()).getId();
    }

    public Long modify(BetResultGuiEntity betResultGuiEntity) throws VeikkausServiceException {

        String id = betResultGuiEntity.getId();
        Optional<BetResult> betResultDb = betResultDao.findById(valueOf(id));
        if (betResultDb.isEmpty()) {
            throw new VeikkausServiceException("Bet result with id: " + id + " wasn't found, modify failed");
        }

        String betId = betResultGuiEntity.getBet().getId();
        Optional<Bet> betDb = betDao.findById(valueOf(betId));
        if (betDb.isEmpty()) {
            throw new VeikkausServiceException("Bet with id: " + id + " wasn't found, modify failed");
        }

        String gameId = betResultGuiEntity.getGame().getId();
        Optional<Game> gameDb = gameDao.findById(valueOf(gameId));
        if (gameDb.isEmpty()) {
            throw new VeikkausServiceException("Game with id: " + id + " wasn't found, modify failed");
        }

        betResultGuiEntity.setBet(betDb.get().toGuiEntity());
        betResultGuiEntity.setGame(gameDb.get().toGuiEntity());

        return betResultDao.save(betResultGuiEntity.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        betResultDao.deleteById(valueOf(id));
        return true;
    }

    public List<BetResultGuiEntity> findAllBetResults() {

        List<BetResultGuiEntity> geList = new ArrayList<>();
        List<BetResult> dbBetResults = ImmutableList.copyOf(betResultDao.findAll());

        for (BetResult dbBetResult : dbBetResults) {
            geList.add(dbBetResult.toGuiEntity());
        }
        return geList;
    }

    /**
     * Method populates betResults list with missing games if user hasn't yet saved a bet result
     */
    public List<BetResultGuiEntity> findBetGamesAndBetResults(String betId) {

        Bet dbBet = betDao.findById(valueOf(betId)).orElse(Bet.builder().build());
        List<BetResult> betResultListPopulatedWithMissingGames = betResultDao.findByBet(dbBet);
        List<BetResult> dbBetResults = ImmutableList.copyOf(betResultListPopulatedWithMissingGames);
        List<Game> dbGames = ImmutableList.copyOf(gameDao.findByTournamentOrderByGameDate(dbBet.getTournament()));

        List<BetResultGuiEntity> betResultGuiEntityList = new ArrayList<>();
        for (Game dbGame : dbGames) {

            BetResult match = null;

            for (BetResult betResult : dbBetResults) {
                if (dbGame.equals(betResult.getGame())) {
                    match = betResult;
                    break;
                }
            }

            if (match == null) {
                BetResult betResultWithoutScore = new BetResult();
                betResultWithoutScore.setId(LONG_NOT_DEFINED);
                betResultWithoutScore.setBet(dbBet);
                betResultWithoutScore.setGame(dbGame);
                betResultWithoutScore.setHomeScore(INT_NOT_DEFINED);
                betResultWithoutScore.setAwayScore(INT_NOT_DEFINED);
                betResultListPopulatedWithMissingGames.add(betResultWithoutScore);
            }
        }
        betResultListPopulatedWithMissingGames.sort(new SortByGameDate());

        for (BetResult dbBetResult : betResultListPopulatedWithMissingGames) {
            betResultGuiEntityList.add(dbBetResult.toGuiEntity());
        }
        return betResultGuiEntityList;
    }

    public BetResultGuiEntity findOneBetResult(String id) {

        return betResultDao.findById(valueOf(id))
                .map(BetResult::toGuiEntity)
                .orElse(BetResultGuiEntity.builder().build());
    }

    private static class SortByGameDate implements Comparator<BetResult> {

        // Used for sorting in ascending order of gameDate of BetResults
        public int compare(BetResult a, BetResult b) {
            return a.getGame().getGameDate().compareTo(b.getGame().getGameDate());
        }
    }

}
