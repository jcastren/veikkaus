package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleDao extends CrudRepository<UserRole, Long> {

}
