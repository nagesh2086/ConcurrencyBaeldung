package org.me.concurrency.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * scheduleAtFixedRate() -> will execute a task after an initial delay of 100
 * milliseconds, and after that, it will execute the same task every 450
 * milliseconds. If the processor needs more time to execute an assigned task
 * than the period parameter of the scheduleAtFixedRate() method, the
 * ScheduledExecutorService will wait until the current task is completed before
 * starting the next
 * 
 * scheduleWithFixedDelay() -> If it is necessary to have a fixed length delay
 * between iterations of the task, scheduleWithFixedDelay() should be used. For
 * example, the following code will guarantee a xxx-millisecond pause between
 * the end of the current execution and the start of another one.
 * 
 * @author kekannag
 *
 */
public class ScheduleAtFixedRateOrWithFixedDelayTest {

	public static void main(String[] args) {
		// createTask(5000); to understand scheduleWithFixedRate()
		Runnable r1 = createTask(0); // to understand scheduleWithFixedDelay()
		Runnable r2 = createTask(0);
		Runnable r3 = createTask(2000);
		Runnable r4 = createTask(3000);
		Runnable r5 = createTask(4000);

		ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
		service.scheduleWithFixedDelay(r1, 2, 3, TimeUnit.SECONDS);
		// service.scheduleAtFixedRate(r2, 2, 2, TimeUnit.SECONDS);
		// service.scheduleAtFixedRate(r3, 2, 2, TimeUnit.SECONDS);
		// service.scheduleAtFixedRate(r4, 2, 2, TimeUnit.SECONDS);
		// service.scheduleAtFixedRate(r5, 2, 2, TimeUnit.SECONDS);
		// scheduleWithFixedDelay
		try {
			System.out.println(Thread.currentThread().getName() + " Task " + " -> going to sleep");
			Thread.sleep(30000);
			System.out.println(Thread.currentThread().getName() + " Task " + " -> woken up from sleep");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		service.shutdown();
	}

	private static Runnable createTask(long millis) {
		return () -> {
			try {
				System.out.println(Thread.currentThread().getName() + " Task " + " -> going to sleep");
				Thread.sleep(millis);
				System.out.println(Thread.currentThread().getName() + " Task " + " -> woken up from sleep");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}

}
