package fi.joonas.veikkaus.jpaentity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Bet {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

	@NotNull
    @ManyToOne
    private User user;

	@NotNull
	@ManyToOne
	private Tournament tournament;

	@NotNull
    @ManyToOne
    private Status status;

    public Bet() {}

	public Bet(User user, Status status) {
		this.user = user;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}

