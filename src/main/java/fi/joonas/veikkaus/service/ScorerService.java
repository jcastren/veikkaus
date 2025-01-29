package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Scorer;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScorerService {

    private final ScorerDao scorerDao;

    private final TournamentPlayerDao tournamentPlayerDao;

    private final GameDao gameDao;

    public List<ScorerGuiEntity> findAllScorers() {

        List<ScorerGuiEntity> scorerGuiEntityList = new ArrayList<>();
        scorerDao.findAll().forEach(dbScorer -> scorerGuiEntityList.add(dbScorer.toGuiEntity()));
        return scorerGuiEntityList;
    }

    public Long insert(String tournamentPlayerId, String gameId) throws VeikkausServiceException {

        TournamentPlayer tournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId))
                .orElseThrow(() -> new VeikkausServiceException("tournamentPlayer with id: %s wasn't found, insert failed".formatted(tournamentPlayerId)));
        Game game = gameDao.findById(Long.valueOf(gameId))
                .orElseThrow(() -> new VeikkausServiceException("game with id: %s wasn't found, insert failed".formatted(gameId)));

        return scorerDao.save(new Scorer(tournamentPlayer, game)).getId();
    }

    public Long insert(ScorerGuiEntity scorerGuiEntity) throws VeikkausServiceException, VeikkausConversionException {

        String tournamentPlayerId = scorerGuiEntity.getTournamentPlayer().getId();
        TournamentPlayer tournamentPlayerDb = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "Tournament player with id: %s wasn't found, insert failed".formatted(tournamentPlayerId)));
        String gameId = scorerGuiEntity.getGame().getId();
        Game gameDb = gameDao.findById(Long.valueOf(gameId))
                .orElseThrow(() -> new VeikkausServiceException("Game with id: %s wasn't found, insert failed".formatted(gameId)));
        scorerGuiEntity.setTournamentPlayer(tournamentPlayerDb.toGuiEntity());
        scorerGuiEntity.setGame(gameDb.toGuiEntity());

        return scorerDao.save(scorerGuiEntity.toDbEntity()).getId();
    }

    public Long modify(String id, String tournamentPlayerId, String gameId) throws VeikkausServiceException {

        TournamentPlayer tournamentPlayer = tournamentPlayerDao.findById(Long.valueOf(tournamentPlayerId))
                .orElseThrow(() -> new VeikkausServiceException("TournamentPlayer with id: %s wasn't found, modify failed".formatted(tournamentPlayerId)));

        Game game = gameDao.findById(Long.valueOf(gameId))
                .orElseThrow(() -> new VeikkausServiceException("Game with id: %s wasn't found, insert failed".formatted(gameId)));

        Scorer scorer = scorerDao.findById(Long.valueOf(id))
                .orElseThrow(() -> new VeikkausServiceException("Scorer with id: %s wasn't found, modify failed".formatted(id)));

        scorer.setTournamentPlayer(tournamentPlayer);
        scorer.setGame(game);
        return scorerDao.save(scorer).getId();
    }

    public boolean delete(String id) {

        scorerDao.deleteById(Long.valueOf(id));
        return true;
    }
}
