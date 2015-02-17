package de.htwg_konstanz.jai.vm;

import net.jcip.annotations.Immutable;

@Immutable
public class PrimitiveSlot implements Slot {
	private final static PrimitiveSlot instance = new PrimitiveSlot();

	private PrimitiveSlot() {
	}

	public static PrimitiveSlot getInstance() {
		return instance;
	}
}
