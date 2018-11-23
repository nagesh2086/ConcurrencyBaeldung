package org.me.concurrency.latch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountDownLatchParallelProcessing {

	public static void main(String[] args) throws InterruptedException {
		List<String> outputScrapper = Collections.synchronizedList(new ArrayList<>());

		CountDownLatch latch = new CountDownLatch(5);

		Stream<Thread> streamOfTh = Stream.generate(() -> new Thread(new Worker(outputScrapper, latch)));
		streamOfTh = streamOfTh.limit(5);
		List<Thread> threadList = streamOfTh.collect(Collectors.toList());

		threadList.forEach(Thread::start);

		latch.await();

		outputScrapper.add("Latch Relaeased!!");
		System.out.println(outputScrapper);
	}

}
