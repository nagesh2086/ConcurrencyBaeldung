package org.me.concurrency.forkjoin.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTaskTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
				28, 29, 30, 31 };

		// Invoke submit tasks and joins result too
		/*
		 * Integer task = ForkJoinPool.commonPool().invoke(new
		 * CustomRecursiveTask(arr)); System.out.println(task);
		 */

		// Submit - submit task and need to use join method to trigger task execution
		/*
		 * ForkJoinTask<Integer> forkJoinTask = ForkJoinPool.commonPool().submit(new
		 * CustomRecursiveTask(arr)); System.out.println(forkJoinTask.join());
		 */

		// execute - submit task and need to use join method to trigger task execution
		CustomRecursiveTask recursiveTask = new CustomRecursiveTask(arr);
		ForkJoinPool.commonPool().execute(recursiveTask);
		System.out.println(recursiveTask.join());
	}

}

class CustomRecursiveTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1596257716995075078L;

	private static final int THREASHOLD = 20;
	private int[] arr;

	public CustomRecursiveTask(int[] arr) {
		this.arr = arr;
	}

	@Override
	protected Integer compute() {
		if (arr.length > THREASHOLD) {
			// not working - use of fork() and join() method
			/*
			 * CustomRecursiveTask firstTask = new
			 * CustomRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2));
			 * CustomRecursiveTask secondTask = new CustomRecursiveTask(
			 * Arrays.copyOfRange(arr, arr.length / 2, arr.length)); firstTask.fork();
			 * return secondTask.join();
			 */
			return ForkJoinTask.invokeAll(forkTasks()).stream().mapToInt(ForkJoinTask::join).sum();
		} else {
			return processing();
		}
	}

	private List<CustomRecursiveTask> forkTasks() {
		ArrayList<CustomRecursiveTask> arrayList = new ArrayList<>();
		arrayList.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2)));
		arrayList.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
		return arrayList;
	}

	private Integer processing() {
		return Arrays.stream(arr).filter(a -> a > 10 && a < 27).map(a -> a * 10).sum();
	}

}
