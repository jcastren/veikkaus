package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentTeamService {

    private final TournamentTeamDao tournamentTeamDao;

    private final TournamentDao tournamentDao;

    private final TeamDao teamDao;

    public Long insert(TournamentTeamGuiEntity tournamentTeamGuiEntity) throws VeikkausServiceException {

        String tournamentId = tournamentTeamGuiEntity.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId))
                .orElseThrow(() -> new VeikkausServiceException("Tournament with id: %s wasn't found, insert failed".formatted(tournamentId)));

        String teamId = tournamentTeamGuiEntity.getTeam().getId();
        Team teamDb = teamDao.findById(Long.valueOf(teamId))
                .orElseThrow(() -> new VeikkausServiceException("Team with id: %s wasn't found, insert failed".formatted(teamId)));

        tournamentTeamGuiEntity.setTournament(tournamentDb.toGuiEntity());
        tournamentTeamGuiEntity.setTeam(teamDb.toGuiEntity());

        return tournamentTeamDao.save(tournamentTeamGuiEntity.toDbEntity()).getId();
    }

    public Long modify(TournamentTeamGuiEntity tournamentTeamGuiEntity) throws VeikkausServiceException {

        String id = tournamentTeamGuiEntity.getId();
        tournamentTeamDao.findById(Long.valueOf(id))
                .orElseThrow(() -> new VeikkausServiceException("TournamentTeam with id: %s wasn't found, modify failed".formatted(id)));

        String tournamentId = tournamentTeamGuiEntity.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId))
                .orElseThrow(() -> new VeikkausServiceException("Tournament with id: %s wasn't found, modify failed".formatted(tournamentId)));

        String teamId = tournamentTeamGuiEntity.getTeam().getId();
        Team teamDb = teamDao.findById(Long.valueOf(teamId))
                .orElseThrow(() -> new VeikkausServiceException("Team with id: %s wasn't found, modify failed".formatted(teamId)));

        tournamentTeamGuiEntity.setTournament(tournamentDb.toGuiEntity());
        tournamentTeamGuiEntity.setTeam(teamDb.toGuiEntity());

        return tournamentTeamDao.save(tournamentTeamGuiEntity.toDbEntity()).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {

        tournamentTeamDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<TournamentTeamGuiEntity> findAllTournamentTeams() {

        List<TournamentTeamGuiEntity> tournamentTeamGuiEntityList = new ArrayList<>();
        tournamentTeamDao.findAll().forEach(tournamentTeam -> tournamentTeamGuiEntityList.add(tournamentTeam.toGuiEntity()));
        return tournamentTeamGuiEntityList;
    }

    public List<TournamentTeamGuiEntity> findTournamentTeamsByTournamentId(String tournamentId) {

        List<TournamentTeamGuiEntity> tournamentTeamGuiEntityList = new ArrayList<>();
        tournamentTeamDao.findByTournamentId(Long.valueOf(tournamentId)).
                forEach(tournamentTeam -> tournamentTeamGuiEntityList.add(tournamentTeam.toGuiEntity()));
        return tournamentTeamGuiEntityList;
    }

    public TournamentTeamGuiEntity findOneTournamentTeam(String id) {

        return tournamentTeamDao.findById(Long.valueOf(id))
                .map(TournamentTeam::toGuiEntity)
                .orElse(TournamentTeamGuiEntity.builder().build());
    }

}