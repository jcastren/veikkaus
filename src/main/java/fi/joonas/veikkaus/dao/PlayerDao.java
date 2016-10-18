package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.Player;

public interface PlayerDao extends CrudRepository<Player, Long> {

}
