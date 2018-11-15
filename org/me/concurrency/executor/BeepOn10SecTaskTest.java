package org.me.concurrency.executor;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * Here is a class with a method that sets up a ScheduledExecutorServiceto beep
 * every ten seconds for an hour
 * 
 * @author kekannag
 *
 */
public class BeepOn10SecTaskTest {

	public static void main(String[] args) {
		BeepControl bc = new BeepControl();
		// bc.beepOn10Seconds();
		bc.beepForAnHour();
	}
}

class BeepControl {
	// private ScheduledExecutorService scheduler =
	// Executors.newScheduledThreadPool(1);

	/*
	 * protected void beepOn10Seconds() { Runnable beeper = new Runnable() { public
	 * void run() { System.out.println("Beep beep..."); } };
	 * 
	 * ScheduledFuture<?> beepHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10,
	 * TimeUnit.SECONDS);
	 * 
	 * Runnable runnable = new Runnable() { public void run() {
	 * beepHandle.cancel(true); } };
	 * 
	 * scheduler.schedule(runnable, 10 * 60, TimeUnit.SECONDS);
	 * 
	 * 
	 * }
	 */

	private final ScheduledExecutorService beepScheduler = Executors.newScheduledThreadPool(10);
	private final ScheduledExecutorService beepCancelScheduler = Executors.newScheduledThreadPool(10);

	public void beepForAnHour() {
		final Runnable beeper = new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		};

		final ScheduledFuture beeperHandle = beepScheduler.scheduleAtFixedRate(beeper, 0, 10, SECONDS);

		final Runnable canceller = new Runnable() {
			public void run() {
				// while(!beeperHandle.isDone()) {
				System.out.println("trying to cancel...");
				beeperHandle.cancel(true);
				// }
			}
		};

		/*
		 * scheduler.schedule(new Runnable() { public void run() {
		 * while(!beeperHandle.isDone()) { beeperHandle.cancel(true); } } }, 3 * 60,
		 * SECONDS);
		 */

		final ScheduledFuture beeperCancelHandler = beepScheduler.scheduleAtFixedRate(canceller, 0, 10, SECONDS);
	}
}
