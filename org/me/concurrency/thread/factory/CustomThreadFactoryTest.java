package org.me.concurrency.thread.factory;

public class CustomThreadFactoryTest {

	public CustomThreadFactoryTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		CustomThreadFactory customThreadFactory = new CustomThreadFactory("1", "CustomThreadFactory");
		Thread newThread = customThreadFactory.newThread(new Task());
		newThread.start();
	}

}
