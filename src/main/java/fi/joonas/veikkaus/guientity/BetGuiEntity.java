package fi.joonas.veikkaus.guientity;

public class BetGuiEntity {
	private String id;
    private UserGuiEntity user;
    private StatusGuiEntity status;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserGuiEntity getUser() {
		return user;
	}
	public void setUser(UserGuiEntity user) {
		this.user = user;
	}
	public StatusGuiEntity getStatus() {
		return status;
	}
	public void setStatus(StatusGuiEntity status) {
		this.status = status;
	}
}

