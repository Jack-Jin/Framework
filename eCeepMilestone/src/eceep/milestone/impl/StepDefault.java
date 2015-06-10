package eceep.milestone.impl;

import java.io.Serializable;

import eceep.milestone.Step;

public class StepDefault implements Step, Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String title;
	private String uri;
	private boolean active;

	public StepDefault(String name, String title, String uri, boolean active) {
		this.name = name;
		this.title = title;
		this.uri = uri;
		this.active = active;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 53 * hash + (this.title != null ? this.title.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof StepDefault))
			return false;

		if (getClass() != obj.getClass())
			return false;

		final StepDefault other = (StepDefault) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unused")
	private StepDefault() {
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getURI() {
		return this.uri;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

}
