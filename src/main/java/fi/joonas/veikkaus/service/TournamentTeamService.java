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

    protected static TournamentTeam convertGuiToDb(TournamentTeamGuiEntity tournamentTeamGuiEntity) {

        TournamentTeam db = new TournamentTeam();

        if (tournamentTeamGuiEntity.getId() != null && !tournamentTeamGuiEntity.getId().isEmpty()) {
            db.setId(Long.valueOf(tournamentTeamGuiEntity.getId()));
        } else {
            db.setId(null);
        }
        db.setTournament(TournamentService.convertGuiToDb(tournamentTeamGuiEntity.getTournament()));
        db.setTeam(TeamService.convertGuiToDb(tournamentTeamGuiEntity.getTeam()));

        return db;
    }

    /**
     * @param tournamentTeamGuiEntity
     * @return
     */
    public Long insert(TournamentTeamGuiEntity tournamentTeamGuiEntity) throws VeikkausServiceException {

        String tournamentId = tournamentTeamGuiEntity.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException("Tournament with id: " + tournamentId + " wasn't found, insert failed");
        }

        String teamId = tournamentTeamGuiEntity.getTeam().getId();
        Optional<Team> teamDb = teamDao.findById(Long.valueOf(teamId));
        if (!teamDb.isPresent()) {
            throw new VeikkausServiceException("Team with id: " + teamId + " wasn't found, insert failed");
        } else {
        }

        tournamentTeamGuiEntity.setTournament(tournamentDb.get().toGuiEntity());
        tournamentTeamGuiEntity.setTeam(teamDb.get().toGuiEntity());

        return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGuiEntity)).getId();
    }

    /**
     * @param tournamentTeamGuiEntity
     * @return
     */
    public Long modify(TournamentTeamGuiEntity tournamentTeamGuiEntity) throws VeikkausServiceException {

        String id = tournamentTeamGuiEntity.getId();
        Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(id));
        if (!tournamentTeamDb.isPresent()) {
            throw new VeikkausServiceException("TournamentTeam with id: " + id + " wasn't found, modify failed");
        }

        String tournamentId = tournamentTeamGuiEntity.getTournament().getId();
        Optional<Tournament> tournamentDb = tournamentDao.findById(Long.valueOf(tournamentId));
        if (!tournamentDb.isPresent()) {
            throw new VeikkausServiceException("Tournament with id: " + id + " wasn't found, modify failed");
        }

        String teamId = tournamentTeamGuiEntity.getTeam().getId();
        Optional<Team> teamDb = teamDao.findById(Long.valueOf(teamId));
        if (!teamDb.isPresent()) {
            throw new VeikkausServiceException("Team with id: " + id + " wasn't found, modify failed");
        }

        tournamentTeamGuiEntity.setTournament(tournamentDb.get().toGuiEntity());
        tournamentTeamGuiEntity.setTeam(teamDb.get().toGuiEntity());

        return tournamentTeamDao.save(convertGuiToDb(tournamentTeamGuiEntity)).getId();
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

        List<TournamentTeamGuiEntity> tournamentTeamGuiEntityList = new ArrayList<>();
        List<TournamentTeam> dbTournamentTeams = ImmutableList.copyOf(tournamentTeamDao.findAll());

        for (TournamentTeam dbTournamentTeam : dbTournamentTeams) {
            tournamentTeamGuiEntityList.add(dbTournamentTeam.toGuiEntity());
        }

        return tournamentTeamGuiEntityList;
    }

    public List<TournamentTeamGuiEntity> findTournamentTeamsByTournamentId(String tournamentId) {

        List<TournamentTeamGuiEntity> tournamentTeamGuiEntityList = new ArrayList<>();

        List<TournamentTeam> dbList = tournamentTeamDao.findByTournamentId(Long.valueOf(tournamentId));
        for (TournamentTeam tournamentTeam : dbList) {
            tournamentTeamGuiEntityList.add(tournamentTeam.toGuiEntity());
        }
        return tournamentTeamGuiEntityList;
    }

    /**
     * @param id
     * @return
     */
    public TournamentTeamGuiEntity findOneTournamentTeam(String id) {

        return tournamentTeamDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}