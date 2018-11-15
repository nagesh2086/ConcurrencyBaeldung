package org.me.concurrency.cyclic.semaphore;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

//not understood :(
public class SemaphoreTest {

	private static Logger LOG = Logger.getLogger(SemaphoreTest.class.getName());
	private static Semaphore s = new Semaphore(5);

	public static void main(String[] args) throws InterruptedException {
		SemaphoreTest st = new SemaphoreTest();

		Thread t1 = new Thread(getRunnableImpl(st), "T1");
		Thread t2 = new Thread(getRunnableImpl(st), "T2");
		Thread t3 = new Thread(getRunnableImpl(st), "T3");
		Thread t4 = new Thread(getRunnableImpl(st), "T4");
		Thread t5 = new Thread(getRunnableImpl(st), "T5");
		Thread t6 = new Thread(getRunnableImpl(st), "T6");

		t1.start();
		t2.start();
		t3.start();
		/*t4.start();
		t5.start();
		t6.start();*/
	}

	private static Runnable getRunnableImpl(SemaphoreTest st) {
		final Runnable _runnable1 = new Runnable() {
			public void run() {
				try {
					st.execute();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		return _runnable1;
	}

	private void execute() throws InterruptedException {
		LOG.info(Thread.currentThread().getName() + "-> Before s.tryAcquire() [ " + s.availablePermits() + " ]");

		if (s.tryAcquire()) {
			LOG.info(Thread.currentThread().getName() + "-> After s.tryAcquire() [ " + s.availablePermits() + " ]");
			s.acquire();
			LOG.info(Thread.currentThread().getName() + "-> After s.acquire() [ " + s.availablePermits() + " ]");
			s.release();
		}

		LOG.info(Thread.currentThread().getName() + "-> After s.release() [ " + s.availablePermits() + " ]");
	}

}
