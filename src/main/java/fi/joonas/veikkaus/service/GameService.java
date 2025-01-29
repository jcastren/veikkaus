package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameDao gameDao;

    private final TournamentDao tournamentDao;

    private final TournamentTeamDao tournamentTeamDao;

    public Long insert(GameGuiEntity gameGuiEntity) throws VeikkausServiceException {

        Long returnedGameId;

        String tournamentId = gameGuiEntity.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "Tournament with id: %s wasn't found, insert failed".formatted(tournamentId)));

        String homeTeamId = gameGuiEntity.getHomeTeam().getId();
        TournamentTeam homeTeamDb = tournamentTeamDao.findById(Long.valueOf(homeTeamId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "TournamentTeam (homeTeam) with id: %s wasn't found, insert failed".formatted(homeTeamId)));

        String awayTeamId = gameGuiEntity.getAwayTeam().getId();

        TournamentTeam awayTeamDb = tournamentTeamDao.findById(Long.valueOf(awayTeamId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "TournamentTeam (awayTeam) with id: %s wasn't found, insert failed".formatted(awayTeamId)));

        /** TODO Why converting back ??? */
        gameGuiEntity.setTournament(tournamentDb.toGuiEntity());
        gameGuiEntity.setHomeTeam(homeTeamDb.toGuiEntity());
        gameGuiEntity.setAwayTeam(awayTeamDb.toGuiEntity());

        try {
            returnedGameId = gameDao.save(gameGuiEntity.toDbEntity()).getId();
        } catch (VeikkausConversionException vce) {
            throw new VeikkausServiceException("Saving game failed.", vce);
        }
        return returnedGameId;
    }

    public Long modify(GameGuiEntity gameGuiEntity) throws VeikkausServiceException {

        Long returnedGameId;

        String id = gameGuiEntity.getId();
        if (gameDao.findById(Long.valueOf(id)).isEmpty()) {
            throw new VeikkausServiceException("Game with id: %s wasn't found, modify failed".formatted(id));
        }

        String tournamentId = gameGuiEntity.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "Tournament with id: %s wasn't found, modify failed".formatted(tournamentId)));

        String homeTeamId = gameGuiEntity.getHomeTeam().getId();
        TournamentTeam homeTeamDb = tournamentTeamDao.findById(Long.valueOf(homeTeamId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "Home team with id: %s wasn't found, modify failed".formatted(homeTeamId)));

        String awayTeamId = gameGuiEntity.getAwayTeam().getId();
        TournamentTeam awayTeamDb = tournamentTeamDao.findById(Long.valueOf(awayTeamId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "Away team with id: %s wasn't found, modify failed".formatted(awayTeamId)));

        gameGuiEntity.setTournament(tournamentDb.toGuiEntity());
        gameGuiEntity.setHomeTeam(homeTeamDb.toGuiEntity());
        gameGuiEntity.setAwayTeam(awayTeamDb.toGuiEntity());

        try {
            returnedGameId = gameDao.save(gameGuiEntity.toDbEntity()).getId();
        } catch (VeikkausConversionException vce) {
            throw new VeikkausServiceException("Saving game failed.", vce);
        }
        return returnedGameId;
    }

    public boolean delete(String id) throws VeikkausServiceException {

        gameDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<GameGuiEntity> findAllGames() {

        List<GameGuiEntity> gameGuiEntityList = new ArrayList<>();
        gameDao.findAll().forEach(game -> gameGuiEntityList.add(game.toGuiEntity()));
        return gameGuiEntityList;
    }

    public List<GameGuiEntity> findTournamentGames(String tournamentId) {

        Tournament dbTournament = tournamentDao.findById(Long.valueOf(tournamentId))
                .orElse(Tournament.builder().build());

        List<GameGuiEntity> gameGuiEntityList = new ArrayList<>();
        gameDao.findByTournament(dbTournament).forEach(game -> gameGuiEntityList.add(game.toGuiEntity()));
        return gameGuiEntityList;
    }

    public GameGuiEntity findOneGame(String id) {

        return gameDao.findById(Long.valueOf(id))
                .map(Game::toGuiEntity)
                .orElse(GameGuiEntity.builder().build());
    }
}
