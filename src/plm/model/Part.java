package plm.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(plm.model.Part.PartPK.class)
public class Part {
	
	@Id
	private String reference;
	@Id
	private String version;
	@Id
	private int iteration;
	
	@Column
	private boolean reserved;
	@Column
	private String  reservedBy;

	@ManyToOne
	private LifeCycleTemplate lifeCycleTemplate;
	@Column
	private String lifeCycleState;
	
	@ManyToOne
	private VersionSchema versionSchema;
	
	@Column
	private String partAttribute1;
	@Column
	private String partAttribute2;
	
	public Part() 
	{
		
	}
	
	public Part(String reference, String version, int iteration) {
		this.reference = reference;
		this.version = version;
		this.iteration = iteration;
	}

	public String getReference() {
		return reference;
	}

	public String getVersion() {
		return version;
	}

	public int getIteration() {
		return iteration;
	}

	public boolean isReserved() {
		return reserved;
	}

	public String getReservedBy() {
		return reservedBy;
	}

	public LifeCycleTemplate getLifeCycleTemplate() {
		return lifeCycleTemplate;
	}

	public String getLifeCycleState() {
		return lifeCycleState;
	}

	public VersionSchema getVersionSchema() {
		return versionSchema;
	}

	public String getPartAttribute1() {
		return partAttribute1;
	}

	public String getPartAttribute2() {
		return partAttribute2;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public void setReservedBy(String reservedBy) {
		this.reservedBy = reservedBy;
	}

	public void setLifeCycleTemplate(LifeCycleTemplate lifeCycleTemplate) {
		this.lifeCycleTemplate = lifeCycleTemplate;
	}

	public void setLifeCycleState(String lifeCycleState) {
		this.lifeCycleState = lifeCycleState;
	}

	public void setVersionSchema(VersionSchema versionSchema) {
		this.versionSchema = versionSchema;
	}

	public void setPartAttribute1(String partAttribute1) {
		this.partAttribute1 = partAttribute1;
	}

	public void setPartAttribute2(String partAttribute2) {
		this.partAttribute2 = partAttribute2;
	}
	
	public static class PartPK {
		private String reference;
		private String version;
		private int iteration;
		public PartPK() {
			
		}
		public PartPK(String reference, String version, int iteration) {
			this.reference = reference;
			this.version = version;
			this.iteration = iteration;
		}
		@Override
		public int hashCode() {
			return Objects.hash(iteration, reference, version);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PartPK other = (PartPK) obj;
			return iteration == other.iteration && Objects.equals(reference, other.reference)
					&& Objects.equals(version, other.version);
		}
		
	}

	
		
}
