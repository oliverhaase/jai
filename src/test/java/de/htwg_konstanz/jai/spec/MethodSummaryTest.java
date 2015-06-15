package de.htwg_konstanz.jai.spec;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg_konstanz.jai.ProgramBuilder;
import de.htwg_konstanz.jai.gen.ByteCodeClass;
import de.htwg_konstanz.jai.gen.Method;
import de.htwg_konstanz.jai.gen.NewInstruction;
import de.htwg_konstanz.jai.gen.Program;
import de.htwg_konstanz.jai.vm.MethodSummary;
import de.htwg_konstanz.jai.vm.ObjectNode;
import de.htwg_konstanz.jai.vm.PrimitiveSlot;
import de.htwg_konstanz.jai.vm.RealObject;
import de.htwg_konstanz.jai.vm.ReferenceSlot;

public class MethodSummaryTest {

	@SuppressWarnings("unused")
	private static class TestClass {

		Object returnObject() {
			Object object = new Object();
			return object;
		}

		Object returnSimple() {
			Object object = new Simple();
			return object;
		}

		int returnInt() {
			return 36 * 187;
		}

	}

	private static class Simple {
		public Simple() {
		}
	}

	private static Program program;
	private static ByteCodeClass testClass;
	private static HashMap<String, Method> methods;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		program = new ProgramBuilder("de.htwg_konstanz.jai.spec.MethodSummaryTest$TestClass")
				.build();

		testClass = program.getByteCodeClass(0);

		methods = new HashMap<String, Method>();
		for (Method method : testClass.getMethods())
			methods.put(method.getMethodName(), method);

	}

	@Test
	public void returnObject() throws Exception {
		MethodSummary methodSummary = methods.get("returnObject").getMethodSummary();

		assertTrue(methodSummary.getReturnValue() instanceof ReferenceSlot);
		assertEquals(1, ((ReferenceSlot) methodSummary.getReturnValue()).getObjects().size());
		ObjectNode returnValue = ((ReferenceSlot) methodSummary.getReturnValue()).getObjects()
				.iterator().next();
		assertTrue(returnValue instanceof RealObject);
		assertEquals("java.lang.Object", ((NewInstruction) returnValue).getType());
		assertTrue(methodSummary.getFieldEdges().getEdges().isEmpty());
	}

	@Test
	public void returnSimple() throws Exception {
		MethodSummary methodSummary = methods.get("returnSimple").getMethodSummary();

		assertTrue(methodSummary.getReturnValue() instanceof ReferenceSlot);
		assertEquals(1, ((ReferenceSlot) methodSummary.getReturnValue()).getObjects().size());
		ObjectNode returnValue = ((ReferenceSlot) methodSummary.getReturnValue()).getObjects()
				.iterator().next();
		assertTrue(returnValue instanceof RealObject);
		assertEquals("de.htwg_konstanz.jai.spec.MethodSummaryTest$Simple",
				((NewInstruction) returnValue).getType());
		assertTrue(methodSummary.getFieldEdges().getEdges().isEmpty());
	}

	@Test
	public void returnInt() throws Exception {
		MethodSummary methodSummary = methods.get("returnInt").getMethodSummary();

		assertTrue(methodSummary.getReturnValue() instanceof PrimitiveSlot);
		assertTrue(methodSummary.getFieldEdges().getEdges().isEmpty());
	}
}
