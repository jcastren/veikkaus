package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamDao extends CrudRepository<Team, Long> {

}
