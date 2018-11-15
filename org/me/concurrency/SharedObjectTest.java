package org.me.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class SharedObject {
	private volatile int count = 0;

	public synchronized void increamentCount() {
		count++;
	}

	public int getCount() {
		return count;
	}
}

public class SharedObjectTest {
	public static void main(String... args) throws InterruptedException {
		SharedObject _sObj = new SharedObject();

		ExecutorService service = Executors.newFixedThreadPool(10);

		IntStream.range(0, 1000).forEach(count -> service.submit(_sObj::increamentCount));

		service.awaitTermination(1000, TimeUnit.MILLISECONDS);

		System.out.println(_sObj.getCount());

		service.shutdownNow();
	}
}