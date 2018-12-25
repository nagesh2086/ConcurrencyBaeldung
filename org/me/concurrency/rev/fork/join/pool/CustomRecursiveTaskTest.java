package org.me.concurrency.rev.fork.join.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTaskTest {
	public static void main(String[] args) {
		final int[] arr = new int[] { 0, 1, 2, 3, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 11, 12, 13, 14, 15, 16,
				17, 18, 19, 20, 21, 22, 22, 22, 23, 23, 23, 24, 25, 25, 2, 1, 4, 6, 28, 29, 26, 26, 25, 24, 27, 30, 100,
				100, 1000, 12, 014, 015, 016, 17, 15, 12, 14, 1, 0, 2, -1, -2, -36 };
		final CustomRecursiveTask crt = new CustomRecursiveTask(arr);
		Integer result = crt.compute();
		System.out.println("CustomRecursiveTask - " + Arrays.toString(arr) + " - \n" + result);
	}
}

class CustomRecursiveTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 3924684976446707601L;

	private int[] arr;

	private static final int THRESHOLD = 20;

	public CustomRecursiveTask(int[] arr) {
		this.arr = arr;
	}

	@Override
	protected Integer compute() {
		int result = 0;
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

		if (arr.length > THRESHOLD) {

			Collection<CustomRecursiveTask> subtasks = createSubtasks();

			// forkJoinPool.execute()\submit() and task.join() example
			/*
			 * for (CustomRecursiveTask customRecursiveTask : subtasks) {
			 * forkJoinPool.execute(customRecursiveTask); result +=
			 * customRecursiveTask.join(); }
			 */

			// forkJoinPool.invoke() example
			/*
			 * for (CustomRecursiveTask customRecursiveTask : subtasks) { result
			 * += forkJoinPool.invoke(customRecursiveTask); }
			 */

			// Recursive Task fork()\join() method example
			for (CustomRecursiveTask customRecursiveTask : subtasks) {
				customRecursiveTask.fork();
				result += customRecursiveTask.join();
			}
			// ForkJoinTask.invokeAll() example
			// return
			// ForkJoinTask.invokeAll(createSubtasks()).stream().mapToInt(ForkJoinTask::join).sum();
		} else {
			return processing(arr);
		}
		return result;
	}

	private Collection<CustomRecursiveTask> createSubtasks() {
		List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
		dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2)));
		dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
		return dividedTasks;
	}

	private Integer processing(int[] arr) {
		System.out.println(
				"This result - (" + Arrays.toString(arr) + ") - was processed by " + Thread.currentThread().getName());
		return Arrays.stream(arr).filter(a -> a > 10 && a < 27).map(a -> a * 10).sum();
	}
}