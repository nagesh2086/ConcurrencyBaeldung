package org.me.concurrency.race.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RaceConditionTest {

	public static void main(String[] args) throws InterruptedException {
		RaceCondition rc = new RaceCondition();

		// TEST 1: Used 10 threads and tried to access shared data i.e. sum at the same
		// time.
		final ExecutorService manyThService = Executors.newFixedThreadPool(10);

		IntStream.range(0, 1000).boxed().forEach(count -> manyThService.submit(rc::calculate));

		Thread.sleep(1000);
		manyThService.shutdownNow();
		manyThService.awaitTermination(1500, TimeUnit.MILLISECONDS);

		System.out.println("TEST 1 (many threads)-> " + rc.getSum());// expected 1000

		// TEST 2: Used single thread and tried to access shared data i.e. sum at the
		// same time.
		final ExecutorService singleThService = Executors.newFixedThreadPool(1);

		IntStream.range(0, 1000).boxed().forEach(count -> singleThService.submit(rc::calculate));

		Thread.sleep(1000);
		singleThService.shutdownNow();
		singleThService.awaitTermination(1500, TimeUnit.MILLISECONDS);

		System.out.println("TEST 1 (Single thread) -> " + rc.getSum());// expected 1000
	}

}

class RaceCondition {
	private int sum;

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public void calculate() {
		setSum(getSum() + 1);
	}
}
