package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.User;

public interface UserDao extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
