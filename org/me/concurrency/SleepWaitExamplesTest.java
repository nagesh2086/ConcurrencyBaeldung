package org.me.concurrency;

public class SleepWaitExamplesTest {

	private static Object LOCK = new Object();

	public SleepWaitExamplesTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		sleepWaitExamples();
	}

	private static void sleepWaitExamples() throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("Thread '" + Thread.currentThread().getName() + "' is woken after sleeping for 1 second");
		synchronized (LOCK) {
			LOCK.wait(1000);
			System.out.println("Object '" + LOCK + "' is woken after" + " waiting for 1 second");
		}
	}

}
