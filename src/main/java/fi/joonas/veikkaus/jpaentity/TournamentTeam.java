package fi.joonas.veikkaus.jpaentity;

import javax.persistence.*;

@Entity
public class TournamentTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Tournament tournament;
    
    @OneToOne
    private Team team;
    
    public TournamentTeam() {}

	public TournamentTeam(Tournament tournament, Team team) {
		this.tournament = tournament;
		this.team = team;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}