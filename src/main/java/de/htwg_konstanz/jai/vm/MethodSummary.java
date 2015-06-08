package de.htwg_konstanz.jai.vm;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class MethodSummary {
	private final FieldEdges fieldEdges;
	private final Slot returnValue;

	public MethodSummary(FieldEdges fieldEdges, Slot returnValue) {
		this.fieldEdges = fieldEdges;
		this.returnValue = returnValue;
	}

	public FieldEdges getFieldEdges() {
		return fieldEdges;
	}

	public Slot getReturnValue() {
		return returnValue;
	}

}
