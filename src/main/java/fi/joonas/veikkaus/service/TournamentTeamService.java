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
 * @author joona
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

    /**
     * @param tournamentTeamGe
     * @return
     */
    public Long insert(TournamentTeamGuiEntity tournamentTeamGe) throws VeikkausServiceException {
        String tournamentId = tournamentTeamGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException("Tournament with id: " + tournamentId + " wasn't found, insert failed");
        }

        String teamId = tournamentTeamGe.getTeam().getId();
        Optional<Team> teamDb = teamDao.findById(Long.valueOf(teamId));
        if (!teamDb.isPresent()) {
            throw new VeikkausServiceException("Team with id: " + teamId + " wasn't found, insert failed");
        } else {
        }

        tournamentTeamGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        tournamentTeamGe.setTeam(TeamService.convertDbToGui(teamDb.get()));

        return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGe)).getId();
    }

    /**
     * @param tournamentTeamGe
     * @return
     */
    public Long modify(TournamentTeamGuiEntity tournamentTeamGe) throws VeikkausServiceException {
        String id = tournamentTeamGe.getId();
        Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(id));
        if (!tournamentTeamDb.isPresent()) {
            throw new VeikkausServiceException("TournamentTeam with id: " + id + " wasn't found, modify failed");
        }

        String tournamentId = tournamentTeamGe.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException("Tournament with id: " + id + " wasn't found, modify failed");
        }

        String teamId = tournamentTeamGe.getTeam().getId();
        Optional<Team> teamDb = teamDao.findById(Long.valueOf(teamId));
        if (!teamDb.isPresent()) {
            throw new VeikkausServiceException("Team with id: " + id + " wasn't found, modify failed");
        }

        tournamentTeamGe.setTournament(TournamentService.convertDbToGui(tournamentDb.get()));
        tournamentTeamGe.setTeam(TeamService.convertDbToGui(teamDb.get()));

        return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGe)).getId();
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(String id) throws VeikkausServiceException {
        boolean succeed;
        tournamentTeamDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    /**
     * @return
     */
    public List<TournamentTeamGuiEntity> findAllTournamentTeams() {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();
        List<TournamentTeam> dbTournTeams = ImmutableList.copyOf(tournamentTeamDao.findAll());

        for (TournamentTeam dbTournTeam : dbTournTeams) {
            geList.add(convertDbToGui(dbTournTeam));
        }

        return geList;
    }

    public List<TournamentTeamGuiEntity> findTournamentTeamsByTournamentId(String tournamentId) {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();

        List<TournamentTeam> dbList = tournamentTeamDao.findByTournamentId(Long.valueOf(tournamentId));
        for (TournamentTeam tt : dbList) {
            geList.add(convertDbToGui(tt));
        }
        return geList;
    }

    /**
     * @param id
     * @return
     */
    public TournamentTeamGuiEntity findOneTournamentTeam(String id) {
        TournamentTeamGuiEntity tournGe = convertDbToGui(tournamentTeamDao.findById(Long.valueOf(id)).get());
        return tournGe;
    }

}