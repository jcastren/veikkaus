package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Scorer;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScorerService {

    private final ScorerDao scorerDao;
    private final TournamentPlayerDao tournamentPlayerDao;
    private final GameDao gameDao;

    @Autowired
    public ScorerService(ScorerDao scorerDao, TournamentPlayerDao tournamentPlayerDao, GameDao gameDao) {
        this.scorerDao = scorerDao;
        this.tournamentPlayerDao = tournamentPlayerDao;
        this.gameDao = gameDao;
    }

    public List<ScorerGuiEntity> findAllScorers() {
        List<ScorerGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(scorerDao.findAll()).forEach(scorer -> geList.add(convertDbToGui(scorer)));
        return geList;
    }

    public ScorerGuiEntity insert(ScorerGuiEntity scorer) throws VeikkausServiceException {
        return save(scorer);
    }

    public ScorerGuiEntity update(ScorerGuiEntity scorer) {
        getFromDb(scorer.getId());
        return save(scorer);
    }

    public void delete(Long id) {
        scorerDao.delete(getFromDb(id));
    }

    private Scorer getFromDb(Long id) {
        return scorerDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Scorer.class, id));
    }

    private ScorerGuiEntity save(ScorerGuiEntity scorer) {
        Long tournamentPlayerId = scorer.getTournamentPlayer().getId();
        TournamentPlayer tournamentPlayerDb = tournamentPlayerDao.findById(tournamentPlayerId).orElseThrow(() -> new VeikkausNotFoundException(TournamentPlayer.class, tournamentPlayerId));
        Long gameId = scorer.getGame().getId();
        Game gameDb = gameDao.findById(gameId).orElseThrow(() -> new VeikkausNotFoundException(Game.class, gameId));
        return convertDbToGui(scorerDao.save(convertGuiToDb(scorer, tournamentPlayerDb, gameDb)));
    }

    protected static ScorerGuiEntity convertDbToGui(Scorer db) {
        ScorerGuiEntity ge = new ScorerGuiEntity();

        ge.setId(db.getId());
        ge.setTournamentPlayer(TournamentPlayerService.convertDbToGui(db.getTournamentPlayer()));
        ge.setGame(GameService.convertDbToGui(db.getGame()));
        return ge;
    }

    protected static Scorer convertGuiToDb(ScorerGuiEntity ge, TournamentPlayer tournamentPlayerDb, Game gameDb) {
        Scorer db = new Scorer();
        db.setId(ge.getId());
        db.setTournamentPlayer(tournamentPlayerDb);
        db.setGame(gameDb);
        return db;
    }
}