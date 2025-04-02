package plm.model;

import javax.persistence.Entity;

@Entity
public class LifeCycleTemplate {

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
}
