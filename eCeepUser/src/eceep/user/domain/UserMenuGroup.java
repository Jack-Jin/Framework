package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;

public class UserMenuGroup {
	private String title;
	private List<UserMenuLeaf> leaves;

	public UserMenuGroup() {
		leaves = new ArrayList<UserMenuLeaf>();
	}

	public boolean isIsVisible() {
		if (leaves.size() <= 0)
			return false;

		boolean found = leaves.stream().filter(A -> A.isIsVisible()).count() > 0;

		return found;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserMenuLeaf> getLeaves() {
		if (this.leaves == null)
			this.leaves = new ArrayList<UserMenuLeaf>();
		return leaves;
	}
}
