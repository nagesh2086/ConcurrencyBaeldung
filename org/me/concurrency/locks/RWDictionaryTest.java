package org.me.concurrency.locks;

public class RWDictionaryTest {

	public static void main(String[] args) {
		Data d1 = new Data("Nagesh", 32);
		Data d2 = new Data("Nageshx", 33);
		Data d3 = new Data("Nageshxx", 34);
		Data d4 = new Data("Nageshxxx", 35);
		Data d5 = new Data("Nageshxxxx", 36);
		Data d6 = new Data("Nageshxxxxx", 37);
		Data d7 = new Data("Nageshxxxxxx", 38);

		RWDictionary rwDict = new RWDictionary();
		System.out.println(rwDict.allKeys());
		System.out.println(rwDict.get("Nageshxxx"));
		rwDict.put("Nagesh", d1);
		rwDict.put("Nageshx", d2);
		rwDict.put("Nageshxx", d3);
		System.out.println(rwDict.allKeys());
		System.out.println(rwDict.get("Nageshxx"));
		rwDict.put("Nageshxxx", d4);
		rwDict.put("Nageshxxxx", d5);
		System.out.println(rwDict.get("Nageshxxxx"));
		rwDict.put("Nageshxxxxx", d6);
		rwDict.put("Nageshxxxxxx", d7);
		System.out.println(rwDict.allKeys());
		System.out.println(rwDict.put("Nageshxxxxx", d7));
		rwDict.clear();
		System.out.println(rwDict.allKeys());
	}

}
