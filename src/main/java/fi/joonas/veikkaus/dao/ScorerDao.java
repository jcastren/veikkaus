package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.Scorer;

public interface ScorerDao extends CrudRepository<Scorer, Long> {}
