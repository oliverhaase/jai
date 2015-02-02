package de.htwg_konstanz.jai.vm;

import java.util.Arrays;

import javax.annotation.CheckReturnValue;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public class LocalVars {
	private final Slot[] vars;

	public LocalVars(int maxLocals) {
		vars = new Slot[maxLocals];
		for (Slot var : vars)
			var = PrimitiveSlot.INSTANCE;
	}

	private LocalVars(LocalVars original) {
		vars = new Slot[original.vars.length];

		for (int i = 0; i < vars.length; i++)
			vars[i] = original.vars[i];
	}

	public Slot get(int index) {
		return vars[index];
	}

	@CheckReturnValue
	public LocalVars set(int index, Slot slot) {
		LocalVars result = new LocalVars(this);
		result.vars[index] = slot;
		return result;
	}

	@Override
	public String toString() {
		return "L" + Arrays.toString(vars);
	}

}
