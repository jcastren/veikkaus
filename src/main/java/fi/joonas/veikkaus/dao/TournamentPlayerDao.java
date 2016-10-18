package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.TournamentPlayer;

public interface TournamentPlayerDao extends CrudRepository<TournamentPlayer, Long> {

}
