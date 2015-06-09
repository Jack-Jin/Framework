package eceep.milestone.impl;

import eceep.milestone.Step;

public class StepDefault implements Step {
	private int id;
	private String name;
	private String title;
	private String uri;
	private boolean active;

	public StepDefault(int id, String name, String title, String uri, boolean active) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.uri = uri;
		this.active = active;
	}
	
	private StepDefault() {
	}

	@Override
	public int getId() {
		return this.id;
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
