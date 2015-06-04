package eceep.milestone;

import java.util.List;

public interface Milestone<T extends Step> {
	List<T> getSteps();

	Milestone addChild(T parent, T child);

	T next();

	T prev();

	T goTop();

	T go(T step);

	T getCurrentStep(); // Current step name.

	boolean activeCurrent(); // Active current step, inactive all of child steps.

	boolean inactiveCurrent(); // Inactive current step and all of child steps.

	byte[] serialize();

	Milestone<T> deserialize(byte[] binary);

	// Leaf<T> getTheFirstLeaf();
	//
	// List<T> getMilestones();
	//
	// int getIndex(); // Current milestone index.
	//
	// void setIndex(int index);
	//
	// boolean isActive(); // Return current step active status.
	//
	// Milestone<T> addFirst(T step);
	//
	// boolean removeChild(T step);
	//
	// Leaf<T> find(Leaf<T> currentLeaf, T step);
	//
	// boolean reloadMilestone(T step);
	//
	// T getPrev();
	//
	// T getNext();

}
