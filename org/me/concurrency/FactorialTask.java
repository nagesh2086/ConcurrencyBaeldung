package org.me.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FactorialTask implements Callable<Integer> {
	int number;

	public FactorialTask() {
	}

	public FactorialTask(int number) {
		this.number = number;
	}

	public Integer call() throws InvalidParamaterException {
		int fact = 1;

		for (int count = number; count > 1; count--) {
			fact = fact * count;
		}

		return fact;
	}

	public void whenTaskSubmitted_ThenFutureResultObtained(FactorialTask task) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<Integer> future = es.submit(task);
		System.out.println(future.get().intValue());
	}
	
	public static void main(String... args) throws InterruptedException, ExecutionException {
		FactorialTask task = new FactorialTask(5);
		task.whenTaskSubmitted_ThenFutureResultObtained(task);
	}
}