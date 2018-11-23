package org.me.concurrency.latch;

import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {
	private java.util.List<String> outputScrapper;
	private CountDownLatch latch;

	public Worker(java.util.List<String> outputScrapper, CountDownLatch latch) {
		this.outputScrapper = outputScrapper;
		this.latch = latch;
	}

	@Override
	public void run() {
		doSomeWork();
		outputScrapper.add(Thread.currentThread().getName() + " counted down");
		latch.countDown();
	}

	private void doSomeWork() {
		System.out.println(Thread.currentThread().getName() + " working working working......");
	}

}
