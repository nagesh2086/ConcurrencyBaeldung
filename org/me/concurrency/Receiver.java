package org.me.concurrency;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receiver implements Runnable {
	Logger Log = Logger.getLogger("Data");
	private Data load;

	// standard constructors
	public Receiver(Data load) {
		this.load = load;
	}

	public void run() {
		for (String receivedMessage = load.receive(); !"End".equals(receivedMessage); receivedMessage = load
				.receive()) {

			System.out.println(receivedMessage);

			// ...
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				Log.log(Level.SEVERE, "Thread interrupted", e);
			}
		}
	}
}