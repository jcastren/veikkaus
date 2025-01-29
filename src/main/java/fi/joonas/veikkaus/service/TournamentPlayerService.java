package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentPlayerService {

    private final TournamentPlayerDao tournamentPlayerDao;

    private final TournamentTeamDao tournamentTeamDao;

    private final PlayerDao playerDao;

    public Long insert(TournamentPlayerGuiEntity tournamentPlayerGuiEntity) throws VeikkausServiceException {

        String tournamentTeamId = tournamentPlayerGuiEntity.getTournamentTeam().getId();
        TournamentTeam tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId))
                .orElseThrow(() -> new VeikkausServiceException(
                        "Tournament team with id: %s wasn't found, insert failed".formatted(tournamentTeamId)));
        String playerId = tournamentPlayerGuiEntity.getPlayer().getId();
        Player playerDb = playerDao.findById(Long.valueOf(playerId))
                .orElseThrow(() -> new VeikkausServiceException("Player with id: %s wasn't found, insert failed".formatted(playerId)));

        tournamentPlayerGuiEntity.setTournamentTeam(tournamentTeamDb.toGuiEntity());
        tournamentPlayerGuiEntity.setPlayer(playerDb.toGuiEntity());

        return tournamentPlayerDao.save(tournamentPlayerGuiEntity.toDbEntity()).getId();
    }

    public Long modify(TournamentPlayerGuiEntity tournamentPlayerGuiEntity) throws VeikkausServiceException {

        String id = tournamentPlayerGuiEntity.getId();
        tournamentPlayerDao.findById(Long.valueOf(id))
                .orElseThrow(() -> new VeikkausServiceException("TournamentPlayer with id: %s wasn't found, modify failed".formatted(id)));

        String tournamentTeamId = tournamentPlayerGuiEntity.getTournamentTeam().getId();
        TournamentTeam tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId))
                .orElseThrow(() -> new VeikkausServiceException("Tournament team with id: %s wasn't found, modify failed".formatted(tournamentTeamId)));

        String playerId = tournamentPlayerGuiEntity.getPlayer().getId();
        Player playerDb = playerDao.findById(Long.valueOf(playerId))
                .orElseThrow(() -> new VeikkausServiceException("Player with id: %s wasn't found, modify failed".formatted(playerId)));

        tournamentPlayerGuiEntity.setTournamentTeam(tournamentTeamDb.toGuiEntity());
        tournamentPlayerGuiEntity.setPlayer(playerDb.toGuiEntity());

        return tournamentPlayerDao.save(tournamentPlayerGuiEntity.toDbEntity()).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {

        tournamentPlayerDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<TournamentPlayerGuiEntity> findAllTournamentPlayers() {

        List<TournamentPlayerGuiEntity> guiEntityList = new ArrayList<>();
        tournamentPlayerDao.findAll().forEach(tournamentPlayer -> guiEntityList.add(tournamentPlayer.toGuiEntity()));
        return guiEntityList;
    }

    /**
     * @param id
     * @return
     */
    public TournamentPlayerGuiEntity findOneTournamentPlayer(String id) {

        return tournamentPlayerDao.findById(Long.valueOf(id))
                .map(TournamentPlayer::toGuiEntity)
                .orElse(TournamentPlayerGuiEntity.builder().build());
    }

}