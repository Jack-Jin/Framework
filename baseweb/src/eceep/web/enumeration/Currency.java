package eceep.web.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Currency {
	CAD(1, "CAD"), Euro(2, "Euro"), Pound(3, "Pound"), USD(0, "USD"), UAE(4, "UAE");

	private int id;
	private String label;

	private Currency(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}
}
