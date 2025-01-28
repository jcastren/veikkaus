package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import org.springframework.data.repository.CrudRepository;

public interface TournamentPlayerDao extends CrudRepository<TournamentPlayer, Long> {

}
