package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
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
    private final TournamentTeamService tournamentTeamService;
    private final PlayerService playerService;

    @Autowired
    public TournamentPlayerService(TournamentPlayerDao tournamentPlayerDao, TournamentTeamService tournamentTeamService, PlayerService playerService) {
        this.tournamentPlayerDao = tournamentPlayerDao;
        this.tournamentTeamService = tournamentTeamService;
        this.playerService = playerService;
    }

    public List<TournamentPlayerGuiEntity> findAllTournamentPlayers() {
        List<TournamentPlayerGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentPlayerDao.findAll()).forEach(tournamentPlayer -> geList.add(convertDbToGui(tournamentPlayer)));
        return geList;
    }

    public TournamentPlayerGuiEntity findOneTournamentPlayer(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public TournamentPlayerGuiEntity insert(TournamentPlayerGuiEntity tournamentPlayer) {
        return save(tournamentPlayer);
    }

    public TournamentPlayerGuiEntity update(TournamentPlayerGuiEntity tournamentPlayer) {
        getFromDb(tournamentPlayer.getId());
        return save(tournamentPlayer);
    }

    public void delete(Long id) throws VeikkausServiceException {
        tournamentPlayerDao.delete(getFromDb(id));
    }

    private TournamentPlayer getFromDb(Long id) {
        return tournamentPlayerDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(TournamentPlayer.class, id));
    }

    private TournamentPlayerGuiEntity save(TournamentPlayerGuiEntity tournamentPlayer) {
        TournamentTeam tournamentTeamDb = tournamentTeamService.getFromDb(tournamentPlayer.getTournamentTeam().getId());
        Player playerDb = playerService.getFromDb(tournamentPlayer.getPlayer().getId());
        return convertDbToGui(tournamentPlayerDao.save(convertGuiToDb(tournamentPlayer, tournamentTeamDb, playerDb)));
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