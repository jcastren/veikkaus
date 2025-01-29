package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.jpaentity.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Tournament
 *
 * @author joona
 */
@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentDao tournamentDao;

    public Long insert(TournamentGuiEntity tournament) {

        return tournamentDao.save(tournament.toDbEntity()).getId();
    }

    public Long modify(TournamentGuiEntity tournament) {

        return tournamentDao.save(tournament.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        tournamentDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<TournamentGuiEntity> findAllTournaments() {

        List<TournamentGuiEntity> tournamentGuiEntityList = new ArrayList<>();
        tournamentDao.findAll().forEach(dbTournament -> tournamentGuiEntityList.add(dbTournament.toGuiEntity()));
        return tournamentGuiEntityList;
    }

    public TournamentGuiEntity findOneTournament(String id) {

        return tournamentDao.findById(Long.valueOf(id))
                .map(Tournament::toGuiEntity)
                .orElse(TournamentGuiEntity.builder().build());
    }
}