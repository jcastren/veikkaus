package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
