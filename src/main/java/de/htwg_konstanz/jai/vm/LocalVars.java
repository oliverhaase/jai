package de.htwg_konstanz.jai.vm;

import java.util.Arrays;

import javax.annotation.CheckReturnValue;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;
import de.htwg_konstanz.jai.gen.Type;

@Immutable
@EqualsAndHashCode
public class LocalVars {
	private final Type[] vars;

	public LocalVars(int maxLocals) {
		vars = new Type[maxLocals];
		for (Type var : vars)
			var = PrimitiveSlot.getInstance();
	}

	private LocalVars(LocalVars original) {
		vars = new Type[original.vars.length];

		for (int i = 0; i < vars.length; i++)
			vars[i] = original.vars[i];
	}

	public Type get(int index) {
		return vars[index];
	}

	@CheckReturnValue
	public LocalVars set(int index, Type slot) {
		LocalVars result = new LocalVars(this);
		result.vars[index] = slot;
		return result;
	}

	@Override
	public String toString() {
		return "L" + Arrays.toString(vars);
	}

}
