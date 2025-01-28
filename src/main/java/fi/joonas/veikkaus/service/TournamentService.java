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
 * @author joona
 */
@Service
public class TournamentService {

    @Autowired
    TournamentDao tournamentDao;

    protected static Tournament convertGuiToDb(TournamentGuiEntity ge) {

        Tournament db = new Tournament();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setName(ge.getName());
        db.setYear(Integer.valueOf(ge.getYear()));

        return db;
    }

    /**
     * @param tournament
     * @return
     */
    public Long insert(TournamentGuiEntity tournament) {

        return tournamentDao.save(convertGuiToDb(tournament)).getId();
    }

    /**
     * @param tournament
     * @return
     */
    public Long modify(TournamentGuiEntity tournament) {

        return tournamentDao.save(convertGuiToDb(tournament)).getId();
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(String id) {

        boolean succeed;
        tournamentDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    /**
     * @return
     */
    public List<TournamentGuiEntity> findAllTournaments() {

        List<TournamentGuiEntity> tournamentGuiEntityList = new ArrayList<>();
        List<Tournament> dbTournaments = ImmutableList.copyOf(tournamentDao.findAll());

        for (Tournament dbTournament : dbTournaments) {
            tournamentGuiEntityList.add(dbTournament.toGuiEntity());
        }

        return tournamentGuiEntityList;
    }

    /**
     * @param id
     * @return
     */
    public TournamentGuiEntity findOneTournament(String id) {

        return tournamentDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}
