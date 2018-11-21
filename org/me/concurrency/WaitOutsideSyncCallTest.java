package org.me.concurrency;

/**
 * We want to test that what will happen if we use Object.wait outside
 * synchronized method\block. Output is runtime exception i.e.
 * IllegalMonitorStateException
 * 
 * @author kekannag
 *
 */
public class WaitOutsideSyncCallTest {

	private Object lock = new Object();

	public static void main(String[] args) throws InterruptedException {
		WaitOutsideSyncCallTest wet = new WaitOutsideSyncCallTest();
		synchronized (wet.lock) {
			wet.lock.wait(2000);
		}
		wet.waitCallTest();
	}

	/**
	 * It can only be called from a synchronized block. There will be no compilation
	 * problem. There will be runtime exception -
	 * java.lang.IllegalMonitorStateException
	 * 
	 * @throws InterruptedException
	 */
	private void waitCallTest() throws InterruptedException {
		lock.wait();
	}

}
