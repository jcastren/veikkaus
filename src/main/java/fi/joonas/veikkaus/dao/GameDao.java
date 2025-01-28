package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Game;
import fi.joonas.veikkaus.jpaentity.Tournament;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameDao extends CrudRepository<Game, Long> {
    List<Game> findByTournament(Tournament tournament);

    List<Game> findByTournamentOrderByGameDate(Tournament tournament);
}
