package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;

public class UserPolicy {
	private int id;
	private String policyName;
	private String description;
	private String menus;

	private List<UserPolicyRule> rules;

	public List<UserPolicyRule> getRules() {
		if(rules==null)
			rules = new ArrayList<UserPolicyRule>();

		return rules;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}
}
