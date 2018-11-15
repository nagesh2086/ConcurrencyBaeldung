package org.me.concurrency.cyclic.barrier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3, () -> {
			System.out.println(Thread.currentThread().getName() + " Barrier action completed....");
		});

		Thread t1 = new Thread(new CBTask(barrier), "T1");
		Thread t2 = new Thread(new CBTask(barrier), "T2");
		Thread t3 = new Thread(new CBTask(barrier), "T3");
		Thread t4 = new Thread(new CBTask(barrier), "T4");

		if (!barrier.isBroken()) {
			t1.start();
			t2.start();
			t3.start();
			t4.start();
		}

		try {
			t4.join(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " Daemon thread ended");
		/*
		 * barrier.reset();
		 * 
		 * Thread t11 = new Thread(new CBTask(barrier), "T11"); Thread t22 = new
		 * Thread(new CBTask(barrier), "T22"); Thread t33 = new Thread(new
		 * CBTask(barrier), "T33");
		 * 
		 * if (!barrier.isBroken()) { t11.start(); t22.start(); t33.start(); }
		 */
	}

}
