package org.me.concurrency.executor;

import java.util.concurrent.Executor;

class DirectExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		// Run task in the invoker\ caller's thread instead of running in the new thread
		command.run();
	}

}