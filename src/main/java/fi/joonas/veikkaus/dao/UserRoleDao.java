package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.UserRole;

public interface UserRoleDao extends CrudRepository<UserRole, Long> {

}
