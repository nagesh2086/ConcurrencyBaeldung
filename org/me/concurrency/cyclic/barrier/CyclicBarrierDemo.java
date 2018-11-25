package org.me.concurrency.cyclic.barrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Number crunching by many thread using CyclicBarrier.
 * 
 * There’s an operation that a fixed number of threads perform and store the
 * corresponding results in a list. When all threads finish performing their
 * action, one of them (typically the last one that trips the barrier) starts
 * processing the data that was fetched by each of these.
 * 
 * @author nagesh2086
 *
 */
public class CyclicBarrierDemo {
	private CyclicBarrier cyclicBarrier;
	private List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());
	private Random random = new Random();
	private int NUM_PARTIAL_RESULTS;
	private int NUM_WORKERS;

	public static void main(String[] args) {
		CyclicBarrierDemo demo = new CyclicBarrierDemo();
		demo.runSimulation(5, 3);
	}

	public void runSimulation(int numWorkers, int numberOfPartialResults) {
		NUM_PARTIAL_RESULTS = numberOfPartialResults;
		NUM_WORKERS = numWorkers;

		cyclicBarrier = new CyclicBarrier(NUM_WORKERS, new AggregatorThread());

		System.out.println("Spawning " + NUM_WORKERS + " worker threads to compute " + NUM_PARTIAL_RESULTS
				+ " partial results each");

		for (int i = 0; i < NUM_WORKERS; i++) {
			Thread worker = new Thread(new NumberCruncherThread());
			worker.setName("Thread " + i);
			worker.start();
		}
	}

	class AggregatorThread implements Runnable {

		@Override
		public void run() {

			String thisThreadName = Thread.currentThread().getName();

			System.out.println(thisThreadName + ": Computing sum of " + NUM_WORKERS + " workers, having "
					+ NUM_PARTIAL_RESULTS + " results each.");
			int sum = 0;

			for (List<Integer> threadResult : partialResults) {
				System.out.print("Adding ");
				for (Integer partialResult : threadResult) {
					System.out.print(partialResult + " ");
					sum += partialResult;
				}
				System.out.println();
			}
			System.out.println(thisThreadName + ": Final result = " + sum);
		}
	}

	class NumberCruncherThread implements Runnable {

		@Override
		public void run() {
			String thisThreadName = Thread.currentThread().getName();
			List<Integer> partialResult = new ArrayList<>();

			// Crunch some numbers and store the partial result
			for (int i = 0; i < NUM_PARTIAL_RESULTS; i++) {
				Integer num = random.nextInt(10);
				System.out.println(thisThreadName + ": Crunching some numbers! Final result - " + num);
				partialResult.add(num);
			}

			partialResults.add(partialResult);
			try {
				System.out.println(thisThreadName + " waiting for others to reach barrier.");
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				// ...
			} catch (BrokenBarrierException e) {
				// ...
			}
		}
	}
}