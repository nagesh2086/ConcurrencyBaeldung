package org.me.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableTEST implements Runnable {

	public static void main(String[] args) {
		/*String value = null;
		value.length();*/
		Thread t = new Thread(new RunnableTEST());
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(t);
		// t.start();
		executorService.shutdown();
	}

	@Override
	public void run() {
		String value = null;
		System.out.println("value -> " + value);
		System.out.println("RUNNTIME exceptions is getting supressed and "
				+ "\n please see in code that next line throw runtime NPE "
				+ "\n  However it will be suprresed and program is terminated.");
		value.length();
		value.indexOf("sxx");
		System.out.println("RunnableTEST xxxxxx");
		/*
		 * throw new NullPointerException("testing propagating RUNTIME exception from "
		 * + "Runnable.run()");
		 */
		System.out.println("RUNNTIME exceptions is getting supressed");
		/*
		 * throw new IOException("testing propagating CHECKED exception from " +
		 * "Runnable.run()");
		 */
	}

}