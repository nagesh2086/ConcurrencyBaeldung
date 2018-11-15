package org.me.concurrency.executor;

public class DirectExecutorTest {

	public static void main(String... args) {
		Runnable command = new Runnable() {
			public void run() {
				System.out.println("Runnable DirectExecutorTest task 1");
			}
		};
		
		DirectExecutor de = new DirectExecutor();
		de.execute(command);
	}
}

/*class DirectExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		// Run task in the invoker\ caller's thread instead of running in the new thread
		command.run();
	}

}*/
