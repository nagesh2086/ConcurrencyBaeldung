package org.me.concurrency;
//We want to test scheduleAtFixedRate and scheduleWithFixedDelay

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {

	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

		final long currentTimeMillis = System.currentTimeMillis();
		/*executor.scheduleAtFixedRate(
				() -> System.out.println("Fixed Rate Scheduled, " + (System.currentTimeMillis() - currentTimeMillis)),
				2000, 2000, TimeUnit.MILLISECONDS);*/

		executor.scheduleWithFixedDelay(
				() -> System.out.println("Fixed Delay Scheduled, " + (System.currentTimeMillis() - currentTimeMillis)),
				2000, 2000, TimeUnit.MILLISECONDS);
	}
}