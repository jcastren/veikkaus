package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.VeikkausUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fi.joonas.veikkaus.constants.VeikkausConstants.INT_NOT_DEFINED;

@Service
@Slf4j
public class GameService {

    @Autowired
    GameDao gameDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    TournamentTeamDao tournamentTeamDao;

    protected static GameGuiEntity convertDbToGui(Game db) {
        GameGuiEntity ge = new GameGuiEntity();

        ge.setId(db.getId().toString());
        ge.setTournament(TournamentService.convertDbToGui(db.getTournament()));
        ge.setHomeTeam(TournamentTeamService.convertDbToGui(db.getHomeTeam()));
        ge.setAwayTeam(TournamentTeamService.convertDbToGui(db.getAwayTeam()));
        ge.setHomeScore(Integer.valueOf(db.getHomeScore()).toString());
        ge.setAwayScore(Integer.valueOf(db.getAwayScore()).toString());
        ge.setGameDate(VeikkausUtil.getDateAsString(db.getGameDate()));
        return ge;
    }

    protected static Game convertGuiToDb(GameGuiEntity ge) throws VeikkausConversionException {
        Game db = new Game();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }

        try {
            db.setHomeScore(Integer.parseInt(ge.getHomeScore()));
        } catch (NumberFormatException nfe) {
            log.info(String.format("Error while parsing integer '%s' Using integer %d instead", ge.getHomeScore(), INT_NOT_DEFINED), nfe);
            db.setHomeScore(INT_NOT_DEFINED);
        }
        try {
            db.setAwayScore(Integer.parseInt(ge.getAwayScore()));
        } catch (NumberFormatException nfe) {
            log.info(String.format("Error while parsing integer '%s' Using integer %d instead", ge.getAwayScore(), INT_NOT_DEFINED), nfe);
            db.setAwayScore(INT_NOT_DEFINED);
        }
        try {
            db.setGameDate(VeikkausUtil.getStringAsDate(ge.getGameDate()));
        } catch (ParseException pe) {
            log.error("Error while parsing date: " + ge.getGameDate(), pe);
            throw new VeikkausConversionException("Error while parsing date: " + ge.getGameDate(), pe);
        }
        db.setTournament(TournamentService.convertGuiToDb(ge.getTournament()));
        db.setHomeTeam(TournamentTeamService.convertGuiToDb(ge.getHomeTeam()));
        db.setAwayTeam(TournamentTeamService.convertGuiToDb(ge.getAwayTeam()));

        return db;
    }

    public Long insert(GameGuiEntity gameGe) throws VeikkausServiceException {
        Long retGameId = null;

        String tournamentId = gameGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Tournament with id: %s wasn't found, insert failed", tournamentId));
        }

        String homeTeamId = gameGe.getHomeTeam().getId();
        Optional<TournamentTeam> homeTeamDb = tournamentTeamDao.findById(Long.valueOf(homeTeamId));
        if (!homeTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("TournamentTeam (homeTeam) with id: %s wasn't found, insert failed", homeTeamId));
        }

        String awayTeamId = gameGe.getAwayTeam().getId();
        Optional<TournamentTeam> awayTeamDb = tournamentTeamDao.findById(Long.valueOf(awayTeamId));
        if (!awayTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("TournamentTeam (awayTeam) with id: %s wasn't found, insert failed", awayTeamId));
        }

        /** TODO Why converting back ??? */
        gameGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        gameGe.setHomeTeam(TournamentTeamService.convertDbToGui(homeTeamDb.get()));
        gameGe.setAwayTeam(TournamentTeamService.convertDbToGui(awayTeamDb.get()));

        try {
            retGameId = gameDao.save(convertGuiToDb(gameGe)).getId();
        } catch (VeikkausConversionException vce) {
            throw new VeikkausServiceException("Saving game failed.", vce);
        }
        return retGameId;
    }

    /**
     * @param gameGe
     * @return
     */
    public Long modify(GameGuiEntity gameGe) throws VeikkausServiceException {
        Long retGameId = null;

        String id = gameGe.getId();
        Optional<Game> gameDb = gameDao.findById(Long.valueOf(id));
        if (!gameDb.isPresent()) {
            throw new VeikkausServiceException("Game with id: " + id + " wasn't found, modify failed");
        }

        String tournamentId = gameGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Tournament with id: %s wasn't found, modify failed", tournamentId));
        }

        String homeTeamId = gameGe.getHomeTeam().getId();
        Optional<TournamentTeam> homeTeamDb = tournamentTeamDao.findById(Long.valueOf(homeTeamId));
        if (!homeTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Home team with id: %s wasn't found, modify failed", homeTeamId));
        }

        String awayTeamId = gameGe.getAwayTeam().getId();
        Optional<TournamentTeam> awayTeamDb = tournamentTeamDao.findById(Long.valueOf(awayTeamId));
        if (!awayTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Away team with id: %s wasn't found, modify failed", awayTeamId));
        }

        gameGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        gameGe.setHomeTeam(TournamentTeamService.convertDbToGui(homeTeamDb.get()));
        gameGe.setAwayTeam(TournamentTeamService.convertDbToGui(awayTeamDb.get()));

        try {
            retGameId = gameDao.save(convertGuiToDb(gameGe)).getId();
        } catch (VeikkausConversionException vce) {
            throw new VeikkausServiceException("Saving game failed.", vce);
        }
        return retGameId;
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(String id) throws VeikkausServiceException {
        boolean succeed;
        gameDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    /**
     * @return
     */
    public List<GameGuiEntity> findAllGames() {
        List<GameGuiEntity> geList = new ArrayList<>();
        List<Game> dbGames = ImmutableList.copyOf(gameDao.findAll());

        for (Game dbGame : dbGames) {
            geList.add(convertDbToGui(dbGame));
        }

        return geList;
    }

    public List<GameGuiEntity> findTournamentGames(String tournamentId) {
        Optional<Tournament> dbTournament = tournamentDao.findById(Long.valueOf(tournamentId));

        List<GameGuiEntity> geList = new ArrayList<>();
        List<Game> dbGames = ImmutableList.copyOf(gameDao.findByTournament(dbTournament.get()));

        for (Game dbGame : dbGames) {
            geList.add(convertDbToGui(dbGame));
        }
        return geList;
    }

    public GameGuiEntity findOneGame(String id) {
        GameGuiEntity gameGe = convertDbToGui(gameDao.findById(Long.valueOf(id)).get());
        return gameGe;
    }

}
