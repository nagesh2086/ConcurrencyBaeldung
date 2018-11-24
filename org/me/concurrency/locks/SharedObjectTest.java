package org.me.concurrency.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Example ReentrantLock
 **/
public class SharedObjectTest {
	public static void main(String... args) throws InterruptedException {
		SharedObject s = new SharedObject();
		s.performTryLock();
		s.perform();
		s.performTryLock();
	}
}

class SharedObject {
	Map<String, Integer> m = new HashMap<>();
	ReentrantLock lock = new ReentrantLock();
	int counter = 0;

	public void perform() {
		lock.lock();
		try {
			// Critical section here
			m.put("USA", 65);
			counter++;
			m.put("EURO", 79);
			counter++;
			m.put("IND", 2);
			counter++;
			m.put("UK", 85);
			counter++;
			System.out.println(Thread.currentThread().getName() + " Added items and count is " + counter);
		} finally {
			lock.unlock();
		}
	}

	public void performTryLock() throws InterruptedException {

		boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);

		if (isLockAcquired) {
			try {
				System.out.println(Thread.currentThread().getName() + " Count is " + counter + " and map is " + m);
				m.clear();
				counter = 0;
			} finally {
				lock.unlock();
			}
		}

	}
}