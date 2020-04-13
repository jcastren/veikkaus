package fi.joonas.veikkaus.jpaentity;

import javax.persistence.*;

@Entity
public class Scorer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private TournamentPlayer tournamentPlayer;
    
    @ManyToOne
    private Game game;
    
	public Scorer() {}

    public Scorer(TournamentPlayer tournamentPlayer, Game game) {
		this.tournamentPlayer = tournamentPlayer;
		this.game = game;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

