package de.htwg_konstanz.jai.vm;

public final class State {
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

}
