package de.htwg_konstanz.jai.vm;

import javax.annotation.CheckReturnValue;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class OpStack {
	private final Slot top;
	private final OpStack rest;

	private final static OpStack emptyStack = new OpStack(null, null);

	private OpStack(Slot top, OpStack rest) {
		this.top = top;
		this.rest = rest;
	}

	public static OpStack getEmptyStack() {
		return emptyStack;
	}

	@CheckReturnValue
	public OpStack push(@NonNull Slot slot) {
		return new OpStack(slot, this);
	}

	@CheckReturnValue
	public OpStack push(Slot slot, int n) {
		OpStack result = this;
		for (int i = 0; i < n; i++)
			result = result.push(slot);
		return result;
	}

	@CheckReturnValue
	public OpStack pop() {
		return rest;
	}

	@CheckReturnValue
	public OpStack pop(int n) {
		OpStack result = this;
		for (int i = 0; i < n; i++)
			result = result.pop();
		return result;
	}

	public Slot top() {
		return top;
	}

	@Override
	public String toString() {
		if (this == emptyStack)
			return "EmptyStack";
		else
			return "[" + top + "], " + rest;
	}
}
