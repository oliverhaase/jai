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
	private static final ReferenceSlot NULL_REFERENCE = new ReferenceSlot();
	private final Set<ObjectNode> objectNodes;

	private ReferenceSlot() {
		objectNodes = new HashSet<>();
	}

	public static ReferenceSlot getNullReference() {
		return NULL_REFERENCE;
	}

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

	@CheckReturnValue
	public ReferenceSlot addObjects(Set<ObjectNode> objectNodes) {
		ReferenceSlot result = new ReferenceSlot(this);
		result.objectNodes.addAll(objectNodes);
		return result;
	}

	public Set<ObjectNode> getObjects() {
		return Collections.unmodifiableSet(objectNodes);
	}

	public boolean isNullReference() {
		return this == NULL_REFERENCE;
	}

}
