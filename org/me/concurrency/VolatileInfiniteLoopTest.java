package org.me.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileInfiniteLoopTest {

	volatile private boolean isActive = false;

	public void printMessage() {
		while (!isActive) {
			System.out.println(Thread.currentThread().getName() + " is executing printMessage()");
		}
	}

	public void negateIsActive() {
		this.isActive = !this.isActive;
		System.out.println(Thread.currentThread().getName() + " changed value of isActive --> " + this.isActive);
	}

	public static void main(String[] args) {
		VolatileInfiniteLoopTest v = new VolatileInfiniteLoopTest();
		ExecutorService pool = Executors.newFixedThreadPool(10);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);

		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);
		
		pool.submit(v::negateIsActive);
		pool.submit(v::negateIsActive);
		pool.submit(v::printMessage);
		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);
		pool.submit(v::printMessage);
		pool.submit(v::negateIsActive);
		pool.submit(v::negateIsActive);
		
		pool.shutdown();
	}

}
