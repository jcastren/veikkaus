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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GameService {

    @Autowired
    GameDao gameDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    TournamentTeamDao tournamentTeamDao;

    public Long insert(GameGuiEntity gameGuiEntity) throws VeikkausServiceException {

        Long returnedGameId;

        String tournamentId = gameGuiEntity.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Tournament with id: %s wasn't found, insert failed", tournamentId));
        }

        String homeTeamId = gameGuiEntity.getHomeTeam().getId();
        Optional<TournamentTeam> homeTeamDb = tournamentTeamDao.findById(Long.valueOf(homeTeamId));
        if (!homeTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("TournamentTeam (homeTeam) with id: %s wasn't found, insert failed", homeTeamId));
        }

        String awayTeamId = gameGuiEntity.getAwayTeam().getId();
        Optional<TournamentTeam> awayTeamDb = tournamentTeamDao.findById(Long.valueOf(awayTeamId));
        if (!awayTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("TournamentTeam (awayTeam) with id: %s wasn't found, insert failed", awayTeamId));
        }

        /** TODO Why converting back ??? */
        gameGuiEntity.setTournament(tournamentDb.get().toGuiEntity());
        gameGuiEntity.setHomeTeam(homeTeamDb.get().toGuiEntity());
        gameGuiEntity.setAwayTeam(awayTeamDb.get().toGuiEntity());

        try {
            returnedGameId = gameDao.save(gameGuiEntity.toDbEntity()).getId();
        } catch (VeikkausConversionException vce) {
            throw new VeikkausServiceException("Saving game failed.", vce);
        }
        return returnedGameId;
    }

    /**
     * @param gameGuiEntity
     * @return
     */
    public Long modify(GameGuiEntity gameGuiEntity) throws VeikkausServiceException {

        Long returnedGameId;

        String id = gameGuiEntity.getId();
        Optional<Game> gameDb = gameDao.findById(Long.valueOf(id));
        if (!gameDb.isPresent()) {
            throw new VeikkausServiceException("Game with id: " + id + " wasn't found, modify failed");
        }

        String tournamentId = gameGuiEntity.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Tournament with id: %s wasn't found, modify failed", tournamentId));
        }

        String homeTeamId = gameGuiEntity.getHomeTeam().getId();
        Optional<TournamentTeam> homeTeamDb = tournamentTeamDao.findById(Long.valueOf(homeTeamId));
        if (!homeTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Home team with id: %s wasn't found, modify failed", homeTeamId));
        }

        String awayTeamId = gameGuiEntity.getAwayTeam().getId();
        Optional<TournamentTeam> awayTeamDb = tournamentTeamDao.findById(Long.valueOf(awayTeamId));
        if (!awayTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    String.format("Away team with id: %s wasn't found, modify failed", awayTeamId));
        }

        gameGuiEntity.setTournament(tournamentDb.get().toGuiEntity());
        gameGuiEntity.setHomeTeam(homeTeamDb.get().toGuiEntity());
        gameGuiEntity.setAwayTeam(awayTeamDb.get().toGuiEntity());

        try {
            returnedGameId = gameDao.save(gameGuiEntity.toDbEntity()).getId();
        } catch (VeikkausConversionException vce) {
            throw new VeikkausServiceException("Saving game failed.", vce);
        }
        return returnedGameId;
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

        List<GameGuiEntity> gameGuiEntityList = new ArrayList<>();
        List<Game> dbGames = ImmutableList.copyOf(gameDao.findAll());

        for (Game dbGame : dbGames) {
            gameGuiEntityList.add(dbGame.toGuiEntity());
        }

        return gameGuiEntityList;
    }

    public List<GameGuiEntity> findTournamentGames(String tournamentId) {

        Optional<Tournament> dbTournament = tournamentDao.findById(Long.valueOf(tournamentId));

        List<GameGuiEntity> gameGuiEntityList = new ArrayList<>();
        List<Game> dbGames = ImmutableList.copyOf(gameDao.findByTournament(dbTournament.get()));

        for (Game dbGame : dbGames) {
            gameGuiEntityList.add(dbGame.toGuiEntity());
        }
        return gameGuiEntityList;
    }

    public GameGuiEntity findOneGame(String id) {

        return gameDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }
}
