package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.Bet;

public interface BetDao extends CrudRepository<Bet, Long> {}
