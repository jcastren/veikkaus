package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.BetResult;

public interface BetResultDao extends CrudRepository<BetResult, Long> {

}
