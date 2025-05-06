package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentPlayerService {

    @Autowired
    TournamentPlayerDao tournamentPlayerDao;

    @Autowired
    TournamentTeamDao tournamentTeamDao;

    @Autowired
    PlayerDao playerDao;

    protected static TournamentPlayerGuiEntity convertDbToGui(TournamentPlayer db) {
        TournamentPlayerGuiEntity ge = new TournamentPlayerGuiEntity();

        ge.setId(db.getId().toString());
        ge.setTournamentTeam(TournamentTeamService.convertDbToGui(db.getTournamentTeam()));
        ge.setPlayer(PlayerService.convertDbToGui(db.getPlayer()));
        ge.setGoals(Integer.valueOf(db.getGoals()).toString());
        return ge;
    }

    protected static TournamentPlayer convertGuiToDb(TournamentPlayerGuiEntity ge) {
        TournamentPlayer db = new TournamentPlayer();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setTournamentTeam(TournamentTeamService.convertGuiToDb(ge.getTournamentTeam()));
        db.setPlayer(PlayerService.convertGuiToDb(ge.getPlayer()));
        db.setGoals(Integer.parseInt(ge.getGoals()));

        return db;
    }

    public Long insert(TournamentPlayerGuiEntity tournamentPlayerGe) throws VeikkausServiceException {
        String tournamentTeamId = tournamentPlayerGe.getTournamentTeam().getId();
        Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId));
        if (tournamentTeamDb.isEmpty()) {
            throw new VeikkausServiceException(
                    "Tournament team with id: %s wasn't found, insert failed".formatted(tournamentTeamId));
        }

        String playerId = tournamentPlayerGe.getPlayer().getId();
        Optional<Player> playerDb = playerDao.findById(Long.valueOf(playerId));
        if (playerDb.isEmpty()) {
            throw new VeikkausServiceException("Player with id: %s wasn't found, insert failed".formatted(playerId));
        }

        tournamentPlayerGe.setTournamentTeam(TournamentTeamService.convertDbToGui(tournamentTeamDb.get()));
        tournamentPlayerGe.setPlayer(PlayerService.convertDbToGui(playerDb.get()));

        return tournamentPlayerDao.save(convertGuiToDb(tournamentPlayerGe)).getId();
    }

    public Long modify(TournamentPlayerGuiEntity tournamentPlayerGe) throws VeikkausServiceException {
        String id = tournamentPlayerGe.getId();
        Optional<TournamentPlayer> tournamentPlayerDb = tournamentPlayerDao.findById(Long.valueOf(id));
        if (tournamentPlayerDb.isEmpty()) {
            throw new VeikkausServiceException("TournamentPlayer with id: %s wasn't found, modify failed".formatted(id));
        }

        String tournamentTeamId = tournamentPlayerGe.getTournamentTeam().getId();
        Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId));
        if (tournamentTeamDb.isEmpty()) {
            throw new VeikkausServiceException("Tournament team with id: %s wasn't found, modify failed".formatted(id));
        }

        String playerId = tournamentPlayerGe.getPlayer().getId();
        Optional<Player> playerDb = playerDao.findById(Long.valueOf(playerId));
        if (playerDb.isEmpty()) {
            throw new VeikkausServiceException("Player with id: %s wasn't found, modify failed".formatted(id));
        }

        tournamentPlayerGe.setTournamentTeam(TournamentTeamService.convertDbToGui(tournamentTeamDb.get()));
        tournamentPlayerGe.setPlayer(PlayerService.convertDbToGui(playerDb.get()));

        return tournamentPlayerDao.save(convertGuiToDb(tournamentPlayerGe)).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {
        tournamentPlayerDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<TournamentPlayerGuiEntity> findAllTournamentPlayers() {
        List<TournamentPlayerGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentPlayerDao.findAll()).forEach(tournamentPlayer -> geList.add(convertDbToGui(tournamentPlayer)));
        return geList;
    }

    public TournamentPlayerGuiEntity findOneTournamentPlayer(String id) {
        return convertDbToGui(tournamentPlayerDao.findById(Long.valueOf(id)).get());
    }

}