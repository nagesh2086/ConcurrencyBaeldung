package org.me.concurrency;

/**
 * Test join method - When we invoke the join() method on a thread, the calling
 * thread goes into a waiting state. It remains in a waiting state until the
 * referenced thread terminates.
 **/
public class SampleThreadTest {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new SampleThread(100), "thread-1_");
		t.start();
		System.out.println(Thread.currentThread().getName() + " waiting because of join");
		t.join(-1);
		System.out.println(Thread.currentThread().getName() + " back from join and done");
	}

}

class SampleThread implements Runnable {
	private int processingCount = 0;

	SampleThread(int processingCount) {
		this.processingCount = processingCount;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " started");
		while (processingCount > 0) {
			try {

				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " interrupted");
			}
			processingCount--;
		}
		System.out.println(Thread.currentThread().getName() + " exited");
	}
}