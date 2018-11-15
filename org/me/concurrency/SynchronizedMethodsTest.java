package org.me.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Study synchronized keyword\ block
 * 
 * @author kekannag
 *
 */
public class SynchronizedMethodsTest {

	public SynchronizedMethodsTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(5);

		SynchronizedMethods sm = new SynchronizedMethods();

		IntStream.range(0, 1000).forEach(count -> service.submit(sm::calculate));

		service.awaitTermination(1000, TimeUnit.MILLISECONDS);

		System.out.println(sm.getSum());

		service.shutdownNow();
	}

}

class SynchronizedMethods {
	private int sum;

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public void calculate() {
		synchronized (this) {
			setSum(getSum() + 1);
		}
	}
}
