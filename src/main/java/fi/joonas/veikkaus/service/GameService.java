package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.TournamentDao;
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
    private final TournamentService tournamentService;
    private final TournamentTeamService tournamentTeamService;

    @Autowired
    public GameService(GameDao gameDao, TournamentDao tournamentDao, TournamentService tournamentService, TournamentTeamService tournamentTeamService) {
        this.gameDao = gameDao;
        this.tournamentDao = tournamentDao;
        this.tournamentService = tournamentService;
        this.tournamentTeamService = tournamentTeamService;
    }

    public List<GameGuiEntity> findAllGames() {
        List<GameGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(gameDao.findAll()).forEach(game -> geList.add(convertDbToGui(game)));
        return geList;
    }

    public GameGuiEntity findOneGame(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public List<GameGuiEntity> findTournamentGames(Long tournamentId) {
        Tournament tournamentDb = tournamentDao.findById(tournamentId).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, tournamentId));
        List<GameGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(gameDao.findByTournament(tournamentDb)).forEach(tournamentGame -> geList.add(convertDbToGui(tournamentGame)));
        return geList;
    }

    public GameGuiEntity insert(GameGuiEntity game) throws VeikkausServiceException {
        return save(game);
    }

    public GameGuiEntity update(GameGuiEntity game) throws VeikkausServiceException {
        getFromDb(game.getId());
        return save(game);
    }

    public void delete(Long id) throws VeikkausServiceException {
        gameDao.delete(getFromDb(id));
    }

    public Game getFromDb(Long id) {
        return gameDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Game.class, id));
    }

    private GameGuiEntity save(GameGuiEntity game) {
        Tournament tournamentDb = tournamentService.getFromDb(game.getTournament().getId());
        TournamentTeam homeTeamDb = tournamentTeamService.getFromDb(game.getHomeTeam().getId());
        TournamentTeam awayTeamDb = tournamentTeamService.getFromDb(game.getAwayTeam().getId());
        return convertDbToGui(gameDao.save(convertGuiToDb(game, tournamentDb, homeTeamDb, awayTeamDb)));
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
