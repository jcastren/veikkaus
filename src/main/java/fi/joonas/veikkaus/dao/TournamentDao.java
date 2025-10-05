package fi.joonas.veikkaus.dao;

import fi.joonas.veikkaus.jpaentity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentDao extends JpaRepository<Tournament, Long> {

}
