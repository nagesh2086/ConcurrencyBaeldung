package org.me.concurrency.rev.fork.join.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class CustomRecursiveActionTest {
	public static void main(String[] args) {
		final String workload = "Chandan Nagar, Kharadi, Pune, Maharashtra, India. 411014.";
		final CustomRecursiveAction cra = new CustomRecursiveAction(workload);
		cra.compute();
	}
}

class CustomRecursiveAction extends RecursiveAction {

	private static final long serialVersionUID = 5895774532414142310L;
	private String workload = "";
	private static final int THRESHOLD = 4;

	private static Logger logger = Logger.getAnonymousLogger();

	public CustomRecursiveAction(String workload) {
		this.workload = workload;
	}

	@Override
	protected void compute() {
		if (workload.length() > THRESHOLD) {
			ForkJoinTask.invokeAll(createSubtasks());
		} else {
			processing(workload);
		}
	}

	private List<CustomRecursiveAction> createSubtasks() {
		List<CustomRecursiveAction> subtasks = new ArrayList<>();

		String partOne = workload.substring(0, workload.length() / 2);
		String partTwo = workload.substring(workload.length() / 2, workload.length());

		subtasks.add(new CustomRecursiveAction(partOne));
		subtasks.add(new CustomRecursiveAction(partTwo));

		return subtasks;
	}

	private void processing(String work) {
		String result = work.toUpperCase();
		// logger.info("This result - (" + result + ") - was processed by " +
		// Thread.currentThread().getName());
		System.out.println("This result - (" + result + ") - was processed by " + Thread.currentThread().getName());
	}
}