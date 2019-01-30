package org.me.concurrency.rev.schedule.executor.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Test - Executors.scheduleWithFixedDelay method
 * 
 * Target is to compare with Executors.scheduleAtFixedRate()
 * 
 * parameters - 5, 10, TimeUnit.SECONDS task will be sleeping for 5000 ms means
 * 5 Seconds
 * 
 * expected - 0 - 5(1 task started) - 10(1 task ended) - 20(2 task started) -
 * 25(2 task ended) - 35(3 task started)....so on.
 * 
 * The expected Delay is (20-10)=10 Seconds.
 * 
 * variable - total time taken of each task like starting T1 - ending T2 i.e. 5
 * Seconds
 * 
 * Actual delays is 5 seconds means (20-10) = 10 Seconds
 * 
 * Interesting.....
 **/
public class ScheduleWithFixedDelayTest {

	public static void main(String[] args) {
		int taskSleepingTime = 5000;
		Runnable command = new FixedDelayTask("FixedRateTask", taskSleepingTime);

		System.out.println(ThreadNameFinder.threadName() + "  Submitted task on " + System.currentTimeMillis());

		final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		pool.scheduleWithFixedDelay(command, 5, 10, TimeUnit.SECONDS);

		int taskTerminationSleepingTime = 50000;
		int taskTerminationAwaitingTime = taskSleepingTime;
		awaitTerminationAfterTime(pool, taskTerminationSleepingTime, taskTerminationAwaitingTime);
	}

	private static void awaitTerminationAfterTime(ScheduledExecutorService pool, int terminationTaskSleepingTime,
			int taskTerminationAwaitingTime) {
		try {
			Thread.sleep(terminationTaskSleepingTime);
			System.out.println(ThreadNameFinder.threadName() + " requested shutdown()");
			pool.shutdown();
			while (!pool.awaitTermination(taskTerminationAwaitingTime, TimeUnit.MILLISECONDS)) {
				pool.shutdownNow();
				System.out.println(ThreadNameFinder.threadName() + " requested shutdownNow()");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pool.shutdownNow();
		}
	}

}

class FixedDelayTask implements Runnable {

	private int sleepingTime;
	private static int counter = 0;

	public FixedDelayTask(String threadName, int sleepingTime) {
		Thread.currentThread().setName("Thread-" + threadName);
		this.sleepingTime = sleepingTime;
	}

	@Override
	public void run() {
		System.out.println(
				counter + " -" + ThreadNameFinder.threadName() + "  executed task on " + System.currentTimeMillis());
		System.out.println(counter + " -" + ThreadNameFinder.threadName() + " Jagate raho....");
		try {
			Thread.sleep(sleepingTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(
				counter++ + " -" + ThreadNameFinder.threadName() + "  ended task on " + System.currentTimeMillis());
	}

}

class ThreadNameFinder {
	static String threadName() {
		return Thread.currentThread().getName();
	}
}
