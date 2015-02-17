package de.htwg_konstanz.jai.vm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.CheckReturnValue;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class ReferenceSlot implements Slot {
	private final Set<ObjectNode> objectNodes;

	public ReferenceSlot(ObjectNode objectNode) {
		objectNodes = new HashSet<>();
		objectNodes.add(objectNode);
	}

	public ReferenceSlot(Set<ObjectNode> objectNodes) {
		this.objectNodes = new HashSet<>(objectNodes);
	}

	private ReferenceSlot(ReferenceSlot original) {
		objectNodes = new HashSet<>(original.objectNodes);
	}

	@CheckReturnValue
	public ReferenceSlot addObject(ObjectNode objectNode) {
		ReferenceSlot result = new ReferenceSlot(this);
		result.objectNodes.add(objectNode);
		return result;
	}

	public Set<ObjectNode> getObjects() {
		return Collections.unmodifiableSet(objectNodes);
	}

}
