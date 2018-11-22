package org.me.concurrency.forkjoin.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveActionTest {

	public static void main(String[] args) {
		String input = "tietoindiaprivatelimitedkharadipune14";
		ForkJoinPool pool = ForkJoinPool.commonPool();
		// pool.execute(action);
		pool.invoke(new CustomRecursiveAction(input));
	}

}

class CustomRecursiveAction extends RecursiveAction {

	private static final long serialVersionUID = 280919159565800354L;
	private String workload = "";
	private final static int THRESHOLD = 4;

	public CustomRecursiveAction(String workload) {
		this.workload = workload;
	}

	@Override
	protected void compute() {
		if (workload.length() > THRESHOLD) {
			ForkJoinTask.invokeAll(createTasks());
		} else {
			processingWorkload();
		}
	}

	private List<CustomRecursiveAction> createTasks() {
		String part1 = workload.substring(0, workload.length() / 2);
		String part2 = workload.substring(workload.length() / 2, workload.length());

		ArrayList<CustomRecursiveAction> tasks = new ArrayList<>();
		CustomRecursiveAction action1 = new CustomRecursiveAction(part1);
		CustomRecursiveAction action2 = new CustomRecursiveAction(part2);
		tasks.add(action1);
		tasks.add(action2);

		return tasks;
	}

	private void processingWorkload() {
		String upperCase = workload.toUpperCase();
		System.out.println(upperCase + " - work was processed by " + Thread.currentThread().getName());
	}

}
