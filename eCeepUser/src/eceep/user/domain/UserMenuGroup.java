package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;

public class UserMenuGroup {
	private boolean isVisible;
	private String title;
	private List<UserMenuLeaf> leaves;
	
	public UserMenuGroup(){
		leaves = new ArrayList<UserMenuLeaf>();
	}

	public boolean isIsVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserMenuLeaf> getLeaves() {
		if(this.leaves == null)
			this.leaves = new ArrayList<UserMenuLeaf>();
		return leaves;
	}
}
