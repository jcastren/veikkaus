package fi.joonas.veikkaus.jpaentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class TournamentTeam {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Team team;
    
    @OneToOne
    private Tournament tournament;
    
    public TournamentTeam() {}
    
    public TournamentTeam(Long id) {
    	this.id = id;
    }

	public TournamentTeam(Team team, Tournament tournament) {
		super();
		this.team = team;
		this.tournament = tournament;
	}

	public Long getId() {
		return id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}



}

