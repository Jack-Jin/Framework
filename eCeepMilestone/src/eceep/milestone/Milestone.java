package eceep.milestone;

import java.util.List;

public interface Milestone<T extends Step> {

	/* Get properties -------------------------------------------- */
	Leaf<T> getTheFirstLeaf();

	Leaf<T> getCurrentLeaf();

	List<T> getSteps();

	List<T> getMilestones();

	boolean isActive(); // Return current step active status.
	/* ------------------------------------------------------------ */
	
	/* Manipulate methods ----------------------------------------- */
	Milestone<T> addFirst(T step);
	
	Milestone<T> addChild(T parent, T child);

	boolean activeCurrent(); // Active current step, inactive all of child steps.
	
	boolean inactiveCurrent(); // Inactive current step and all of child steps.
	
	boolean reloadMilestone(T step);
	
	byte[] serialize();
	
	Milestone<T> deserialize(byte[] binary);
	/* ------------------------------------------------------------ */
	
	/* Navigation methods ----------------------------------------- */
	Leaf<T> find(Leaf<T> currentLeaf, T step);
	
	T goTop();
	
	T go(T step);
	
	T next();

	T prev();
	/* ------------------------------------------------------------ */

	// T getCurrentStep(); // Current step name.

	// int getIndex(); // Current milestone index.
	
	// void setIndex(int index);
	
	// boolean removeChild(T step);
	
	// T getPrev();
	
	// T getNext();
}
