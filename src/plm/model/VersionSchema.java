package plm.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VersionSchema {

    @Id
    private Long id;

	public String getNextVersionLabel(String currentVersionLabel) {
		//
		// Implementation and returned value are not relevant for this exercise
		//
		return null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
