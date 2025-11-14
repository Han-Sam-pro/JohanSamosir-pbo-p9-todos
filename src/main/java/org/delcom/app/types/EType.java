package org.delcom.app.types;

public enum EType {
	INFLOW("Inflow"), OUTFLOW("Outflow");

	private String value;

	EType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static EType fromString(String v) {
		for (EType t : EType.values()) {
			if (t.name().equalsIgnoreCase(v) || t.value.equalsIgnoreCase(v)) return t;
		}
		return null;
	}
}
