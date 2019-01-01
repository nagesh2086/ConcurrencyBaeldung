package org.me.concurrency.rev.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class LongRunningActionTest {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		Phaser ph = new Phaser(1);
		// ph.register();

		service.submit(new LongRunningAction("thread-1", ph));
		service.submit(new LongRunningAction("thread-2", ph));
		service.submit(new LongRunningAction("thread-3", ph));
		ph.arriveAndAwaitAdvance();

		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName() + " has finished phase - " + ph.getPhase());

		service.submit(new LongRunningAction("thread-4", ph));
		service.submit(new LongRunningAction("thread-5", ph));
		ph.arriveAndAwaitAdvance();

		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName() + " has finished phase - " + ph.getPhase());

		ph.arriveAndDeregister();
		System.out.println(
				Thread.currentThread().getName() + " shutting down phase - " + (ph.getPhase() + Integer.MIN_VALUE));
	}
}

class LongRunningAction implements Runnable {
	private String threadName;
	private Phaser ph;

	LongRunningAction(String threadName, Phaser ph) {
		this.threadName = threadName;
		this.ph = ph;
		ph.register();
		System.out.println(threadName + " - This is phase - " + ph.getPhase());
	}

	@Override
	public void run() {
		// System.out.println(Thread.currentThread().getName() + " - before
		// execution phase - " + ph.getPhase());
		ph.arriveAndAwaitAdvance();
		try {
			System.out.println(Thread.currentThread().getName() + " - in execution phase - " + ph.getPhase());
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ph.arriveAndDeregister();
	}
}