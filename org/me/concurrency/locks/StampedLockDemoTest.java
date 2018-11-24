package org.me.concurrency.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/* StampedLock */
class StampedLockDemoTest {
	public static void main(String... args) throws InterruptedException {
		StampedLockDemo s = new StampedLockDemo();
		s.put("Pune", "70LPA");
		s.put("Mumbai", "100LPA");
		s.put("Nashik", "30LPA");
		s.put("Solapur", "30LPA");
		s.put("Karmala", "7LPA");

		System.out.println(s.readWithOptimisticLock("Karmala"));
		System.out.println(s.readWithOptimisticLock("Pune"));
		System.out.println(s.get("Nashik"));
		System.out.println(s.get("Mumbai"));
	}
}

class StampedLockDemo {
	Map<String, String> map = new HashMap<>();
	private StampedLock lock = new StampedLock();

	public void put(String key, String value) {
		long stamp = lock.writeLock();
		try {
			System.out.println("write lock added element...");
			map.put(key, value);
		} finally {
			lock.unlockWrite(stamp);
		}
	}

	public String get(String key) throws InterruptedException {
		long stamp = lock.readLock();
		try {
			System.out.print("readLock -> ");
			return map.get(key);
		} finally {
			lock.unlockRead(stamp);
		}
	}

	public String readWithOptimisticLock(String key) {
		long stamp = lock.tryOptimisticRead();
		String value = map.get(key);

		boolean validate = lock.validate(stamp);

		if (validate) {
			System.out.print("readWithOptimisticLock -> ");
		}

		if (!validate) {
			stamp = lock.readLock();
			try {
				System.out.print("readLock if optimistic fails -> ");
				return map.get(key);
			} finally {
				lock.unlockRead(stamp);
			}
		}
		return value;
	}
}