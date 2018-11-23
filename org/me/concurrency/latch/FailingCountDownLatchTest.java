package org.me.concurrency.latch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FailingCountDownLatchTest {
	public static void main(String... args) throws InterruptedException {
		List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch countDownLatch = new CountDownLatch(5);
		List<Thread> workers = Stream.generate(() -> new Thread(new BrokenWorker(outputScraper, countDownLatch)))
				.limit(5).collect(Collectors.toList());

		workers.forEach(Thread::start);
		// countDownLatch.await(3, TimeUnit.SECONDS);
		System.out.println(countDownLatch.await(3, TimeUnit.SECONDS));
		// countDownLatch.await();
	}
}