package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.BetResultGuiEntity;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.BetResult;
import fi.joonas.veikkaus.jpaentity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static fi.joonas.veikkaus.constants.VeikkausConstants.INT_NOT_DEFINED;
import static fi.joonas.veikkaus.constants.VeikkausConstants.LONG_NOT_DEFINED;

@Service
public class BetResultService {

    private final BetResultDao betResultDao;
    private final BetDao betDao;
    private final GameDao gameDao;
    private final GameService gameService;

    @Autowired
    public BetResultService(BetResultDao betResultDao, BetDao betDao, GameDao gameDao, GameService gameService) {
        this.betResultDao = betResultDao;
        this.betDao = betDao;
        this.gameDao = gameDao;
        this.gameService = gameService;
    }

    public List<BetResultGuiEntity> findAllBetResults() {
        List<BetResultGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(betResultDao.findAll()).forEach(betResult -> geList.add(convertDbToGui(betResult)));
        return geList;
    }

    public BetResultGuiEntity findOneBetResult(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public BetResultGuiEntity insert(BetResultGuiEntity betResult) throws VeikkausServiceException {
        return save(betResult);
    }

    public BetResultGuiEntity update(BetResultGuiEntity betResult) throws VeikkausServiceException {
        getFromDb(betResult.getId());
        return save(betResult);
    }

    public void delete(Long id) {
        betResultDao.delete(getFromDb(id));
    }

    /**
     * Method populates betResults list with missing games if user hasn't yet saved a bet result
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

    private static class SortByGameDate implements Comparator<BetResult> {
        // Used for sorting in ascending order of gameDate of BetResults
        public int compare(BetResult a, BetResult b) {
            return a.getGame().getGameDate().compareTo(b.getGame().getGameDate());
        }
    }

    public BetResult getFromDb(Long id) {
        return betResultDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(BetResult.class, id));
    }

    private BetResultGuiEntity save(BetResultGuiEntity betResult) {
        // TODO refactoroi
        Long betId = betResult.getBet().getId();
        Bet betDb = betDao.findById(betId).orElseThrow(() -> new VeikkausNotFoundException(Bet.class, betId));
        Game gameDb = gameService.getFromDb(betResult.getGame().getId());
        return convertDbToGui(betResultDao.save(convertGuiToDb(betResult, betDb, gameDb)));
    }

    protected static BetResultGuiEntity convertDbToGui(BetResult db) {
        BetResultGuiEntity ge = new BetResultGuiEntity();

        ge.setId(db.getId());
        ge.setBet(BetService.convertDbToGui(db.getBet()));
        ge.setGame(GameService.convertDbToGui(db.getGame()));
        ge.setHomeScore(db.getHomeScore());
        ge.setAwayScore(db.getAwayScore());
        return ge;
    }

    protected static BetResult convertGuiToDb(BetResultGuiEntity ge, Bet betDb, Game gameDb) {
        BetResult db = new BetResult();

        db.setId(ge.getId());
        db.setBet(betDb);
        db.setGame(gameDb);
        db.setHomeScore(ge.getHomeScore());
        db.setAwayScore(ge.getAwayScore());
        return db;
    }
}