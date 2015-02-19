package de.htwg_konstanz.jai.vm;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class State {
	private final static State exceptionState = new State(null, null, null);

	private final LocalVars localVars;
	private final OpStack opStack;
	private final FieldEdges fieldEdges;

	public State(LocalVars localVars, OpStack opStack, FieldEdges fieldEdges) {
		this.localVars = localVars;
		this.opStack = opStack;
		this.fieldEdges = fieldEdges;
	}

	public OpStack getOpStack() {
		return opStack;
	}

	public LocalVars getLocalVars() {
		return localVars;
	}

	public FieldEdges getFieldEdges() {
		return fieldEdges;
	}

	public static State getExceptionState() {
		return exceptionState;
	}

	public boolean isExceptionState() {
		return this == exceptionState;
	}

}
