package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Bet;
import org.springframework.data.repository.CrudRepository;

public interface BetDao extends CrudRepository<Bet, Long> {
}
