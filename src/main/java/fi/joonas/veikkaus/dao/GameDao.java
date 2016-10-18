package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.Game;

public interface GameDao extends CrudRepository<Game, Long> {

}
