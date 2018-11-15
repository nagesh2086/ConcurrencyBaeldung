package org.me.concurrency.executor;

import java.util.concurrent.Executor;

class ThreadPerTaskExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		new Thread(command, "TaskExecutor-" + Math.round(Math.random() * 10)).start();
	}

}