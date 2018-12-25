package org.me.concurrency.rev.custom.thread.pool;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CustomThreadPoolTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CustomThreadPoolTest test = new CustomThreadPoolTest();
		test.giveRangeOfLongs_whenSummedInParallel_shouldBeEqualToExpectedTotal();
	}

	public void giveRangeOfLongs_whenSummedInParallel_shouldBeEqualToExpectedTotal()
			throws InterruptedException, ExecutionException {

		long firstNum = 1;
		long lastNum = 1_000_000;

		List<Long> aList = LongStream.rangeClosed(firstNum, lastNum).boxed().collect(Collectors.toList());

		ForkJoinPool customThreadPool = new ForkJoinPool(4);
		long actualTotal = customThreadPool.submit(() -> aList.parallelStream().reduce(0L, Long::sum)).get();

		long expectedTotal = (lastNum + firstNum) * lastNum / 2;
		System.out.println("expectedTotal -> " + expectedTotal);
		System.out.println("actualTotal -> " + actualTotal);
	}
}