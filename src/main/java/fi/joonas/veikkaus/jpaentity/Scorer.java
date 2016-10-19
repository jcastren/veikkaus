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
public class Scorer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private TournamentPlayer tournamentPlayer;
    
    @ManyToOne
    private Game game;
    
	public Scorer() {}

	public Scorer(Long id) {
		this.id = id;
	}

    public Scorer(TournamentPlayer tournamentPlayer, Game game) {
		super();
		this.tournamentPlayer = tournamentPlayer;
		this.game = game;
	}

	public Long getId() {
		return id;
	}

	public TournamentPlayer getTournamentPlayer() {
		return tournamentPlayer;
	}

	public void setTournamentPlayer(TournamentPlayer tournamentPlayer) {
		this.tournamentPlayer = tournamentPlayer;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}

