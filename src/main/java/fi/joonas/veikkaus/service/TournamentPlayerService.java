package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentPlayerService {

    private final TournamentPlayerDao tournamentPlayerDao;
    private final TournamentTeamDao tournamentTeamDao;
    private final PlayerDao playerDao;

    @Autowired
    public TournamentPlayerService(TournamentPlayerDao tournamentPlayerDao, TournamentTeamDao tournamentTeamDao, PlayerDao playerDao) {
        this.tournamentPlayerDao = tournamentPlayerDao;
        this.tournamentTeamDao = tournamentTeamDao;
        this.playerDao = playerDao;
    }

    public List<TournamentPlayerGuiEntity> findAllTournamentPlayers() {
        List<TournamentPlayerGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentPlayerDao.findAll()).forEach(tournamentPlayer -> geList.add(convertDbToGui(tournamentPlayer)));
        return geList;
    }

    public TournamentPlayerGuiEntity findOneTournamentPlayer(Long id) {
        TournamentPlayer db = tournamentPlayerDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(TournamentPlayer.class, id));
        return convertDbToGui(db);
    }

    public TournamentPlayerGuiEntity insert(TournamentPlayerGuiEntity tournamentPlayer) {
        Long tournamentTeamId = tournamentPlayer.getTournamentTeam().getId();
        TournamentTeam tournamentTeamDb = tournamentTeamDao.findById(tournamentTeamId).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, tournamentTeamId));
        Long playerId = tournamentPlayer.getPlayer().getId();
        Player playerDb = playerDao.findById(playerId).orElseThrow(() -> new VeikkausNotFoundException(Player.class, playerId));
        return convertDbToGui(tournamentPlayerDao.save(convertGuiToDb(tournamentPlayer, tournamentTeamDb, playerDb)));
    }

    public TournamentPlayerGuiEntity update(TournamentPlayerGuiEntity tournamentPlayer) {
        Long tournamentPlayerId = tournamentPlayer.getId();
        tournamentPlayerDao.findById(tournamentPlayerId).orElseThrow(() -> new VeikkausNotFoundException(TournamentPlayer.class, tournamentPlayerId));
        Long tournamentTeamId = tournamentPlayer.getTournamentTeam().getId();
        TournamentTeam tournamentTeamDb = tournamentTeamDao.findById(tournamentTeamId).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, tournamentTeamId));
        Long playerId = tournamentPlayer.getPlayer().getId();
        Player playerDb = playerDao.findById(playerId).orElseThrow(() -> new VeikkausNotFoundException(Player.class, playerId));
        return convertDbToGui(tournamentPlayerDao.save(convertGuiToDb(tournamentPlayer, tournamentTeamDb, playerDb)));
    }

    public void delete(Long id) throws VeikkausServiceException {
        TournamentPlayer tournamentPlayer = tournamentPlayerDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(TournamentPlayer.class, id));
        tournamentPlayerDao.delete(tournamentPlayer);
    }

    protected static TournamentPlayerGuiEntity convertDbToGui(TournamentPlayer db) {
        TournamentPlayerGuiEntity ge = new TournamentPlayerGuiEntity();

        ge.setId(db.getId());
        ge.setTournamentTeam(TournamentTeamService.convertDbToGui(db.getTournamentTeam()));
        ge.setPlayer(PlayerService.convertDbToGui(db.getPlayer()));
        ge.setGoals(db.getGoals());
        return ge;
    }

    protected static TournamentPlayer convertGuiToDb(TournamentPlayerGuiEntity ge, TournamentTeam tournamentTeamDb, Player playerDb) {
        TournamentPlayer db = new TournamentPlayer();
        db.setId(ge.getId());
        db.setTournamentTeam(tournamentTeamDb);
        db.setPlayer(playerDb);
        db.setGoals(ge.getGoals());
        return db;
    }
}