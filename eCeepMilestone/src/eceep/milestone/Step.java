package eceep.milestone;

public interface Step {
	int getId();
	
	String getName();
	
	String getTitle();
	
	String getURI();
	
	boolean isActive();
	
	void setActive(boolean active);
}
