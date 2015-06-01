package de.htwg_konstanz.jai.vm;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class FieldEdge {
	@Getter
	private final ObjectNode origin;
	@Getter
	private final String name;
	@Getter
	private final ObjectNode destination;

	public FieldEdge(ObjectNode origin, String name, ObjectNode destination) {
		this.origin = origin;
		this.name = name;
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "FieldEdge ['" + origin + "' --" + name + "--> '" + destination + "']";
	}

}
