package de.htwg_konstanz.jai;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public final class ClassPath {
	private static ClassPath instance;

	private Set<URL> urls;
	private Reflections reflections;

	private ClassPath() {
		urls = ClasspathHelper.forClassLoader();

		addPlattformClasses();

		reflections = new Reflections(new ConfigurationBuilder().setScanners(
				new SubTypesScanner(false)).setUrls(urls));
	}

	public static ClassPath getInstance() {
		if (instance == null) {
			instance = new ClassPath();
		}
		return instance;
	}

	/**
	 * Get all subtypes of {@code type} in the current Classpath.
	 */
	public Set<String> getSubTypesOf(String type) {
		Set<String> types = reflections.getStore().getSubTypesOf(type); // getSubTypesOf(Class.forName(type));
		return types;
	}

	public Set<String> getClassesOfPackage(String packageName) {
		Set<String> types = reflections.getStore().getSubTypesOf("java.lang.Object");
		Set<String> result = new HashSet<String>();
		for (String clazz : types) {
			if (clazz.startsWith(packageName))
				result.add(clazz);
		}
		return result;
	}

	public static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
			// return Class.forName(className, false, null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new AssertionError(className + " not found");
		}
	}

	public static Class<?> getClassIfAvailable(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException | LinkageError e) {
			System.out.println("Class not in current ClassPath: " + className);
			return null;
		}
	}

	private void addPlattformClasses() {
		File file = new File(System.getProperty("java.home").replaceAll("\\\\", "/") + "/lib/");
		List<String> jars = getJarsInDir(file);
		try {
			for (String string : jars) {
				urls.add(new URL("file:\\\\\\" + string));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private List<String> getJarsInDir(File directory) {
		List<String> jars = new ArrayList<String>();

		if (!directory.exists())
			return jars;

		File[] files = directory.listFiles();
		for (File file : files) {
			String fileName = file.getAbsolutePath();
			if (file.isDirectory())
				jars.addAll(getJarsInDir(file));
			else if (fileName.endsWith(".jar"))
				jars.add(fileName);
		}
		return jars;
	}
}
