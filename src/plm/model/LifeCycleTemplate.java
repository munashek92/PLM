package plm.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LifeCycleTemplate {

    @Id
    private Long id;

	public String getInitialState() {
		//
		// Implementation and returned value are not relevant for this exercise
		//
		return null;
	}
	
	public boolean isKnown(String lifeCycleState) {
		//
		// Implementation and returned value are not relevant for this exercise
		//
		return true;
	}
	
	public boolean isFinal(String lifeCycleState) {
		//
		// Implementation and returned value are not relevant for this exercise
		//
		return true;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
