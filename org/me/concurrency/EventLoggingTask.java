package org.me.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EventLoggingTask implements Runnable {

	private ExecutorService es;

	@Override
	public void run() {
		System.out.println("Message: EventLoggingTask");
	}

	public void executeTask() {
		es = Executors.newSingleThreadExecutor();
		Future future = es.submit(new EventLoggingTask());
		es.shutdown();
	}

	public static void main(String... args) {
		EventLoggingTask obj = new EventLoggingTask();
		obj.executeTask();
	}
}