package de.htwg_konstanz.jai.spec;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.htwg_konstanz.jai.ProgramBuilder;
import de.htwg_konstanz.jai.gen.ByteCodeClass;
import de.htwg_konstanz.jai.gen.Instruction;
import de.htwg_konstanz.jai.gen.InvokeInstruction;
import de.htwg_konstanz.jai.gen.InvokeVirtual;
import de.htwg_konstanz.jai.gen.List;
import de.htwg_konstanz.jai.gen.Method;
import de.htwg_konstanz.jai.gen.Program;
import de.htwg_konstanz.jai.gen.ReferenceType;
import de.htwg_konstanz.jai.gen.Type;
import de.htwg_konstanz.jai.vm.OpStack;
import de.htwg_konstanz.jai.vm.ReferenceSlot;
import de.htwg_konstanz.jai.vm.RegularState;

public class MethodResolutionTest {

	@SuppressWarnings("unused")
	private static class TestClass {

		void object_toStringReal(int i) {
			Object obj = new Object();
			obj.toString();
		}

		void InvokeInstruction_resolveMethodDefinitionPhantom(InvokeInstruction i) {
			i.resolveMethodDefinition(null, null, null);
		}

		void InvokeInstruction_getArgumentClassesPhantom(InvokeInstruction i) {
			i.getArgumentClasses(null);
		}

		void set_toStringPhantom(Set<?> s) {
			s.toString();
		}

		void object_toStringPhantom(Object obj) {
			obj.toString();
		}

		void object_equalsPhantom(Object obj) {
			obj.equals(obj);
		}

		void object_hashCodePhantom(Object obj) {
			obj.hashCode();
		}

		void opstack_topReal(OpStack stack) {
			stack.top();
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

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

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
	public void InvokeInstruction_resolveMethodDefinitionPhantom() throws Exception {
		Method method = methods.get("InvokeInstruction_resolveMethodDefinitionPhantom");

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
				.pop(instruction.getConsumeStack() - 1).top();

		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(1, targetMethods.size());
		// assertTrue(targetMethods.contains(getMethod(
		// "de.htwg_konstanz.jai.gen.InvokeInstruction",
		// "resolveMethodDefinition",
		// new List<Type>()
		// .add(new ReferenceType("de.htwg_konstanz.jai.gen.InvokeInstruction"))
		// .add(new ReferenceType("java.lang.Class"))
		// .add(new ReferenceType("java.lang.String"))
		// .add(new ReferenceType("de.htwg_konstanz.jai.gen.List<Type>")))));
	}

	@Test
	public void InvokeInstruction_getArgumentClassesPhantom() throws Exception {
		Method method = methods.get("InvokeInstruction_getArgumentClassesPhantom");

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
				.pop(instruction.getConsumeStack() - 1).top();

		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(2, targetMethods.size());
	}

	@Test
	public void set_toStringPhantom() throws Exception {
		Method method = methods.get("set_toStringPhantom");

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
		// .pop(instruction.getConsumeStack()).top();

		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		for (Method method2 : targetMethods) {
			System.out.println(method2.clazz());
		}

		assertEquals(22, targetMethods.size());
		// assertTrue(targetMethods.contains(getMethod("java.lang.Object",
		// "toString",
		// new List<Type>().add(new ReferenceType("java.lang.Object")))));

	}

	@Test
	public void object_toStringPhantom() throws Exception {
		Method method = methods.get("object_toStringPhantom");

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
		// .pop(instruction.getConsumeStack()).top();

		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(2650, targetMethods.size());
		assertTrue(targetMethods.contains(getMethod("java.lang.Object", "toString",
				new List<Type>().add(new ReferenceType("java.lang.Object")))));

	}

	@Test
	public void object_equalsPhantom() throws Exception {
		Method method = methods.get("object_equalsPhantom");

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
		// .pop(instruction.getConsumeStack()).top();

		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(1453, targetMethods.size());
		assertTrue(targetMethods.contains(getMethod(
				"java.lang.Object",
				"equals",
				new List<Type>().add(new ReferenceType("java.lang.Object")).add(
						new ReferenceType("java.lang.Object")))));
	}

	@Test
	public void object_hashCodePhantom() throws Exception {
		Method method = methods.get("object_hashCodePhantom");

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
		// .pop(instruction.getConsumeStack()).top();

		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(1359, targetMethods.size());
		assertTrue(targetMethods.contains(getMethod("java.lang.Object", "hashCode",
				new List<Type>().add(new ReferenceType("java.lang.Object")))));
	}

	@Test
	public void opstack_topReal() throws Exception {
		Method method = methods.get("opstack_topReal");

		InvokeVirtual instruction = getInvokeVirtualInstruction(method);

		RegularState stateIn = (RegularState) instruction.statesIn().iterator().next();
		// ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack()
		// .pop(instruction.getConsumeStack()).top();

		ReferenceSlot objRef = (ReferenceSlot) stateIn.getOpStack().top();

		Set<Method> targetMethods = instruction.getTargetMethods(objRef);

		assertEquals(1, targetMethods.size());
		assertTrue(targetMethods.contains(getMethod("de.htwg_konstanz.jai.vm.OpStack", "top",
				new List<Type>().add(new ReferenceType("de.htwg_konstanz.jai.vm.OpStack")))));

	}

	private Method getMethod(String clazz, String methodName, List<Type> list) {
		for (Method method : program.getClass(clazz).getMethods())
			if (method.matches(methodName, list, true))
				return method;
		throw new AssertionError("Used wrong class for assert");
	}

	private InvokeVirtual getInvokeVirtualInstruction(Method method) {
		InvokeVirtual instruction = null;
		for (Instruction i : method.getInstructions())
			if (i instanceof InvokeVirtual)
				instruction = (InvokeVirtual) i;
		return instruction;
	}
}
