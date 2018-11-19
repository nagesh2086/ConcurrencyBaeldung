package org.me.concurrency;

import java.util.concurrent.atomic.AtomicBoolean;

public class ControlSubThreadTest {

	public static void main(String... args) {
		Thread.currentThread().setName("me_0");

		ControlSubThread csth = new ControlSubThread(2000);
		csth.start();

		try {
			System.out.println(Thread.currentThread().getName() + " going for sleep");
			Thread.sleep(15000);
			System.out.println(Thread.currentThread().getName() + " woked up from sleep");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Thread was interrupted, Failed to complete operation");
		}

		// csth.stop();//TEST: stop the thread

		// TEST: interrupt the thread
		while (csth.isRunning()) {
			csth.interrupt();
		}
	}
}

class ControlSubThread implements Runnable {

	private Thread worker;
	private final AtomicBoolean running = new AtomicBoolean(false);
	private int interval;

	public ControlSubThread(int sleepInterval) {
		interval = sleepInterval;
	}

	public void start() {
		worker = new Thread(this, "ControlSubThread_0");
		worker.start();
	}

	public void stop() {
		running.set(false);
	}

	public void interrupt() {
		running.set(false);
		worker.interrupt();
	}

	public boolean isRunning() {
		return running.get();
	}

	public boolean isStopped() {
		return !running.get();
	}

	public void run() {
		running.set(true);
		while (running.get()) {
			try {
				System.out.println(Thread.currentThread().getName() + " going for sleep");
				Thread.sleep(interval);
				System.out.println(Thread.currentThread().getName() + " woked up from sleep");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Thread was interrupted, Failed to complete operation");
			}
			// do something here
		}
	}
}