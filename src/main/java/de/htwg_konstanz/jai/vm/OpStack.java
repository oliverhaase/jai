package de.htwg_konstanz.jai.vm;

import java.util.Stack;

import javax.annotation.CheckReturnValue;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import net.jcip.annotations.Immutable;
import de.htwg_konstanz.jai.gen.Type;

@Immutable
@EqualsAndHashCode
public final class OpStack {
	private final Stack<Type> stack;

	public OpStack() {
		stack = new Stack<Type>();
	}

	private OpStack(@NonNull OpStack original) {
		this.stack = new Stack<Type>();
		stack.addAll(original.stack);
	}

	@CheckReturnValue
	public OpStack push(@NonNull Type slot) {
		OpStack result = new OpStack(this);
		result.stack.push(slot);
		return result;
	}

	@CheckReturnValue
	public OpStack push(Type slot, int n) {
		OpStack result = new OpStack(this);
		for (int i = 0; i < n; i++)
			result = result.push(slot);
		return result;
	}

	@CheckReturnValue
	public OpStack pop() {
		OpStack result = new OpStack(this);
		result.stack.pop();
		return result;
	}

	@CheckReturnValue
	public OpStack pop(int n) {
		OpStack result = new OpStack(this);
		for (int i = 0; i < n; i++)
			result = result.pop();
		return result;
	}

	public Type peek() {
		return stack.peek();
	}
}
