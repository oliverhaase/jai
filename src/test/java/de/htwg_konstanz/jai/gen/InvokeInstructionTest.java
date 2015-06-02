package de.htwg_konstanz.jai.gen;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.htwg_konstanz.jai.vm.FieldEdge;
import de.htwg_konstanz.jai.vm.FieldEdges;
import de.htwg_konstanz.jai.vm.ObjectNode;
import de.htwg_konstanz.jai.vm.OpStack;
import de.htwg_konstanz.jai.vm.PhantomObject;
import de.htwg_konstanz.jai.vm.PrimitiveSlot;
import de.htwg_konstanz.jai.vm.ReferenceSlot;

public class InvokeInstructionTest {

	private InvokeSpecial invokeSpecial;
	private OpStack opStack;
	private FieldEdges fieldEdges;
	private List<Type> params;
	private FieldEdges methodSummary;

	@Before
	public void setUp() throws Exception {
		invokeSpecial = new InvokeSpecial();
		ObjectNode thiz = new New();
		ObjectNode arg1 = new New();
		((New) arg1).setType("arg1");
		ObjectNode arg2 = new New();
		((New) arg2).setType("arg2");
		ObjectNode arg3 = new New();
		((New) arg3).setType("arg3");
		ObjectNode arg4 = new New();
		((New) arg4).setType("arg4");
		ObjectNode arg1_f_obj1 = new New();
		((New) arg1_f_obj1).setType("arg1_f_obj1");
		ObjectNode obj1_f_obj2 = new New();
		((New) obj1_f_obj2).setType("obj1_f_obj2");
		ObjectNode obj1_g_obj5 = new New();
		((New) obj1_g_obj5).setType("obj1_g_obj5");
		ObjectNode obj2_f_obj3 = new New();
		((New) obj2_f_obj3).setType("obj2_f_obj3");
		ObjectNode obj2_f_obj4 = new New();
		((New) obj2_f_obj4).setType("obj2_f_obj4");
		ObjectNode arg4_f_obj6 = new New();
		((New) arg4_f_obj6).setType("arg4_f_obj6");
		ObjectNode obj6_g_obj7 = new New();
		((New) obj6_g_obj7).setType("obj6_g_obj7");

		ReferenceType param1 = new ReferenceType("param1");
		ReferenceType param2 = new ReferenceType("param2");
		ReferenceType param3 = new ReferenceType("param3");
		ReferenceType param4 = new ReferenceType("param4");
		ObjectNode param1_f_subPhantom1 = new GetField();
		((GetField) param1_f_subPhantom1).setLabel("param1_f_subPhantom1");
		ObjectNode subPhantom1_f_subPhantom2 = new GetField();
		((GetField) subPhantom1_f_subPhantom2).setLabel("subPhantom1_f_subPhantom2");
		ObjectNode subPhantom1_g_subPhantom5 = new GetField();
		((GetField) subPhantom1_g_subPhantom5).setLabel("subPhantom1_g_subPhantom5");
		ObjectNode subPhantom2_f_subPhantom3 = new GetField();
		((GetField) subPhantom2_f_subPhantom3).setLabel("subPhantom2_f_subPhantom3");
		ObjectNode param3_f_subPhantom6 = new GetField();
		((GetField) param3_f_subPhantom6).setLabel("param3_f_subPhantom6");
		ObjectNode subPhantom6_f_subPhantom7 = new GetField();
		((GetField) subPhantom6_f_subPhantom7).setLabel("subPhantom6_f_subPhantom7");
		ObjectNode param4_f_subPhantom8 = new GetField();
		((GetField) param4_f_subPhantom8).setLabel("param4_f_subPhantom8");
		ObjectNode subPhantom8_g_subPhantom9 = new GetField();
		((GetField) subPhantom8_g_subPhantom9).setLabel("subPhantom8_g_subPhantom9");

		ObjectNode param3_g_obj8 = new New();
		((New) param3_g_obj8).setType("param3_g_obj8");
		ObjectNode obj8_f_obj9 = new New();
		((New) obj8_f_obj9).setType("obj8_f_obj9");
		ObjectNode subPhantom5_f_obj10 = new New();
		((New) subPhantom5_f_obj10).setType("subPhantom5_f_obj10");

		opStack = OpStack.getEmptyStack().push(new ReferenceSlot(thiz))
				.push(new ReferenceSlot(arg1)).push(PrimitiveSlot.getInstance(), 2)
				.push(new ReferenceSlot(arg2)).push(new ReferenceSlot(arg3))
				.push(new ReferenceSlot(arg4));

		fieldEdges = FieldEdges.getInstance().add(new FieldEdge(arg1, "f", arg1_f_obj1))
				.add(new FieldEdge(arg1_f_obj1, "f", obj1_f_obj2))
				.add(new FieldEdge(arg1_f_obj1, "g", obj1_g_obj5))
				.add(new FieldEdge(obj1_f_obj2, "f", obj2_f_obj3))
				.add(new FieldEdge(obj1_f_obj2, "f", obj2_f_obj4))
				.add(new FieldEdge(arg4, "f", arg4_f_obj6))
				.add(new FieldEdge(arg4_f_obj6, "f", arg4)).add(new FieldEdge(arg2, "f", arg1))
				.add(new FieldEdge(arg4_f_obj6, "g", obj6_g_obj7))
				.add(new FieldEdge(obj6_g_obj7, "f", arg4_f_obj6));

		params = new List<Type>(param1, new PrimitiveType(), new PrimitiveType(), param2, param3,
				param4);

		methodSummary = FieldEdges.getInstance()
				.add(new FieldEdge(param1, "f", param1_f_subPhantom1))
				.add(new FieldEdge(param1_f_subPhantom1, "f", subPhantom1_f_subPhantom2))
				.add(new FieldEdge(param1_f_subPhantom1, "g", subPhantom1_g_subPhantom5))
				.add(new FieldEdge(subPhantom1_g_subPhantom5, "f", subPhantom5_f_obj10))
				.add(new FieldEdge(subPhantom1_f_subPhantom2, "f", subPhantom2_f_subPhantom3))
				.add(new FieldEdge(param2, "f", param1))
				.add(new FieldEdge(param3, "f", param3_f_subPhantom6))
				.add(new FieldEdge(param3_f_subPhantom6, "f", subPhantom6_f_subPhantom7))
				.add(new FieldEdge(param4, "f", param4_f_subPhantom8))
				.add(new FieldEdge(param4_f_subPhantom8, "g", subPhantom8_g_subPhantom9))
				.add(new FieldEdge(subPhantom8_g_subPhantom9, "f", param4_f_subPhantom8))
				.add(new FieldEdge(param3, "g", param3_g_obj8))
				.add(new FieldEdge(param3_g_obj8, "f", obj8_f_obj9));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void mapArgumentsTest() {
		Map<PhantomObject, Set<ObjectNode>> mapArguments = invokeSpecial.mapArguments(opStack,
				fieldEdges, params, methodSummary);
		assertEquals(12, mapArguments.size());
	}

	@Test
	public final void transferFieldEdgesTest() {
		Map<PhantomObject, Set<ObjectNode>> mapping = invokeSpecial.mapArguments(opStack,
				fieldEdges, params, methodSummary);

		assertEquals(10, fieldEdges.getEdges().size());
		FieldEdges transfered = invokeSpecial
				.transferFieldEdges(fieldEdges, methodSummary, mapping);
		assertEquals(13, transfered.getEdges().size());
	}
}
