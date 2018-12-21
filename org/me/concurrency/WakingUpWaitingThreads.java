package org.me.concurrency;

/**
 * TaskThread - having sum and incrementing it and then notify
 * 
 * ReadThreadA - trying to read sum of Task and if sum == 0 then waiting for
 * Task thread to complete it's work.
 * 
 * ReadThreadB - same as ReadThreadA.
 * 
 * @author kekannag
 *
 */
public class WakingUpWaitingThreads {

	public static void main(String[] args) throws InterruptedException {
		final Object LOCK = new Object();
		final Task t = new Task(LOCK);
		final ReadThreadA a = new ReadThreadA(t, LOCK);
		final ReadThreadB b = new ReadThreadB(t, LOCK);

		Thread r1 = new Thread(a, "ReadThreadA-1");
		r1.start();

		Thread r2 = new Thread(b, "ReadThreadB-2");
		r2.start();

		Thread.sleep(2000);
		Thread t1 = new Thread(t, "TaskThread");
		t1.start();
	}

}

class Task implements Runnable {
	private int sum;
	private final Object LOCK;

	public Task(Object LOCK) {
		this.LOCK = LOCK;
	}

	public int getSum() {
		return sum;
	}

	@Override
	public void run() {
		synchronized (LOCK) {
			while (sum < 100000) {
				sum += 1;
			}
			System.out.println(Thread.currentThread().getName() + " - SUM has been incremented and notifying all ");
			LOCK.notifyAll();
		}
	}

}

class ReadThreadA implements Runnable {
	private final Task t;
	private Object LOCK;

	public ReadThreadA(final Task t, Object LOCK) {
		this.t = t;
		this.LOCK = LOCK;
	}

	@Override
	public void run() {
		synchronized (LOCK) {
			try {
				if (t.getSum() == 0) {
					System.out.println(Thread.currentThread().getName() + " waiting Task thread to finish ");
					LOCK.wait();
				}
				System.out.println(Thread.currentThread().getName() + " waiting over and sum is " + t.getSum());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class ReadThreadB implements Runnable {
	private final Task t;
	private Object LOCK;

	public ReadThreadB(final Task t, Object LOCK) {
		this.t = t;
		this.LOCK = LOCK;
	}

	@Override
	public void run() {
		synchronized (LOCK) {
			try {
				if (t.getSum() == 0) {
					System.out.println(Thread.currentThread().getName() + " waiting Task thread to finish ");
					LOCK.wait();
				}
				System.out.println(Thread.currentThread().getName() + " waiting over and sum is " + t.getSum());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
