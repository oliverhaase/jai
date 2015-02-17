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
	private static final ReferenceSlot EMPTY_REFERNCE_SLOT = new ReferenceSlot();
	private final Set<ObjectNode> objectNodes;

	private ReferenceSlot() {
		objectNodes = new HashSet<>();
	}

	public static ReferenceSlot getInstance() {
		return EMPTY_REFERNCE_SLOT;
	}

	public ReferenceSlot(ObjectNode objectNode) {
		objectNodes = new HashSet<>();
		objectNodes.add(objectNode);
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

	@CheckReturnValue
	public ReferenceSlot addObjects(Set<ObjectNode> objectNodes) {
		ReferenceSlot result = new ReferenceSlot(this);
		result.objectNodes.addAll(objectNodes);
		return result;
	}

	public Set<ObjectNode> getObjects() {
		return Collections.unmodifiableSet(objectNodes);
	}

}
