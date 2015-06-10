package eceep.milestone;

public interface Step {
	String getName();
	
	String getTitle();
	
	String getURI();
	
	boolean isActive();
	
	void setActive(boolean active);
	
	int hashCode();
	
	boolean equals(Object obj);
}
