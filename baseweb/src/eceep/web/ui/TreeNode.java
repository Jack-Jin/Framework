package eceep.web.ui;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private String id;
	private String name;

	private List<TreeNode> children;

	public TreeNode(String id, String name) {
		this.id = id;
		this.name = name;
		
		children = new ArrayList<TreeNode>();
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

	public List<TreeNode> getChildren() {
		return children;
	}


}
