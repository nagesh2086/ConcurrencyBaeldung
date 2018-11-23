package org.me.concurrency.parallel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * The following example lets have a parallel Stream use a custom Thread Pool to
 * calculate the sum of long values from 1 to 1,000,000, inclusive.
 * 
 * @author kekannag
 *
 */
public class SumLongsInParallel {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// arraylist 1 to 1_000_000

		long startInclusive = 0;
		long endInclusive = 1_000_000;

		LongStream longStream = LongStream.rangeClosed(startInclusive, endInclusive);
		Stream<Long> boxed = longStream.boxed();
		List<Long> list = boxed.collect(Collectors.toList());

		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

		ForkJoinTask<Long> task = pool.submit(() -> list.parallelStream().reduce(0L, Long::sum));

		System.out.println(task.get());

		System.out.println(((startInclusive + endInclusive) * endInclusive) / 2);// (lastNum + firstNum) * lastNum / 2

	}

}
