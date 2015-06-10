package eceep.milestone;

import java.io.IOException;
import java.util.List;

public interface Milestone<T extends Step> {

	/* Get properties -------------------------------------------- */
	T getTheFirstStep();

	T getCurrentStep();

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
	
	byte[] serialize() throws IOException;
	
	Milestone<T> deserialize(byte[] binary) throws IOException, ClassNotFoundException;
	/* ------------------------------------------------------------ */
	
	/* Navigation methods ----------------------------------------- */
	Leaf<T> find(Leaf<T> currentLeaf, T step);
	
	T goTop();
	
	T go(T step);
	
	T next();

	T next(T step);
	
	T prev();
	/* ------------------------------------------------------------ */

	// T getCurrentStep(); // Current step name.

	// int getIndex(); // Current milestone index.
	
	// void setIndex(int index);
	
	// boolean removeChild(T step);
	
	// T getPrev();
	
	// T getNext();
}
