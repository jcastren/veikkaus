package fi.joonas.veikkaus.guientity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class StatusGuiEntity {

	private String id;

	@NotNull
	@Max(5)
    private int statusNumber;

    private String description;

	public StatusGuiEntity() {
		super();
	}

	public StatusGuiEntity(String id, int statusNumber, String description ) {
		super();
		this.id = id;
		this.statusNumber = statusNumber;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatusNumber() {
		return statusNumber;
	}

	public void setStatusNumber(int statusNumber) {
		this.statusNumber = statusNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}