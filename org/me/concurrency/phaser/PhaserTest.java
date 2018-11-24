package org.me.concurrency.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/* Phase API simple example */
class PhaserTest {
	public static void main(String... args) {
		ExecutorService service = Executors.newCachedThreadPool();
		Phaser ph = new Phaser(1);
		// System.out.println("This is phase-" + ph.getPhase());

		service.submit(new LongRunningAction("action-1", ph));
		service.submit(new LongRunningAction("action-2", ph));
		service.submit(new LongRunningAction("action-3", ph));

		// System.out.println("This is phase-" + ph.getPhase());
		ph.arriveAndAwaitAdvance();

		service.submit(new LongRunningAction("action-4", ph));
		service.submit(new LongRunningAction("action-5", ph));

		// System.out.println("This is phase-" + ph.getPhase());
		ph.arriveAndAwaitAdvance();

		ph.arriveAndDeregister();
	}
}

class LongRunningAction implements Runnable {
	private String threadName;
	private Phaser ph;

	LongRunningAction(String threadName, Phaser ph) {
		this.threadName = threadName;
		this.ph = ph;
		// System.out.println("This is phase-" + ph.register());
	}

	@Override
	public void run() {
		System.out.println("This is phase " + ph.getPhase());
		System.out.println("Thread " + threadName + " before long running action");
		ph.arriveAndAwaitAdvance();
		try {
			Thread.sleep(20);
			System.out.println("Thread " + threadName + " executed in phase-" + ph.getPhase());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ph.arriveAndDeregister();
	}
}