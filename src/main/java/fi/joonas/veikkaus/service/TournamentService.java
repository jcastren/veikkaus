package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.jpaentity.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Tournament
 *
 * @author jcastren
 */
@Service
public class TournamentService {

    @Autowired
    TournamentDao tournamentDao;

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
        db.setYear(Integer.parseInt(ge.getYear()));

        return db;
    }

    public Long insert(TournamentGuiEntity tournament) {
        return tournamentDao.save(convertGuiToDb(tournament)).getId();
    }

    public Long modify(TournamentGuiEntity tournament) {
        return tournamentDao.save(convertGuiToDb(tournament)).getId();
    }

    public boolean delete(String id) {
        tournamentDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<TournamentGuiEntity> findAllTournaments() {
        List<TournamentGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(tournamentDao.findAll()).forEach(tournament -> geList.add(convertDbToGui(tournament)));
        return geList;
    }

    public TournamentGuiEntity findOneTournament(String id) {
        return convertDbToGui(tournamentDao.findById(Long.valueOf(id)).get());
    }

}
