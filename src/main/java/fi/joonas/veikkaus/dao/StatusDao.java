package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusDao extends CrudRepository<Status, Long> {

}
