package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic level class for DB handling of Tournament
 *
 * @author jcastren
 */
@Service
public class TournamentTeamService {

    @Autowired
    TournamentTeamDao tournamentTeamDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    TeamDao teamDao;

    protected static TournamentTeamGuiEntity convertDbToGui(TournamentTeam db) {
        TournamentTeamGuiEntity ge = new TournamentTeamGuiEntity();

        ge.setId(db.getId().toString());
        ge.setTournament(TournamentService.convertDbToGui(db.getTournament()));
        ge.setTeam(TeamService.convertDbToGui(db.getTeam()));
        return ge;
    }

    protected static TournamentTeam convertGuiToDb(TournamentTeamGuiEntity ge) {
        TournamentTeam db = new TournamentTeam();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setTournament(TournamentService.convertGuiToDb(ge.getTournament()));
        db.setTeam(TeamService.convertGuiToDb(ge.getTeam()));

        return db;
    }

    public Long insert(TournamentTeamGuiEntity tournamentTeamGe) throws VeikkausServiceException {
        String tournamentId = tournamentTeamGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (tournamentDb.isEmpty()) {
            throw new VeikkausServiceException("Tournament with id: %s wasn't found, insert failed".formatted(tournamentId));
        }

        String teamId = tournamentTeamGe.getTeam().getId();
        Optional<Team> teamDb = teamDao.findById(Long.valueOf(teamId));
        if (teamDb.isEmpty()) {
            throw new VeikkausServiceException("Team with id: %s wasn't found, insert failed".formatted(teamId));
        }

        tournamentTeamGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        tournamentTeamGe.setTeam(TeamService.convertDbToGui(teamDb.get()));

        return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGe)).getId();
    }

    public Long modify(TournamentTeamGuiEntity tournamentTeamGe) throws VeikkausServiceException {
        String id = tournamentTeamGe.getId();
        Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(id));
        if (tournamentTeamDb.isEmpty()) {
            throw new VeikkausServiceException("TournamentTeam with id: %s wasn't found, modify failed".formatted(id));
        }

        String tournamentId = tournamentTeamGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (tournamentDb.isEmpty()) {
            throw new VeikkausServiceException("Tournament with id: %s wasn't found, modify failed".formatted(tournamentId));
        }

        String teamId = tournamentTeamGe.getTeam().getId();
        Optional<Team> teamDb = teamDao.findById(Long.valueOf(teamId));
        if (teamDb.isEmpty()) {
            throw new VeikkausServiceException("Team with id: %s wasn't found, modify failed".formatted(teamId));
        }

        tournamentTeamGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        tournamentTeamGe.setTeam(TeamService.convertDbToGui(teamDb.get()));

        return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGe)).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {
        tournamentTeamDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<TournamentTeamGuiEntity> findAllTournamentTeams() {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentTeamDao.findAll()).forEach(tournamentTeam -> geList.add(convertDbToGui(tournamentTeam)));
        return geList;
    }

    public List<TournamentTeamGuiEntity> findTournamentTeamsByTournamentId(String tournamentId) {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();
        tournamentTeamDao.findByTournamentId(Long.valueOf(tournamentId)).forEach(tournamentTeam -> geList.add(convertDbToGui(tournamentTeam)));
        return geList;
    }

    public TournamentTeamGuiEntity findOneTournamentTeam(String id) {
        return convertDbToGui(tournamentTeamDao.findById(Long.valueOf(id)).get());
    }

}