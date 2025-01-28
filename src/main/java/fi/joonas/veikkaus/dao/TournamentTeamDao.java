package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TournamentTeamDao extends CrudRepository<TournamentTeam, Long> {

    public List<TournamentTeam> findByTournamentId(Long tournamentId);

}
