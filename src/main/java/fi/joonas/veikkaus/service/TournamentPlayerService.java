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

    public Long insert(TournamentPlayerGuiEntity tournamentPlayerGuiEntity) throws VeikkausServiceException {

        String tournamentTeamId = tournamentPlayerGuiEntity.getTournamentTeam().getId();
        Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId));
        if (!tournamentTeamDb.isPresent()) {
            throw new VeikkausServiceException(
                    "Tournament team with id: " + tournamentTeamId + " wasn't found, insert failed");
        }

        String playerId = tournamentPlayerGuiEntity.getPlayer().getId();
        Optional<Player> playerDb = playerDao.findById(Long.valueOf(playerId));
        if (!playerDb.isPresent()) {
            throw new VeikkausServiceException("Player with id: " + playerId + " wasn't found, insert failed");
        }

        tournamentPlayerGuiEntity.setTournamentTeam(tournamentTeamDb.get().toGuiEntity());
        tournamentPlayerGuiEntity.setPlayer(playerDb.get().toGuiEntity());

        return tournamentPlayerDao.save(tournamentPlayerGuiEntity.toDbEntity()).getId();
    }

    public Long modify(TournamentPlayerGuiEntity tournamentPlayerGuiEntity) throws VeikkausServiceException {

        String id = tournamentPlayerGuiEntity.getId();
        Optional<TournamentPlayer> tournamentPlayerDb = tournamentPlayerDao.findById(Long.valueOf(id));
        if (!tournamentPlayerDb.isPresent()) {
            throw new VeikkausServiceException("TournamentPlayer with id: " + id + " wasn't found, modify failed");
        }

        String tournamentTeamId = tournamentPlayerGuiEntity.getTournamentTeam().getId();
        Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId));
        if (!tournamentTeamDb.isPresent()) {
            throw new VeikkausServiceException("Tournament team with id: " + id + " wasn't found, modify failed");
        }

        String playerId = tournamentPlayerGuiEntity.getPlayer().getId();
        Optional<Player> playerDb = playerDao.findById(Long.valueOf(playerId));
        if (!playerDb.isPresent()) {
            throw new VeikkausServiceException("Player with id: " + id + " wasn't found, modify failed");
        }

        tournamentPlayerGuiEntity.setTournamentTeam(tournamentTeamDb.get().toGuiEntity());
        tournamentPlayerGuiEntity.setPlayer(playerDb.get().toGuiEntity());

        return tournamentPlayerDao.save(tournamentPlayerGuiEntity.toDbEntity()).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {

        boolean succeed;
        tournamentPlayerDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    public List<TournamentPlayerGuiEntity> findAllTournamentPlayers() {

        List<TournamentPlayerGuiEntity> guiEntityList = new ArrayList<>();
        List<TournamentPlayer> dbTournamentPlayers = ImmutableList.copyOf(tournamentPlayerDao.findAll());

        for (TournamentPlayer dbTournamentPlayer : dbTournamentPlayers) {
            guiEntityList.add(dbTournamentPlayer.toGuiEntity());
        }

        return guiEntityList;
    }

    /**
     * @param id
     * @return
     */
    public TournamentPlayerGuiEntity findOneTournamentPlayer(String id) {

        return tournamentPlayerDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}