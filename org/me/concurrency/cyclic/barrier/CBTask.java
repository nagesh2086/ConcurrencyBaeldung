package org.me.concurrency.cyclic.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CBTask implements Runnable {

	private final CyclicBarrier barrier;

	public CBTask(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
			System.out.println(Thread.currentThread().getName() + " is waiting...");
			barrier.await();
			System.out.println(Thread.currentThread().getName() + " is realeased hurray...");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

}
