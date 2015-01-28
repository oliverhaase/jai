package de.htwg_konstanz.jai.spec;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg_konstanz.jai.ProgramBuilder;
import de.htwg_konstanz.jai.gen.BranchInstruction;
import de.htwg_konstanz.jai.gen.ByteCodeClass;
import de.htwg_konstanz.jai.gen.GotoInstruction;
import de.htwg_konstanz.jai.gen.Method;
import de.htwg_konstanz.jai.gen.Program;

public class DynamicOrderTest {

	private final static int ILOAD_1 = 1;
	private final static int IFLE = 2;
	private final static int ICONST_1 = 3;
	private final static int GOTO = 4;
	private final static int ICONST_M1 = 5;
	private final static int IRETURN = 6;

	@SuppressWarnings("unused")
	private static class TestClass {
		int f(int n) {
			return (n > 0) ? 1 : -1;
		}
	}

	private static Program program;
	private static ByteCodeClass testClass;
	private static Method f;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		program = new ProgramBuilder("de.htwg_konstanz.jai.spec.DynamicOrderTest$TestClass")
				.build();

		testClass = program.getByteCodeClass(0);

		for (Method method : testClass.getMethods())
			if (method.getMethodName().equals("f"))
				f = method;
	}

	@Test
	public void testTarget() {
		assertEquals(((GotoInstruction) f.getInstruction(GOTO)).target(), f.getInstruction(IRETURN));
	}

	@Test
	public void testTargets() {
		assertEquals(((BranchInstruction) f.getInstruction(IFLE)).targets().size(), 1);
		assertTrue(((BranchInstruction) f.getInstruction(IFLE)).targets().contains(
				f.getInstruction(ICONST_M1)));
	}

	// succ() attribute

	@Test
	public void testSuccEntryPoint() {
		assertEquals(f.entryPoint().succ().size(), 1);
		assertTrue(f.entryPoint().succ().contains(f.getInstruction(ILOAD_1)));
	}

	@Test
	public void testSuccILOAD_1() {
		assertEquals(f.getInstruction(ILOAD_1).succ().size(), 1);
		assertTrue(f.getInstruction(ILOAD_1).succ().contains(f.getInstruction(IFLE)));
	}

	@Test
	public void testSuccIFLE() {
		assertEquals(f.getInstruction(IFLE).succ().size(), 2);
		assertTrue(f.getInstruction(IFLE).succ().contains(f.getInstruction(ICONST_1)));
		assertTrue(f.getInstruction(IFLE).succ().contains(f.getInstruction(ICONST_M1)));
	}

	@Test
	public void testSuccICONST_1() {
		assertEquals(f.getInstruction(ICONST_1).succ().size(), 1);
		assertTrue(f.getInstruction(ICONST_1).succ().contains(f.getInstruction(GOTO)));
	}

	@Test
	public void testSuccGOTO() {
		assertEquals(f.getInstruction(GOTO).succ().size(), 1);
		assertTrue(f.getInstruction(GOTO).succ().contains(f.getInstruction(IRETURN)));
	}

	@Test
	public void testSuccICONST_M1() {
		assertEquals(f.getInstruction(ICONST_M1).succ().size(), 1);
		assertTrue(f.getInstruction(ICONST_M1).succ().contains(f.getInstruction(IRETURN)));
	}

	@Test
	public void testSuccIRETURN() {
		assertEquals(f.getInstruction(IRETURN).succ().size(), 1);
		assertTrue(f.getInstruction(IRETURN).succ().contains(f.exitPoint()));
	}

	@Test
	public void testSuccExitPoint() {
		assertTrue(f.exitPoint().succ().isEmpty());
	}

	// pred() attribute

	@Test
	public void testPredEntryPoint() {
		assertTrue(f.entryPoint().pred().isEmpty());
	}

	@Test
	public void testPredILOAD_1() {
		assertEquals(f.getInstruction(ILOAD_1).pred().size(), 1);
		assertTrue(f.getInstruction(ILOAD_1).pred().contains(f.entryPoint()));
	}

	@Test
	public void testPredIFLE() {
		assertEquals(f.getInstruction(IFLE).pred().size(), 1);
		assertTrue(f.getInstruction(IFLE).pred().contains(f.getInstruction(ILOAD_1)));
	}

	@Test
	public void testPredICONST_1() {
		assertEquals(f.getInstruction(ICONST_1).pred().size(), 1);
		assertTrue(f.getInstruction(ICONST_1).pred().contains(f.getInstruction(IFLE)));
	}

	@Test
	public void testPredGOTO() {
		assertEquals(f.getInstruction(GOTO).pred().size(), 1);
		assertTrue(f.getInstruction(GOTO).pred().contains(f.getInstruction(ICONST_1)));
	}

	@Test
	public void testPredICONST_M1() {
		assertEquals(f.getInstruction(ICONST_M1).pred().size(), 1);
		assertTrue(f.getInstruction(ICONST_M1).pred().contains(f.getInstruction(IFLE)));
	}

	@Test
	public void testPredIRETURN() {
		assertEquals(f.getInstruction(IRETURN).pred().size(), 2);
		assertTrue(f.getInstruction(IRETURN).pred().contains(f.getInstruction(ICONST_M1)));
		assertTrue(f.getInstruction(IRETURN).pred().contains(f.getInstruction(GOTO)));
	}

	@Test
	public void testPredExitPoint() {
		assertEquals(f.exitPoint().pred().size(), 1);
		assertTrue(f.exitPoint().pred().contains(f.getInstruction(IRETURN)));
	}
}
