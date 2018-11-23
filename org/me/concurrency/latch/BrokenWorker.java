package org.me.concurrency.latch;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BrokenWorker implements Runnable {
	private List<String> outputScraper;
	private CountDownLatch countDownLatch;

	BrokenWorker(List<String> outputScraper, CountDownLatch countDownLatch) {
		this.outputScraper = outputScraper;
		this.countDownLatch = countDownLatch;

	}

	@Override
	public void run() {
		if (true) {
			throw new RuntimeException("Oh dear, I'm a BrokenWorker");
		}
		countDownLatch.countDown();
		outputScraper.add("Counted down");
	}
}