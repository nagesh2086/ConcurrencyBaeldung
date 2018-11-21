package org.me.concurrency;

public class DataApplication {

	public static void main(String[] args) throws InterruptedException {
		Data data = new Data();
		Thread sender = new Thread(new Sender(data));
		Thread receiver = new Thread(new Receiver(data));

		receiver.start();
		Thread.sleep(2000);
		sender.start();
	}

}
