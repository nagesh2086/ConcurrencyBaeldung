package org.me.concurrency.executor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * example of ExecutorCompletionService
 * 
 * @author nagesh2086
 *
 */
public class ExecutorCompletionServiceTest {

	public static void main(String[] args) {
		ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

		ExecutorCompletionService<String> service = new ExecutorCompletionService<>(WORKER_THREAD_POOL);
		List<DelayedCallable> tasks = Arrays.asList(new DelayedCallable("slow thread", 3000),
				new DelayedCallable("fast thread", 100));

		for (int i = 0; i < 2; i++) {
			service.submit(tasks.get(i));
		}

		Future<String> future;
		try {
			future = service.take();
			System.out.println(future.get());

			future = service.take();
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			WORKER_THREAD_POOL.shutdownNow();
			Thread.currentThread().interrupt();
		}

		awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
	}

	private static void awaitTerminationAfterShutdown(ExecutorService WORKER_THREAD_POOL) {
		WORKER_THREAD_POOL.shutdown();
		try {
			if (!WORKER_THREAD_POOL.awaitTermination(60, TimeUnit.SECONDS)) {
				WORKER_THREAD_POOL.shutdownNow();
			}
		} catch (InterruptedException e) {
			WORKER_THREAD_POOL.shutdownNow();
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}
