package org.me.concurrency.rev.schedule.executor.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleAtFixedRateTest {

	public static void main(String[] args) {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		int taskSleepingTime = 11000;
		Runnable command = new FixedRateTask("FixedRateTask", taskSleepingTime);

		System.out.println(Thread.currentThread().getName() + "  Submitted task --> " + System.currentTimeMillis());
		pool.scheduleAtFixedRate(command, 5, 10, TimeUnit.MILLISECONDS);

		awaitTerminationAfterTime(pool, 30000, taskSleepingTime);// taskSleepingTime is nothing but the
																	// awaitTerminatingTime
	}

	private static void awaitTerminationAfterTime(ScheduledExecutorService pool, int waitingTime,
			int awaitTerminatingTime) {
		try {
			Thread.sleep(waitingTime);
			System.out.println(Thread.currentThread().getName() + " requested shutdown()");
			pool.shutdown();
			while (!pool.awaitTermination(awaitTerminatingTime, TimeUnit.MILLISECONDS)) {
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

	private int sleepingTime;

	public FixedRateTask(String threadName, int sleepingTime) {
		Thread.currentThread().setName("Thread-" + threadName);
		this.sleepingTime = sleepingTime;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "  executed task --> " + System.currentTimeMillis());
		System.out.println(Thread.currentThread().getName() + " Jagate raho....");
		try {
			Thread.sleep(sleepingTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
