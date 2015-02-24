package de.htwg_konstanz.jai.vm;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class ExceptionState implements State {
	private final FieldEdges fieldEdges;

	public ExceptionState(FieldEdges fieldEdges) {
		this.fieldEdges = fieldEdges;
	}

	public FieldEdges getFieldEdges() {
		return fieldEdges;
	}
}
