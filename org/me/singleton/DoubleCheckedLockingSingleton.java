package org.me.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Singleton pattern example with Double checked Locking
 */

public class DoubleCheckedLockingSingleton {
	private static volatile DoubleCheckedLockingSingleton INSTANCE;

	private DoubleCheckedLockingSingleton() throws Exception {
		if (INSTANCE != null) {
			throw new Exception("DoubleCheckedLockingSingleton -> already initialized");
		}
	}

	public static DoubleCheckedLockingSingleton getInstance() throws Exception {
		if (INSTANCE == null) {
			synchronized (DoubleCheckedLockingSingleton.class) {
				// double checking Singleton instance
				if (INSTANCE == null) {
					INSTANCE = new DoubleCheckedLockingSingleton();
				}
			}
		}
		return INSTANCE;
	}

	public static void main(String... args) throws Exception {
		DoubleCheckedLockingSingleton instance1 = DoubleCheckedLockingSingleton.getInstance();
		System.out.println("instance1 -> " + instance1.hashCode());

		DoubleCheckedLockingSingleton instance2 = null;

		Constructor<?>[] declaredConstructors = DoubleCheckedLockingSingleton.class.getDeclaredConstructors();
		for (Constructor<?> constructor : declaredConstructors) {
			constructor.setAccessible(true);
			Object newInstance = constructor.newInstance(args);
			instance2 = (DoubleCheckedLockingSingleton) newInstance;
		}

		System.out.println("instance2 -> " + instance2.hashCode());
	}
}