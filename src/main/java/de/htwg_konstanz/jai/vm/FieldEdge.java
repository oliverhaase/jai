package de.htwg_konstanz.jai.vm;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class FieldEdge {
	private final ObjectNode origin;
	private final String name;
	private final ObjectNode destination;

	public FieldEdge(ObjectNode origin, String name, ObjectNode destination) {
		this.origin = origin;
		this.name = name;
		this.destination = destination;
	}
}
