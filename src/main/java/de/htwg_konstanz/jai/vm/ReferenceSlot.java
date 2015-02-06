package de.htwg_konstanz.jai.vm;

public class ReferenceSlot implements Slot {
	private final ObjectNode[] objectNodes;

	public ReferenceSlot(ObjectNode objectNode) {
		objectNodes = new ObjectNode[1];
		objectNodes[0] = objectNode;
	}

}
