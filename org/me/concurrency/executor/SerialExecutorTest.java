package org.me.concurrency.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Description: Many {@code Executor} implementations impose some sort of
 * limitation on how and when tasks are scheduled. The executor below serializes
 * the submission of tasks to a second executor, illustrating a composite
 * executor.
 * 
 * @author kekannag
 *
 */
public class SerialExecutorTest {

	public static void main(String[] args) {
		//thread execution flow is not stopping so try simple executor
		//SerialExecutor se = new SerialExecutor(Executors.newSingleThreadExecutor());
		//SerialExecutor se = new SerialExecutor(new DirectExecutor());
		SerialExecutor se = new SerialExecutor(new ThreadPerTaskExecutor());
		
		se.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " Runnable command 1");
			}
		});
		se.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " Runnable command 2");
			}
		});
	}

}

class SerialExecutor implements Executor {
	private final Queue<Runnable> tasks = new ArrayDeque<>();
	private Runnable active;
	private final Executor executor;

	public SerialExecutor(Executor executor) {
		this.executor = executor;
	}

	@Override
	public synchronized void execute(Runnable command) {
		//1.
		tasks.offer(new Runnable() {

			@Override
			public void run() {
				try {
					command.run();
				} finally {
					scheduleNext();
				}
			}
		});
		
		//2.
		//tasks.offer(command);
		
		//3.
		//command.run();

		if (active == null) {
			Runnable next = scheduleNext();
			executor.execute(next);
		}
	}

	private synchronized Runnable scheduleNext() {
	//private Runnable scheduleNext() {
		/*if ((active = tasks.poll()) != null) {
			executor.execute(active);
		}*/
		return tasks.poll();
	}

}
