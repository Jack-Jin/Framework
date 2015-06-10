package eceep.milestone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Leaf<T extends Step> implements Serializable {
	private static final long serialVersionUID = 1L;

	private T step;

	private Leaf<T> parent;
	private List<Leaf<T>> children;

	public Leaf(Leaf<T> parent, T step) {
		this.parent = parent;
		this.step = step;
		this.children = new ArrayList<Leaf<T>>();
	}

	@SuppressWarnings("unused")
	private Leaf() {
	}
	
	public T getStep() {
		return step;
	}

	public void setStep(T step) {
		this.step = step;
	}

	public Leaf<T> getParent() {
		return parent;
	}

	public void setParent(Leaf<T> parent) {
		this.parent = parent;
	}

	public List<Leaf<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Leaf<T>> children) {
		this.children = children;
	}

}
