package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.Team;

public interface TeamDao extends CrudRepository<Team, Long> {

}
