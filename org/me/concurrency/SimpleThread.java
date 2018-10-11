package org.me.concurrency;

import java.util.concurrent.Executors;

public class SimpleThread extends Thread {

	private String message;

	public SimpleThread(String message) {
		this.message = message;
	}

	// standard logger, constructor

	@Override
	public void run() {
		System.out.println(message);
	}

	public static void givenAThread_whenRunIt_thenResult(String message) throws Exception {

		Thread thread = new SimpleThread(message);
		thread.start();
		thread.join();
	}

	public static void givenAThread_whenSubmitToES_thenResult(String message) throws Exception {

		Executors.newSingleThreadExecutor().submit(new SimpleThread(message))
				.get();
	}

	public static void main(String... args) throws Exception {
		givenAThread_whenRunIt_thenResult("SimpleThread executed using Thread");
		givenAThread_whenSubmitToES_thenResult("SimpleThread executed using ExecutorService");
	}
}