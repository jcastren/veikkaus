package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import fi.joonas.veikkaus.util.VeikkausUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final GameDao gameDao;
    private final TournamentDao tournamentDao;
    private final TournamentTeamDao tournamentTeamDao;

    @Autowired
    public GameService(GameDao gameDao, TournamentDao tournamentDao, TournamentTeamDao tournamentTeamDao) {
        this.gameDao = gameDao;
        this.tournamentDao = tournamentDao;
        this.tournamentTeamDao = tournamentTeamDao;
    }

    public List<GameGuiEntity> findAllGames() {
        List<GameGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(gameDao.findAll()).forEach(game -> geList.add(convertDbToGui(game)));
        return geList;
    }

    public GameGuiEntity findOneGame(Long id) {
        Game db = gameDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Game.class, id));
        return convertDbToGui(db);
    }

    public List<GameGuiEntity> findTournamentGames(Long tournamentId) {
        Tournament tournamentDb = tournamentDao.findById(tournamentId).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, tournamentId));
        List<GameGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(gameDao.findByTournament(tournamentDb)).forEach(tournamentGame -> geList.add(convertDbToGui(tournamentGame)));
        return geList;
    }

    public GameGuiEntity insert(GameGuiEntity game) throws VeikkausServiceException {
        Long tournamentId = game.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(tournamentId).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, tournamentId));
        Long homeTeamId = game.getHomeTeam().getId();
        TournamentTeam homeTeamDb = tournamentTeamDao.findById(homeTeamId).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, homeTeamId));
        Long awayTeamId = game.getAwayTeam().getId();
        TournamentTeam awayTeamDb = tournamentTeamDao.findById(awayTeamId).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, awayTeamId));
        return convertDbToGui(gameDao.save(convertGuiToDb(game, tournamentDb, homeTeamDb, awayTeamDb)));
    }

    public GameGuiEntity update(GameGuiEntity game) throws VeikkausServiceException {
        Long gameId = game.getId();
        gameDao.findById(gameId).orElseThrow(() -> new VeikkausNotFoundException(Game.class, gameId));
        Long tournamentId = game.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(tournamentId).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, tournamentId));
        Long homeTeamId = game.getHomeTeam().getId();
        TournamentTeam homeTeamDb = tournamentTeamDao.findById(homeTeamId).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, homeTeamId));
        Long awayTeamId = game.getAwayTeam().getId();
        TournamentTeam awayTeamDb = tournamentTeamDao.findById(awayTeamId).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, awayTeamId));
        return convertDbToGui(gameDao.save(convertGuiToDb(game, tournamentDb, homeTeamDb, awayTeamDb)));
    }

    public void delete(Long id) throws VeikkausServiceException {
        Game game = gameDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Game.class, id));
        gameDao.delete(game);
    }

    protected static GameGuiEntity convertDbToGui(Game db) {
        GameGuiEntity ge = new GameGuiEntity();

        ge.setId(db.getId());
        ge.setTournament(TournamentService.convertDbToGui(db.getTournament()));
        ge.setHomeTeam(TournamentTeamService.convertDbToGui(db.getHomeTeam()));
        ge.setAwayTeam(TournamentTeamService.convertDbToGui(db.getAwayTeam()));
        ge.setHomeScore(db.getHomeScore());
        ge.setAwayScore(db.getAwayScore());
        ge.setGameDate(VeikkausUtil.getDateAsString(db.getGameDate()));
        return ge;
    }

    protected static Game convertGuiToDb(GameGuiEntity ge, Tournament tournamentDb, TournamentTeam homeTeamDb, TournamentTeam awayTeamDb) throws VeikkausConversionException {
        Game db = new Game();
        db.setId(ge.getId());
        db.setTournament(tournamentDb);
        db.setHomeTeam(homeTeamDb);
        db.setAwayTeam(awayTeamDb);
        db.setHomeScore(ge.getHomeScore());
        db.setAwayScore(ge.getAwayScore());
        try {
            db.setGameDate(VeikkausUtil.getStringAsDate(ge.getGameDate()));
        } catch (ParseException pe) {
            throw new VeikkausConversionException("Error while parsing date: " + ge.getGameDate(), pe);
        }
        return db;
    }

}
