package org.me.concurrency.executor;

import java.util.concurrent.Callable;

public class DelayedCallable implements Callable<String> {

	private String message;
	private int millis;

	public DelayedCallable(String message, int millis) {
		this.message = message;
		this.millis = millis;
	}

	@Override
	public String call() throws Exception {
		System.out.println("waiting for " + millis);
		Thread.sleep(millis);
		return message;
	}

}
