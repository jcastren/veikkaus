package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Scorer;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScorerService {

    @Autowired
    ScorerDao scorerDao;

    @Autowired
    TournamentPlayerDao tournamentPlayerDao;

    @Autowired
    GameDao gameDao;

    protected static ScorerGuiEntity convertDbToGui(Scorer db) {
        ScorerGuiEntity ge = new ScorerGuiEntity();
        ge.setId(db.getId().toString());
        ge.setTournamentPlayer(TournamentPlayerService.convertDbToGui(db.getTournamentPlayer()));
        ge.setGame(GameService.convertDbToGui(db.getGame()));
        return ge;
    }

    protected static Scorer convertGuiToDb(ScorerGuiEntity ge) {
        Scorer db = new Scorer();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setTournamentPlayer(TournamentPlayerService.convertGuiToDb(ge.getTournamentPlayer()));
        try {
            db.setGame(GameService.convertGuiToDb(ge.getGame()));
        } catch (VeikkausConversionException vce) {
            log.error("Error converting GameGuiEntity to DbEntity", vce);
        }

        return db;
    }

    public List<ScorerGuiEntity> findAllScorers() {
        List<ScorerGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(scorerDao.findAll()).forEach(dbScorer -> geList.add(convertDbToGui(dbScorer)));
        return geList;
    }

    public Long insert(String tournamentPlayerId, String gameId) throws VeikkausServiceException {
        Optional<TournamentPlayer> tournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId));
        if (tournamentPlayer.isEmpty()) {
            throw new VeikkausServiceException("tournamentPlayer with id: %s wasn't found, insert failed".formatted(tournamentPlayerId));
        }

        Optional<Game> game = gameDao.findById(Long.valueOf(gameId));
        if (game.isEmpty()) {
            throw new VeikkausServiceException("game with id: %s wasn't found, insert failed".formatted(gameId));
        }

        return scorerDao.save(new Scorer(tournamentPlayer.get(), game.get())).getId();
    }

    public Long insert(ScorerGuiEntity scorerGe) throws VeikkausServiceException {
        String tournamentPlayerId = scorerGe.getTournamentPlayer().getId();
        Optional<TournamentPlayer> tournamentPlayerDb = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId));
        if (tournamentPlayerDb.isEmpty()) {
            throw new VeikkausServiceException(
                    "Tournament player with id: %s wasn't found, insert failed".formatted(tournamentPlayerId));
        }

        String gameId = scorerGe.getGame().getId();
        Optional<Game> gameDb = gameDao.findById(Long.valueOf(gameId));
        if (gameDb.isEmpty()) {
            throw new VeikkausServiceException("Game with id: %s wasn't found, insert failed".formatted(gameId));
        }

        scorerGe.setTournamentPlayer(TournamentPlayerService.convertDbToGui(tournamentPlayerDb.get()));
        scorerGe.setGame(GameService.convertDbToGui(gameDb.get()));

        return scorerDao.save(convertGuiToDb(scorerGe)).getId();
    }

    public Long modify(String id, String tournamentPlayerId, String gameId) throws VeikkausServiceException {
        Optional<TournamentPlayer> tournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId));
        if (tournamentPlayer.isEmpty()) {
            throw new VeikkausServiceException("TournamentPlayer with id: %s wasn't found, modify failed".formatted(tournamentPlayerId));
        }

        Optional<Game> game = gameDao.findById(Long.valueOf(gameId));
        if (game.isEmpty()) {
            throw new VeikkausServiceException("Game with id: %s wasn't found, insert failed".formatted(gameId));
        }

        Optional<Scorer> scorer = scorerDao.findById(Long.valueOf(id));
        if (scorer.isEmpty()) {
            throw new VeikkausServiceException("Scorer with id: %s wasn't found, modify failed".formatted(id));
        }

        scorer.get().setTournamentPlayer(tournamentPlayer.get());
        scorer.get().setGame(game.get());
        return scorerDao.save(scorer.get()).getId();
    }

    public boolean delete(String id) {
        scorerDao.deleteById(Long.valueOf(id));
        return true;
    }

}
