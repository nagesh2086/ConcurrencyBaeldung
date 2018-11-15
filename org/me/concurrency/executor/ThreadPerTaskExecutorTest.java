package org.me.concurrency.executor;

public class ThreadPerTaskExecutorTest {

	public static void main(String[] args) {
		// We want to execute the task in new thread
		ThreadPerTaskExecutor executor = new ThreadPerTaskExecutor();

		Runnable _command1 = new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " executing Runnable task 1");
			}
		};

		Runnable _command2 = new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " executing Runnable task 2");
			}
		};

		executor.execute(_command1);
		executor.execute(_command2);

		System.out.println("ENDED MAIN Daemon Thread-" + Thread.currentThread().getName());
	}

}

/*
 * class ThreadPerTaskExecutor implements Executor {
 * 
 * @Override public void execute(Runnable command) { new Thread(command,
 * "TaskExecutor-" + Math.round(Math.random()*10)).start(); }
 * 
 * }
 */