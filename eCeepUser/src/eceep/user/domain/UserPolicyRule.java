package eceep.user.domain;

import java.util.Map;
import org.apache.commons.collections4.map.LinkedMap;

public class UserPolicyRule<T> {
	private final Class<T> type;

	private int id;
	private String name;
	private Map<String, String> options;

	private T value;

	public UserPolicyRule(Class<T> type) {
		this.options = new LinkedMap<String, String>();

		this.type = type;
	}

	public Class<T> getType() {
		return this.type;
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Map<String, String> getOptions() {
		return options;
	}
	

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
}
