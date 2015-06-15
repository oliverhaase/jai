package de.htwg_konstanz.jai.testClasses;

public abstract class AbstractClass {

	public static AbstractClass instance;

	public abstract AbstractClass delegate();

	public Object foo() {
		return delegate().foo();
	}

}
