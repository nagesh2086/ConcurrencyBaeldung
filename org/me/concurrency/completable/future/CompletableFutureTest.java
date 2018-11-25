package org.me.concurrency.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* example on completable future class
- supplyAsync
- thenApply - accept argument, process it and return the result
- thenAccept - accept argument and process it right there, no return result
- thenRun - no argumentn and return value
- completableFuture.cancel() - CancellationException runtime exception */
public class CompletableFutureTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFutureTest test = new CompletableFutureTest();
		System.out.println(test.calculateAsSync().get());

		// encapsulated computation logic
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
		System.out.println("supplyAsync() -> " + future1.get());

		// thenApply - processing results of asynchronous computation
		CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future2 = completableFuture1.thenApply(s -> s + " World");
		System.out.println("thenApply() - > " + future2.get());

		// thenAccept - processing results of asynchronous computation
		CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<Void> future3 = completableFuture2
				.thenAccept(s -> System.out.println("thenAccept() -> Computation returned: " + s));

		// thenRun - processing results of asynchronous computation
		CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<Void> future4 = completableFuture3
				.thenRun(() -> System.out.println("thenRun() -> Computation finished."));

		System.out.println(test.calculateAsyncWithCancellation().get());
	}

	private CompletableFuture<String> calculateAsSync() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = new CompletableFuture<>();

		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(() -> {
			Thread.sleep(5000);
			future.complete("executed!!");
			return null;
		});

		// awaitTerminationAfterShutdown(service);
		return future;
	}

	public Future<String> calculateAsyncWithCancellation() throws InterruptedException {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();

		Executors.newCachedThreadPool().submit(() -> {
			Thread.sleep(500);
			completableFuture.cancel(false);
			return null;
		});

		return completableFuture;
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
