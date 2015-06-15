package de.htwg_konstanz.jai.testClasses;

public class SubAbstractClass extends AbstractClass {
	public static AbstractClass instance;

	@Override
	public AbstractClass delegate() {
		return instance;
	}

	@Override
	public Object foo() {
		return new Object();
	}
}
