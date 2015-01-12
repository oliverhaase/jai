package de.htwg_konstanz.jai;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AstConverterVisitorTest {

	private AstConverter astConverter;

	@Before
	public void setUp() throws Exception {
		JavaClass clazz = Repository
				.lookupClass("de.htwg_konstanz.jai.AstConverterVisitorTest$TestClass");
		astConverter = new AstConverter(clazz);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test() {
		astConverter.convert();
	}

	@SuppressWarnings("unused")
	private static class TestClass {

		public TestClass(int i, String s) {
		}

		public void methodVoidNoParam() {
			;
		}

		public void methodVoidBasicType(int i) {
			;
		}

		public void methodVoidBasicType(int i, double d) {
			;
		}

		public void methodVoidRefType(Object o) {
			;
		}

		public void methodVoidRefType(Object o, String s) {
			;
		}

		public void methodVoidArrayBasicType(int[] i) {
			;
		}

		public void methodVoidArrayRefType(String[] o) {
			;
		}

		public int methodBasicType(int i) {
			return i;
		}

		public String methodRefType(String s) {
			return s;
		}

		public static void staticMethod() {
			;
		}

		public static void staticMethodVoidBasicType(int i, double d) {
			;
		}

		public static void staticMethodVoidRefType(Object o, String s) {
			;
		}

		public static int staticMethodBasicType(int i) {
			return i;
		}

		public static String staticMethodRefType(String s) {
			return s;
		}
	}

}
