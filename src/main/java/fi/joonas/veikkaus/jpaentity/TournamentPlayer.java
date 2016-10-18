package fi.joonas.veikkaus.jpaentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class TournamentPlayer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Player player;
    
    private int goals;
    
    @ManyToOne
    private TournamentTeam tournamentTeam;
    
    public TournamentPlayer() {}
    
    public TournamentPlayer(Long id) {
    	this.id = id;
    }

	public TournamentPlayer(Player player, int goals, TournamentTeam tournamentTeam) {
		super();
		this.player = player;
		this.goals = goals;
		this.tournamentTeam = tournamentTeam;
	}
    
	public Long getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public TournamentTeam getTournamentTeam() {
		return tournamentTeam;
	}

	public void setTournamentTeam(TournamentTeam tournamentTeam) {
		this.tournamentTeam = tournamentTeam;
	}

}

