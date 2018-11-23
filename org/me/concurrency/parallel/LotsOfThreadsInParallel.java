package org.me.concurrency.latch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Instead of blocking a parent thread until some child threads have finished,
 * we can block each child thread until all the others have started.
 * 
 * Our test blocks until - all the Workers have started, - unblocks the Workers,
 * - and then blocks until the Workers have finished:
 * 
 * @author kekannag
 *
 */
public class LotsOfThreadsInParallel {

	public static void main(String[] args) throws InterruptedException {
		List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch readyThreadCounter = new CountDownLatch(5);
		CountDownLatch callingThreadBlocker = new CountDownLatch(1);
		CountDownLatch completedThreadCounter = new CountDownLatch(5);
		List<Thread> workers = Stream.generate(() -> new Thread(
				new WaitingWorker(outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
				.limit(5).collect(Collectors.toList());

		workers.forEach(Thread::start);
		readyThreadCounter.await();
		outputScraper.add("Workers ready");
		callingThreadBlocker.countDown();
		completedThreadCounter.await();
		outputScraper.add("Workers completed");

		System.out.println(outputScraper);

	}

}