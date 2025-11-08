package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
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

@Service
public class TournamentTeamService {

    private final TournamentTeamDao tournamentTeamDao;
    private final TournamentService tournamentService;
    private final TeamService teamService;

    @Autowired
    public TournamentTeamService(TournamentTeamDao tournamentTeamDao, TournamentService tournamentService, TeamService teamService) {
        this.tournamentTeamDao = tournamentTeamDao;
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    public List<TournamentTeamGuiEntity> findAllTournamentTeams() {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentTeamDao.findAll()).forEach(tournamentTeam -> geList.add(convertDbToGui(tournamentTeam)));
        return geList;
    }

    public TournamentTeamGuiEntity findOneTournamentTeam(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public List<TournamentTeamGuiEntity> findTournamentTeamsByTournamentId(Long tournamentId) {
        List<TournamentTeamGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentTeamDao.findByTournamentId(tournamentId)).forEach(tournamentTeam -> geList.add(convertDbToGui(tournamentTeam)));
        return geList;
    }

    public TournamentTeamGuiEntity insert(TournamentTeamGuiEntity tournamentTeam) {
        return save(tournamentTeam);
    }

    public TournamentTeamGuiEntity update(TournamentTeamGuiEntity tournamentTeam) {
        getFromDb(tournamentTeam.getId());
        return save(tournamentTeam);
    }

    public void delete(Long id) {
        tournamentTeamDao.delete(getFromDb(id));
    }

    public TournamentTeam getFromDb(Long id) {
        return tournamentTeamDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(TournamentTeam.class, id));
    }

    private TournamentTeamGuiEntity save(TournamentTeamGuiEntity tournamentTeam) {
        Tournament tournamentDb = tournamentService.getFromDb(tournamentTeam.getTournament().getId());
        Team teamDb = teamService.getFromDb(tournamentTeam.getTeam().getId());
        return convertDbToGui(tournamentTeamDao.save(convertGuiToDb(tournamentTeam, tournamentDb, teamDb)));
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