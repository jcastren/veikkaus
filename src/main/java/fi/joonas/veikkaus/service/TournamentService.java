package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.exception.VeikkausBadRequestException;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import fi.joonas.veikkaus.jpaentity.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of tournaments
 *
 * @author jcastren
 */
@Service
public class TournamentService {

    private final TournamentDao tournamentDao;

    @Autowired
    public TournamentService(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    public List<TournamentGuiEntity> findAllTournaments() {
        List<TournamentGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentDao.findAll()).forEach(tournament -> geList.add(convertDbToGui(tournament)));
        return geList;
    }

    public TournamentGuiEntity findOneTournament(Long id) {
        Tournament db = tournamentDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, id));
        return convertDbToGui(db);
    }

    public TournamentGuiEntity insert(TournamentGuiEntity tournament) {
        return convertDbToGui(tournamentDao.save(convertGuiToDb(tournament)));
    }

    public TournamentGuiEntity update(TournamentGuiEntity tournament) {
        tournamentDao.findById(Long.parseLong(tournament.getId())).orElseThrow(() -> new VeikkausNotFoundException(Team.class, tournament.getId()));
        return convertDbToGui(tournamentDao.save(convertGuiToDb(tournament)));
    }

    public void delete(Long id) {
        Tournament tournament = tournamentDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Tournament.class, id));
        tournamentDao.delete(tournament);
    }

    protected static TournamentGuiEntity convertDbToGui(Tournament db) {
        TournamentGuiEntity ge = new TournamentGuiEntity();

        ge.setId(db.getId().toString());
        ge.setName(db.getName());
        ge.setYear(Integer.valueOf(db.getYear()).toString());

        return ge;
    }

    protected static Tournament convertGuiToDb(TournamentGuiEntity ge) {
        Tournament db = new Tournament();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setName(ge.getName());
        try {
            db.setYear(Integer.parseInt(ge.getYear()));
        } catch (NumberFormatException e) {
            throw new VeikkausBadRequestException(Tournament.class, ge.getId() != null ? ge.getId() : "", "Year must be a number");
        }

        return db;
    }
}
