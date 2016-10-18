package fi.joonas.veikkaus.dao;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.TournamentTeam;

public interface TournamentTeamDao extends CrudRepository<TournamentTeam, Long> {

}
