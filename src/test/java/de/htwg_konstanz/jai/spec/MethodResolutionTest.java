package de.htwg_konstanz.jai.spec;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.htwg_konstanz.jai.ProgramBuilder;
import de.htwg_konstanz.jai.gen.ByteCodeClass;
import de.htwg_konstanz.jai.gen.Instruction;
import de.htwg_konstanz.jai.gen.InvokeVirtual;
import de.htwg_konstanz.jai.gen.List;
import de.htwg_konstanz.jai.gen.Method;
import de.htwg_konstanz.jai.gen.Program;
import de.htwg_konstanz.jai.gen.ReferenceType;
import de.htwg_konstanz.jai.gen.Type;
import de.htwg_konstanz.jai.vm.ReferenceSlot;
import de.htwg_konstanz.jai.vm.RegularState;

public class MethodResolutionTest {

	@SuppressWarnings("unused")
	private static class TestClass {

		void object_toStringReal(int i) {
			Object obj = new Object();
			obj.toString();
		}

		void object_toStringPhantom(Object obj) {
			obj.toString();
		}

	}

	private Program program;
	private ByteCodeClass testClass;
	private HashMap<String, Method> methods;

	@Before
	public void setUpBeforeClass() throws Exception {
		program = new ProgramBuilder("de.htwg_konstanz.jai.spec.MethodResolutionTest$TestClass")
				.build();

		testClass = program.getByteCodeClass(0);

		methods = new HashMap<String, Method>();
		for (Method method : testClass.getMethods())
			methods.put(method.getMethodName(), method);

	}

	@Test
	public void object_toStringReal() throws Exception {
		Method method = methods.get("object_toStringReal");

		InvokeVirtual instruction = null;
		for (Instruction i : method.getInstructions())
			if (i instanceof InvokeVirtual)
				instruction = (InvokeVirtual) i;

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
		// .pop(instruction.getConsumeStack()).top();

		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(1, targetMethods.size());
		assertTrue(targetMethods.contains(getMethod("java.lang.Object", "toString",
				new List<Type>().add(new ReferenceType("java.lang.Object")))));

	}

	@Test
	public void object_toStringPhantom() throws Exception {
		Method method = methods.get("object_toStringPhantom");

		InvokeVirtual instruction = null;
		for (Instruction i : method.getInstructions())
			if (i instanceof InvokeVirtual)
				instruction = (InvokeVirtual) i;

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
		// .pop(instruction.getConsumeStack()).top();

		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(1, targetMethods.size());
		assertTrue(targetMethods.contains(getMethod("java.lang.Object", "toString",
				new List<Type>().add(new ReferenceType("java.lang.Object")))));

	}

	private Method getMethod(String clazz, String methodName, List<Type> list) {
		for (Method method : program.getClass(clazz).getMethods())
			if (method.matches(methodName, list))
				return method;
		throw new AssertionError("Used wrong class for assert");
	}
}
