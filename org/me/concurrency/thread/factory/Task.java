package org.me.concurrency.thread.factory;

public class Task implements Runnable {

	public Task() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		System.out.println(Task.class.getSimpleName() + " executed by " + Thread.currentThread().getName());
	}

}
