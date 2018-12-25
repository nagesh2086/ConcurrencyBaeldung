package org.me.concurrency;

/**
 * Why AnyObject.wait can be called only from the synchronized block? -
 * java.lang.IllegalMonitorStateException
 * 
 * Yes, wait cannot be called without sync. block otherwise we can see
 * java.lang.IllegalMonitorStateException.
 * 
 * @author kekannag
 *
 */
public class WaitTest {

	public static void main(String[] args) throws InterruptedException {
		WaitTest.class.wait();
	}

}
