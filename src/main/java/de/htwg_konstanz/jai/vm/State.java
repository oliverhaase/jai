package de.htwg_konstanz.jai.vm;

public final class State {
	private final LocalVars localVars;
	private final OpStack opStack;

	public State(LocalVars localVars, OpStack opStack) {
		this.localVars = localVars;
		this.opStack = opStack;
	}

	public Object getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	public OpStack getOpStack() {
		return opStack;
	}

	public LocalVars getLocalVars() {
		return localVars;
	}

}
