package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.LinkedMap;

public class UserPolicy {
	private int id;
	private String policyName;
	private String menus;
	private String description;

	private List<UserPolicyRule> rules;

	public List<UserPolicyRule> getRules() {
		if (rules == null)
			rules = new ArrayList<UserPolicyRule>();

		return rules;
	}

	/**
	 * @param Clazz
	 *            , "Check": Boolean; "Value": String; "Option": Map<String,
	 *            String>
	 * @param ruleName
	 * @return
	 */
	private <T> UserPolicyRule<T> getRuleValue(Class<T> Clazz, String ruleName) {
		List<UserPolicyRule<T>> tmpRules = this.rules.stream().filter(u -> u.getName().equals(ruleName))
				.collect(Collectors.toList());
		UserPolicyRule<T> tmpRule = tmpRules.get(0);

		return tmpRule;

		// if (tmpRule.getType().equals(Clazz))
		// return tmpRule.getValue();
		//
		// return null;
	}

	public Boolean getRuleValueBoolean(String ruleName) {
		UserPolicyRule<Boolean> rule = this.getRuleValue(Boolean.class, ruleName);

		if (rule.getType().equals(Boolean.class))
			return rule.getValue();

		return null;
	}

	public String getRuleValueString(String ruleName) {
		UserPolicyRule<String> rule = this.getRuleValue(String.class, ruleName);

		if (rule.getType().equals(String.class))
			return rule.getValue();

		return null;
	}

	public Map<String, Boolean> getRuleValueMap(String ruleName) {
		UserPolicyRule<Map> rule = this.getRuleValue(Map.class, ruleName);

		if (rule.getType().equals(Map.class))
			return rule.getValue();

		return null;
	}

	// @SuppressWarnings("unchecked")
	public Map<String, String> getRuleValueMapOptions(String ruleName) {
		// Return values;
		Map<String, String> result = new LinkedMap<String, String>();

		// Get rule.
		UserPolicyRule<Map> rule = this.getRuleValue(Map.class, ruleName);

		// Rule value type wrong, return empty. 
		if (!rule.getType().equals(Map.class))
			return result;

		// Get rules.
		Map<String, Boolean> values = this.getRuleValueMap(ruleName);

		// Get options list depend on rule values.
		Map<String, String> options = rule.getOptions();
		for (Map.Entry<String, String> option : options.entrySet()) {
			String key = option.getKey();
			
			// rule value is true.
			if (values.containsKey(key) && values.get(key))
				result.put(key, option.getValue());
		}

		return result;
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
