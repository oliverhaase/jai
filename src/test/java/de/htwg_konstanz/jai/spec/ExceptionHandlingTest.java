package de.htwg_konstanz.jai.spec;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

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
				e();
			} catch (NoSuchElementException e) {
				d();
			}
			e();
		}

		void catch2Test(int i) {
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
	public void catchTest() throws Exception {
		Method method = methods.get("catchTest");

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		final Integer[] TRY = { 4, 5, 8, 9 };
		final Integer[] CATCH = { 15 };
		final Integer[] NOT_TRY = getNotTry(instructions, TRY);

		for (int instructionPosition : TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(CATCH.length, exceptionHandler.size());
			assertTrue(exceptionHandler.contains(CATCH[0]));
		}

		for (int instructionPosition : NOT_TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(0, exceptionHandler.size());
		}

		Instruction instruction = instructions.get(9);
		Iterator<Instruction> successors = instruction.succ().iterator();
		assertEquals(15, successors.next().getPosition());
		assertEquals(12, successors.next().getPosition());

	}

	@Test
	public void catch2Test() throws Exception {
		Method method = methods.get("catch2Test");
		System.out.println("\n" + method);
		print(method.entryPoint(), "");

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		final Integer[] TRY = { 4, 5 };
		final Integer[] CATCH = { 11, 19 };
		final Integer[] NOT_TRY = getNotTry(instructions, TRY);

		for (int instructionPosition : TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(CATCH.length, exceptionHandler.size());
			assertTrue(exceptionHandler.contains(CATCH[0]));
			assertTrue(exceptionHandler.contains(CATCH[1]));
		}

		for (int instructionPosition : NOT_TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(0, exceptionHandler.size());
		}

	}

	@Test
	public void catchFinallyTest() throws Exception {
		Method method = methods.get("catchFinallyTest");
		System.out.println("\n" + method);
		print(method.entryPoint(), "");

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		final Integer[] TRY1 = { 4, 5 };
		final Integer[] TRY2 = { 8, 11, 12, 13 };
		final Integer[] TRY3 = { 23, 24, 25 };
		final int CATCH1 = 11;
		final int CATCH2 = 23;
		final int FINALLY = 35;
		final Integer[] NOT_TRY = getNotTry(instructions, new Integer[][] { TRY1, TRY2, TRY3 });

		for (int instructionPosition : TRY1) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(3, exceptionHandler.size());
			assertTrue(exceptionHandler.contains(CATCH1));
			assertTrue(exceptionHandler.contains(CATCH2));
			assertTrue(exceptionHandler.contains(FINALLY));
		}

		for (int instructionPosition : TRY2) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(1, exceptionHandler.size());
			assertTrue(exceptionHandler.contains(FINALLY));
		}

		for (int instructionPosition : TRY3) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(1, exceptionHandler.size());
			assertTrue(exceptionHandler.contains(FINALLY));
		}

		for (int instructionPosition : NOT_TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(0, exceptionHandler.size());
		}
	}

	@Test
	public void finallyTest() throws Exception {
		Method method = methods.get("finallyTest");
		System.out.println("\n" + method);
		print(method.entryPoint(), "");

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		final Integer[] TRY1 = { 4, 5, 8 };
		final int FINALLY = 11;
		final Integer[] NOT_TRY = getNotTry(instructions, new Integer[][] { TRY1 });

		for (int instructionPosition : TRY1) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(1, exceptionHandler.size());
			assertTrue(exceptionHandler.contains(FINALLY));
		}

		for (int instructionPosition : NOT_TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(0, exceptionHandler.size());
		}
	}

	@Test
	public void throwTest() throws Exception {
		Method method = methods.get("catchThrowTest");

		HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
		for (Instruction i : method.getInstructions())
			instructions.put(i.getPosition(), i);

		final Integer[] TRY = { 4, 5 };
		final Integer[] CATCH = { 6 };
		final Integer[] NOT_TRY = getNotTry(instructions, TRY);

		for (int instructionPosition : TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(CATCH.length, exceptionHandler.size());
			assertTrue(exceptionHandler.contains(CATCH[0]));
		}

		for (int instructionPosition : NOT_TRY) {
			Set<Integer> exceptionHandler = instructions.get(instructionPosition)
					.getExceptionHandlers();

			assertEquals(0, exceptionHandler.size());
		}
	}

	private void print(Instruction instruction, String tab) {
		System.out.println(tab + instruction.getLabel());
		for (Instruction i : instruction.succ()) {
			print(i, instruction.succ().size() > 1 ? " |" + tab : tab);
		}
	}

	private Integer[] getNotTry(HashMap<Integer, Instruction> instructions, Integer[] trys) {
		return getNotTry(instructions, new Integer[][] { trys });
	}

	private Integer[] getNotTry(HashMap<Integer, Instruction> instructions, Integer[][] trys) {
		Set<Integer> all = new HashSet<Integer>(instructions.keySet());
		for (Integer[] t : trys) {
			all.removeAll(Arrays.asList(t));
		}
		return all.toArray(new Integer[0]);
	}
}
