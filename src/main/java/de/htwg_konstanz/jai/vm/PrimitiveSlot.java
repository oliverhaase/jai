package de.htwg_konstanz.jai.vm;


public class PrimitiveSlot implements Slot {
	private static PrimitiveSlot instance = new PrimitiveSlot();

	private PrimitiveSlot() {
	}

	public static PrimitiveSlot getInstance() {
		return instance;
	}
}
