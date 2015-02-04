package de.htwg_konstanz.jai.vm;

import de.htwg_konstanz.jai.gen.Type;

public class PrimitiveSlot extends Type {
	private static PrimitiveSlot instance = new PrimitiveSlot();

	private PrimitiveSlot() {
	}

	public static PrimitiveSlot getInstance() {
		return instance;
	}
}
