package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.BetResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BetResultDao extends CrudRepository<BetResult, Long> {
    List<BetResult> findByBet(Bet bet);
}
