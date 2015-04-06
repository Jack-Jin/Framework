package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserPolicy {
	private int id;
	private String policyName;
	private String description;
	private String menus;

	private List<UserPolicyRule> rules;

	public List<UserPolicyRule> getRules() {
		if (rules == null)
			rules = new ArrayList<UserPolicyRule>();

		return rules;
	}

	/**
	 * @param Clazz
	 *            , "Check": Boolean; "Value": String; "Option": List<String>
	 * @param ruleName
	 * @return
	 */
	private <T> T getRuleValue(Class<T> Clazz, String ruleName) {
		List<UserPolicyRule<T>> tmpRules = this.rules.stream().filter(u -> u.getKey().equals(ruleName)).collect(Collectors.toList());
		UserPolicyRule<T> tmpRule = tmpRules.get(0);
		
//		UserPolicyRule<T> tmpRule = null;
//		for(UserPolicyRule<T> item : this.rules)		{
//			if(item.getKey().equalsIgnoreCase(ruleName)) {
//				tmpRule = item;
//				break;
//			}
//		}		

		if (tmpRule.getType().equals(Clazz))
			return tmpRule.getValue();

		return null;
	}

	public Boolean getRuleValueBoolean(String ruleName) {
		return this.getRuleValue(Boolean.class, ruleName);
	}

	public String getRuleValueString(String ruleName) {
		return this.getRuleValue(String.class, ruleName);
	}

	@SuppressWarnings("unchecked")
	public List<String> getRuleValueList(String ruleName) {		
		return this.getRuleValue(List.class, ruleName);
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
