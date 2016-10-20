package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.Status;

public interface StatusDao extends CrudRepository<Status, Long> {

}
