package de.htwg_konstanz.jai.vm;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import net.jcip.annotations.Immutable;

@Immutable
@EqualsAndHashCode
public final class FieldEdges {
	final Set<FieldEdges> edges = new HashSet<>();

}
