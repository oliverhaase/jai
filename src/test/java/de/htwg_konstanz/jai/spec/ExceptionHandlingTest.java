package de.htwg_konstanz.jai.spec;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg_konstanz.jai.ProgramBuilder;
import de.htwg_konstanz.jai.gen.ByteCodeClass;
import de.htwg_konstanz.jai.gen.Instruction;
import de.htwg_konstanz.jai.gen.Method;
import de.htwg_konstanz.jai.gen.Program;

public class ExceptionHandlingTest {

	@SuppressWarnings("unused")
	private static class TestClassException {

		void catchTest(int i) {
			a();
			try {
				b();
			} catch (NoSuchElementException e) {
				c();
			} catch (NoSuchMethodError e) {
				d();
			}
			e();
		}

		void catchFinallyTest(int i) {
			a();
			try {
				b();
			} catch (NoSuchElementException e) {
				c();
			} catch (NoSuchMethodError e) {
				d();
			} finally {
				e();
			}
			f();
		}

		void finallyTest(int i) {
			a();
			try {
				b();
			} finally {
				e();
			}
			f();
		}

		void catchIfTest(int i) {
			a();
			try {
				b();
				if (i < 0)
					;
			} catch (NoSuchElementException e) {
				c();
			}
			e();
		}

		void catchReturnTest(int i) {
			a();
			try {
				b();
				return;
			} catch (NoSuchElementException e) {
				c();
			}
			e();
		}

		void catchReturnTest2(int i) {
			a();
			try {
				// b();
				return;
			} catch (NoSuchElementException e) {
				c();
			} catch (NoSuchMethodError e) {
				d();
			} finally {
				e();
			}
			e();
		}

		void catchThrowTest(Exception x) throws Exception {
			a();
			try {
				// b();
				throw x;
			} catch (NoSuchElementException e) {
				c();
			}
			e();
		}

		private void a() {
		};

		private void b() {
		};

		private void c() {
		};

		private void d() {
		};

		private void e() {
		};

		private void f() {
		};

		private void g() {
		};
	}

	private static Program program;
	private static ByteCodeClass testClass;
	private static Method f;
	private static HashMap<String, Method> methods;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		program = new ProgramBuilder(
				"de.htwg_konstanz.jai.spec.ExceptionHandlingTest$TestClassException").build();

		testClass = program.getByteCodeClass(0);

		methods = new HashMap<String, Method>();
		for (Method method : testClass.getMethods())
			methods.put(method.getMethodName(), method);

	}

	@Test
	public void testCatchTest() throws Exception {
		Method method = methods.get("catchTest");
		System.out.println("\n" + method);
		print(method.entryPoint(), "");

		final int END_OF_TRY = 5;
		final int CATCH1 = 11;
		final int CATCH2 = 19;

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		Instruction instruction = instructions.get(END_OF_TRY);

		List<Integer> exceptionHandler = instruction.getExceptionHandlers();

		assertTrue(exceptionHandler.size() == 2);
		assertTrue(exceptionHandler.contains(CATCH1));
		assertTrue(exceptionHandler.contains(CATCH2));

	}

	@Test
	public void testCatchFinallyTest() throws Exception {
		Method method = methods.get("catchFinallyTest");
		System.out.println("\n" + method);
		print(method.entryPoint(), "");

		final int END_OF_TRY = 5;
		final int FINALLY = 35;
		final int CATCH1 = 11;
		final int CATCH2 = 23;

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		List<Integer> exceptionHandler = instructions.get(END_OF_TRY).getExceptionHandlers();

		assertTrue(exceptionHandler.size() == 2);
		assertTrue(exceptionHandler.contains(CATCH1));
		assertTrue(exceptionHandler.contains(CATCH2));

		exceptionHandler = instructions.get(CATCH1 + 2).getExceptionHandlers();

		assertTrue(exceptionHandler.size() == 1);
		assertTrue(exceptionHandler.contains(FINALLY));

		exceptionHandler = instructions.get(CATCH2 + 2).getExceptionHandlers();

		assertTrue(exceptionHandler.size() == 1);
		assertTrue(exceptionHandler.contains(FINALLY));
	}

	@Test
	public void testFinallyTest() throws Exception {
		Method method = methods.get("finallyTest");
		System.out.println("\n" + method);
		print(method.entryPoint(), "");

		final int END_OF_TRY = 8;
		final int FINALLY = 11;

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		List<Integer> exceptionHandler = instructions.get(END_OF_TRY).getExceptionHandlers();

		assertTrue(exceptionHandler.size() == 1);
		assertTrue(exceptionHandler.contains(FINALLY));
	}

	private void print(Instruction instruction, String tab) {
		System.out.println(tab + instruction.getLabel());
		for (Instruction i : instruction.succ()) {
			print(i, instruction.succ().size() > 1 ? " |" + tab : tab);
		}
	}
}
