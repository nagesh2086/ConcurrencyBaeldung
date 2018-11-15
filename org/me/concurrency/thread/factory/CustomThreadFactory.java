package org.me.concurrency.thread.factory;

import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

public class CustomThreadFactory implements ThreadFactory {

	private String threadId;
	private String threadName;

	private final Logger LOGG = Logger.getLogger("__CustomThreadFactory");

	public CustomThreadFactory(String threadId, String threadName) {
		this.threadId = threadId;
		this.threadName = threadName;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, threadName + "-" + threadId);
		LOGG.info("thread has been created " + t.getName());
		return t;
	}

}
