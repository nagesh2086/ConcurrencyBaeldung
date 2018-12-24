package org.me.concurrency.sender.receiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SenderReceiverDataApp {

	public static void main(String[] args) {
		Data data = new Data();
		// legacy way
		/*
		 * Sender sender = new Sender(data); Receiver receiver = new Receiver(data);
		 * 
		 * Thread reTh = new Thread(receiver, "Receiver_TH"); Thread seTh = new
		 * Thread(sender, "Sender_TH");
		 * 
		 * reTh.start(); seTh.start();
		 */

		// Using executor service framework

		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(new Receiver(data));
		service.submit(new Sender(data));

		try {
			// Thread.sleep(6000);
			Thread.sleep(2000);
			service.shutdown();
			while (!service.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
				// service.shutdownNow();
				System.out.println(Thread.currentThread().getName() + " - waiting for completion of submitted work");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

}
