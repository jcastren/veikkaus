package fi.joonas.veikkaus.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.joonas.veikkaus.jpaentity.TournamentTeam;

public interface TournamentTeamDao extends CrudRepository<TournamentTeam, Long> {
	
	public List<TournamentTeam> findByTournamentId(Long tournamentId);

}
