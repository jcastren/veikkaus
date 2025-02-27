package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.BetResultGuiEntity;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.BetResult;
import fi.joonas.veikkaus.jpaentity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fi.joonas.veikkaus.constants.VeikkausConstants.INT_NOT_DEFINED;
import static fi.joonas.veikkaus.constants.VeikkausConstants.LONG_NOT_DEFINED;

@Service
public class BetResultService {

    @Autowired
    BetResultDao betResultDao;

    @Autowired
    BetDao betDao;

    @Autowired
    GameDao gameDao;

    protected static BetResultGuiEntity convertDbToGui(BetResult db) {
        BetResultGuiEntity ge = new BetResultGuiEntity();
        ge.setId(db.getId().toString());
        ge.setBet(BetService.convertDbToGui(db.getBet()));
        ge.setGame(GameService.convertDbToGui(db.getGame()));
        ge.setHomeScore(db.getHomeScore());
        ge.setAwayScore(db.getAwayScore());
        return ge;
    }

    protected static BetResult convertGuiToDb(BetResultGuiEntity ge) {
        BetResult db = new BetResult();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setBet(BetService.convertGuiToDb(ge.getBet()));
        try {
            db.setGame(GameService.convertGuiToDb(ge.getGame()));
        } catch (VeikkausConversionException e) {
            e.printStackTrace();
        }
        db.setHomeScore(ge.getHomeScore());
        db.setAwayScore(ge.getAwayScore());

        return db;
    }

    public Long insert(BetResultGuiEntity betResultGe) throws VeikkausServiceException {
        String betId = betResultGe.getBet().getId();
        Optional<Bet> betDb = betDao.findById(Long.valueOf(betId));
        if (!betDb.isPresent()) {
            throw new VeikkausServiceException("Bet with id: " + betId + " wasn't found, insert failed");
        }

        String gameId = betResultGe.getGame().getId();
        Optional<Game> gameDb = gameDao.findById(Long.valueOf(gameId));
        if (!gameDb.isPresent()) {
            throw new VeikkausServiceException("Game with id: " + gameId + " wasn't found, insert failed");
        }

        betResultGe.setBet(BetService.convertDbToGui(betDb.get()));
        betResultGe.setGame(GameService.convertDbToGui(gameDb.get()));

        return betResultDao.save(convertGuiToDb(betResultGe)).getId();
    }

    public Long modify(BetResultGuiEntity betResultGe) throws VeikkausServiceException {
        String id = betResultGe.getId();
        Optional<BetResult> betResultDb = betResultDao.findById(Long.valueOf(id));
        if (!betResultDb.isPresent()) {
            throw new VeikkausServiceException("Bet result with id: " + id + " wasn't found, modify failed");
        }

        String betId = betResultGe.getBet().getId();
        Optional<Bet> betDb = betDao.findById(Long.valueOf(betId));
        if (!betDb.isPresent()) {
            throw new VeikkausServiceException("Bet with id: " + id + " wasn't found, modify failed");
        }

        String gameId = betResultGe.getGame().getId();
        Optional<Game> gameDb = gameDao.findById(Long.valueOf(gameId));
        if (!gameDb.isPresent()) {
            throw new VeikkausServiceException("Game with id: " + id + " wasn't found, modify failed");
        }

        betResultGe.setBet(BetService.convertDbToGui(betDb.get()));
        betResultGe.setGame(GameService.convertDbToGui(gameDb.get()));

        return betResultDao.save(convertGuiToDb(betResultGe)).getId();
    }

    public boolean delete(String id) {
        boolean succeed;
        betResultDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    public List<BetResultGuiEntity> findAllBetResults() {
        List<BetResultGuiEntity> geList = new ArrayList<>();
        List<BetResult> dbBetResults = ImmutableList.copyOf(betResultDao.findAll());

        for (BetResult dbBetResult : dbBetResults) {
            geList.add(convertDbToGui(dbBetResult));
        }
        return geList;
    }

    /**
     * Method populates betResults list with missing games if user hasn't yet saved a bet result
     *
     * @param betId
     * @return
     */
    public List<BetResultGuiEntity> findBetGamesAndBetResults(String betId) {
        Optional<Bet> dbBet = betDao.findById(Long.valueOf(betId));
        List<BetResult> betResultListPopulatedWithMissingGames = betResultDao.findByBet(dbBet.get());
        List<BetResult> dbBetResults = ImmutableList.copyOf(betResultListPopulatedWithMissingGames);
        List<Game> dbGames = ImmutableList.copyOf(gameDao.findByTournamentOrderByGameDate(dbBet.get().getTournament()));

        List<BetResultGuiEntity> geList = new ArrayList<>();
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
                betResultWithoutScore.setBet(dbBet.get());
                betResultWithoutScore.setGame(dbGame);
                betResultWithoutScore.setHomeScore(INT_NOT_DEFINED);
                betResultWithoutScore.setAwayScore(INT_NOT_DEFINED);
                betResultListPopulatedWithMissingGames.add(betResultWithoutScore);
            }
        }
        Collections.sort(betResultListPopulatedWithMissingGames, new SortByGameDate());

        for (BetResult dbBetResult : betResultListPopulatedWithMissingGames) {
            geList.add(convertDbToGui(dbBetResult));
        }
        return geList;
    }

    public BetResultGuiEntity findOneBetResult(String id) {
        BetResultGuiEntity betResultGe = convertDbToGui(betResultDao.findById(Long.valueOf(id)).get());
        return betResultGe;
    }

    private class SortByGameDate implements Comparator<BetResult> {
        // Used for sorting in ascending order of gameDate of BetResults
        public int compare(BetResult a, BetResult b) {
            return a.getGame().getGameDate().compareTo(b.getGame().getGameDate());
        }
    }

}
