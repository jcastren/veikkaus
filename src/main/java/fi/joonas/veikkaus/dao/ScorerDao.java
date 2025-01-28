package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Scorer;
import org.springframework.data.repository.CrudRepository;

public interface ScorerDao extends CrudRepository<Scorer, Long> {
}
