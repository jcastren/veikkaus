package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.Tournament;

public interface TournamentDao extends CrudRepository<Tournament, Long> {

}
