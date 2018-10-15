package org.me.singleton;

/**
* Singleton pattern example with static factory method
*/

public class Singleton{
    //initailzed during class loading
    private static final Singleton INSTANCE = new Singleton();
  
    //to prevent creating another instance of Singleton
    private Singleton(){}

    public static Singleton getSingleton(){
        return INSTANCE;
    }
    
    public static void main(String... args) {
		System.out.println(Singleton.INSTANCE);
		System.out.println(Singleton.getSingleton());
		System.out.println(Singleton.INSTANCE);
	}
}