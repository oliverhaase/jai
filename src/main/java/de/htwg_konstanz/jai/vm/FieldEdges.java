package de.htwg_konstanz.jai.vm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.CheckReturnValue;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class FieldEdges {
	private static final FieldEdges EMPTY_FIELDEDGES = new FieldEdges();
	private final Set<FieldEdge> edges;

	public static FieldEdges getInstance() {
		return EMPTY_FIELDEDGES;
	}

	private FieldEdges() {
		edges = new HashSet<>();
	}

	private FieldEdges(FieldEdges original) {
		edges = new HashSet<>(original.edges);
	}

	@CheckReturnValue
	public FieldEdges add(FieldEdge fieldEdge) {
		FieldEdges result = new FieldEdges(this);
		result.edges.add(fieldEdge);
		return result;
	}

	@CheckReturnValue
	public FieldEdges addAll(Set<FieldEdge> fieldEdges) {
		FieldEdges result = new FieldEdges(this);
		result.edges.addAll(fieldEdges);
		return result;
	}

	public Set<ObjectNode> getObjectsOfField(ObjectNode obj, String fieldName) {
		HashSet<ObjectNode> objects = new HashSet<ObjectNode>();
		for (FieldEdge fieldEdge : edges) {
			if (fieldEdge.getOrigin().equals(obj) && fieldEdge.getName().equals(fieldName))
				objects.add(fieldEdge.getDestination());
		}
		return objects;
	}

	public Set<FieldEdge> getEdgesStartingFrom(ObjectNode origin) {
		HashSet<FieldEdge> objects = new HashSet<FieldEdge>();
		for (FieldEdge fieldEdge : edges) {
			if (fieldEdge.getOrigin().equals(origin))
				objects.add(fieldEdge);
		}
		return objects;
	}

	public Set<FieldEdge> getEdges() {
		return Collections.unmodifiableSet(edges);
	}

	// public Set<ObjectNode> getFieldValues(ReferenceSlot reference, String
	// fieldName) {
	// Set<ObjectNode> fieldValues = new HashSet<ObjectNode>();
	//
	// for (ObjectNode obj : reference)
	// fieldValues.addAll(getObjectsOfField(obj, fieldName));
	//
	// return fieldValues;
	// }

}
