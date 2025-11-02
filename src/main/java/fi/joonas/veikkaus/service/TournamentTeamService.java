package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of tournament teams
 *
 * @author jcastren
 */
@Service
public class TournamentTeamService {

    private final TournamentTeamDao tournamentTeamDao;
    private final TournamentDao tournamentDao;
    private final TeamDao teamDao;

    @Autowired
    public TournamentTeamService(TournamentTeamDao tournamentTeamDao, TournamentDao tournamentDao, TeamDao teamDao) {
        this.tournamentTeamDao = tournamentTeamDao;
        this.tournamentDao = tournamentDao;
        this.teamDao = teamDao;
    }

    public List<TournamentTeamGuiEntity> findAllTournamentTeams() {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentTeamDao.findAll()).forEach(tournamentTeam -> geList.add(convertDbToGui(tournamentTeam)));
        return geList;
    }

    public TournamentTeamGuiEntity findOneTournamentTeam(Long id) {
        TournamentTeam db = tournamentTeamDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, id));
        return convertDbToGui(db);
    }

    public List<TournamentTeamGuiEntity> findTournamentTeamsByTournamentId(Long tournamentId) {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();
        tournamentTeamDao.findByTournamentId(tournamentId).forEach(tournamentTeam -> geList.add(convertDbToGui(tournamentTeam)));
        return geList;
    }

    public TournamentTeamGuiEntity insert(TournamentTeamGuiEntity tournamentTeam) {
        Long tournamentId = tournamentTeam.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(tournamentId).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, tournamentId));
        Long teamId = tournamentTeam.getTeam().getId();
        Team teamDb = teamDao.findById(teamId).orElseThrow(() -> new VeikkausNotFoundException(Team.class, teamId));
        return convertDbToGui(tournamentTeamDao.save(convertGuiToDb(tournamentTeam, tournamentDb, teamDb)));
    }

    public TournamentTeamGuiEntity update(TournamentTeamGuiEntity tournamentTeam) {
        Long tournamentTeamId = tournamentTeam.getId();
        tournamentTeamDao.findById(Long.valueOf(tournamentTeamId)).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, tournamentTeamId));
        Long tournamentId = tournamentTeam.getTournament().getId();
        Tournament tournamentDb = tournamentDao.findById(tournamentId).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, tournamentId));
        Long teamId = tournamentTeam.getTeam().getId();
        Team teamDb = teamDao.findById(teamId).orElseThrow(() -> new VeikkausNotFoundException(Team.class, teamId));
        return convertDbToGui(tournamentTeamDao.save(convertGuiToDb(tournamentTeam, tournamentDb, teamDb)));
    }

    public void delete(Long id) {
        TournamentTeam tournamentTeam = tournamentTeamDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, id));
        tournamentTeamDao.delete(tournamentTeam);
    }

    protected static TournamentTeamGuiEntity convertDbToGui(TournamentTeam db) {
        TournamentTeamGuiEntity ge = new TournamentTeamGuiEntity();

        ge.setId(db.getId());
        ge.setTournament(TournamentService.convertDbToGui(db.getTournament()));
        ge.setTeam(TeamService.convertDbToGui(db.getTeam()));
        return ge;
    }

    protected static TournamentTeam convertGuiToDb(TournamentTeamGuiEntity ge, Tournament tournamentDb, Team teamDb) {
        TournamentTeam db = new TournamentTeam();
        db.setId(ge.getId());
        db.setTournament(tournamentDb);
        db.setTeam(teamDb);
        return db;
    }

}