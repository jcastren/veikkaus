package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerDao extends CrudRepository<Player, Long> {

}
