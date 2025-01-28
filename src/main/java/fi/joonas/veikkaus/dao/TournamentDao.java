package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Tournament;
import org.springframework.data.repository.CrudRepository;

public interface TournamentDao extends CrudRepository<Tournament, Long> {

}
