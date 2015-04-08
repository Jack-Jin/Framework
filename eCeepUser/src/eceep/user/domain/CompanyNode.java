package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;

public class CompanyNode {
	private int id;
	private String name;

	private List<CompanyNode> children;

	public CompanyNode(int id, String name) {
		this.id = id;
		this.name = name;

		children = new ArrayList<CompanyNode>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CompanyNode> getChildren() {
		if (children == null)
			children = new ArrayList<CompanyNode>();

		return children;
	}

}
