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
    
    @ManyToOne
    private TournamentTeam tournamentTeam;
    
    @OneToOne
    private Player player;
    
    private int goals;
    
    public TournamentPlayer() {}

	public TournamentPlayer(TournamentTeam tournamentTeam, Player player, int goals) {
		this.tournamentTeam = tournamentTeam;
		this.player = player;
		this.goals = goals;
	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TournamentTeam getTournamentTeam() {
		return tournamentTeam;
	}

	public void setTournamentTeam(TournamentTeam tournamentTeam) {
		this.tournamentTeam = tournamentTeam;
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

}

