package eceep.user.domain;

public class UserPolicyRule<T> {
	private final Class<T> type;

	private int id;
	private String name;

	private T value;

	public UserPolicyRule(Class<T> type) {
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
}
