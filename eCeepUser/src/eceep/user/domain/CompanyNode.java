package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;


public class CompanyNode {
	private String id;
	private String name;

	private List<CompanyNode> children;

	public CompanyNode(String id, String name) {
		this.id = id;
		this.name = name;
		
		children = new ArrayList<CompanyNode>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CompanyNode> getChildren() {
		return children;
	}

}
