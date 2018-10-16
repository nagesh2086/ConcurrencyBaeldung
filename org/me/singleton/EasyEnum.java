package org.me.singleton;

/**
 * Singleton pattern example using Java Enum.
 * 
 * Thread-safety is guranteed for creation of instance by JVM.
 * 
 * Non-Cloneable is guranteed by throwing CloneNotSupportedException from
 * clone() inside Enum class.
 * 
 * Instance through de-serialization - Serialization is guarnateed by JVM for Enum.
 * 
 * Instance through reflection - java ensures internally that enum value is instantiated only once.
 * 
 * @author kekannag
 *
 */
public enum EasyEnum {
	INSTANCE;

	public static void main(String[] args) throws CloneNotSupportedException {
		System.out.println(EasyEnum.INSTANCE + "\n");
		System.out.println(EasyEnum.INSTANCE.hashCode() + "\n");
		System.out.println(EasyEnum.INSTANCE.clone());
	}

}
