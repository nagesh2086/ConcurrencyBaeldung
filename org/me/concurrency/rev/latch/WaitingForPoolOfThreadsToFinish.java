package org.me.concurrency.rev.latch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Waiting for the pool of threads to finish.
 * meaning - Instead of blocking parent thread untill all child threads have completed.
 */
public class WaitingForPoolOfThreadsToFinish {

	public static void main(String args[]) throws InterruptedException {
		final List<String> outputScraper = Collections.synchronizedList(new ArrayList<String>());
		final CountDownLatch latch = new CountDownLatch(2);

		List<Thread> workers = Stream.generate(() -> new Thread(new Worker(outputScraper, latch))).limit(5)
				.collect(Collectors.toCollection(ArrayList::new));

		workers.stream().forEach(Thread::start);

		latch.await();

		System.out.println(Thread.currentThread().getName() + " - finished");
	}

}

class Worker implements Runnable {
	private final List<String> outputScraper;
	private final CountDownLatch latch;

	Worker(List<String> outputScraper, CountDownLatch latch) {
		this.outputScraper = outputScraper;
		this.latch = latch;
	}

	@Override
	public void run() {
		doSomeWork();
		System.out.println(Thread.currentThread().getName() + " - counted down");
		latch.countDown();
	}

	private void doSomeWork() {
		// System.out.println(Thread.currentThread().getName() + " - before count down -
		// " + outputScraper);
	}
}
