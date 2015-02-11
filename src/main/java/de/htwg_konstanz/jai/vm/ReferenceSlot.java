package de.htwg_konstanz.jai.vm;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public class ReferenceSlot implements Slot {
	private final Set<ObjectNode> objectNodes = new HashSet<>();

	public ReferenceSlot(ObjectNode objectNode) {
		objectNodes.add(objectNode);
	}

}
