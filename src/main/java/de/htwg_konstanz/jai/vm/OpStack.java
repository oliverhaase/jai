package de.htwg_konstanz.jai.vm;

import javax.annotation.CheckReturnValue;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import net.jcip.annotations.Immutable;
import de.htwg_konstanz.jai.gen.Type;

@Immutable
@EqualsAndHashCode
public final class OpStack {
	private final Type top;
	private final OpStack rest;

	private final static OpStack emptyStack = new OpStack(null, null);

	private OpStack(Type top, OpStack rest) {
		this.top = top;
		this.rest = rest;
	}

	public static OpStack getEmptyStack() {
		return emptyStack;
	}

	@CheckReturnValue
	public OpStack push(@NonNull Type slot) {
		return new OpStack(slot, this);
	}

	@CheckReturnValue
	public OpStack push(Type slot, int n) {
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

	public Type top() {
		return top;
	}

}
