package eceep.user.domain;

public class UserPolicyOption {
	private int id;
	private String optionName;
	private String optionValue;
	private boolean ruleValue;

	public UserPolicyOption(int id, String optionName, String optionValue, boolean ruleValue) {
		super();
		this.id = id;
		this.optionName = optionName;
		this.optionValue = optionValue;
		this.ruleValue = ruleValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public boolean isRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(boolean ruleValue) {
		this.ruleValue = ruleValue;
	}

}
