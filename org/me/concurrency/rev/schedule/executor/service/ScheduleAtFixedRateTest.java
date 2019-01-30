package org.me.concurrency.rev.schedule.executor.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleAtFixedRateTest {

	public static void main(String[] args) {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		Runnable command = new FixedRateTask("FixedRateTask");
		pool.scheduleAtFixedRate(command, 5, 10, TimeUnit.MILLISECONDS);

		awaitTerminationAfterTime(pool, 30000);
	}

	private static void awaitTerminationAfterTime(ScheduledExecutorService pool, int waitingTime) {
		try {
			Thread.sleep(waitingTime);
			System.out.println(Thread.currentThread().getName() + " requested shutdown()");
			pool.shutdown();
			while (!pool.awaitTermination(3000, TimeUnit.MILLISECONDS)) {
				pool.shutdownNow();
				System.out.println(Thread.currentThread().getName() + " requested shutdownNow()");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pool.shutdownNow();
		}
	}

}

class FixedRateTask implements Runnable {

	public FixedRateTask(String threadName) {
		Thread.currentThread().setName("Thread-" + threadName);
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Jagate raho....");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
