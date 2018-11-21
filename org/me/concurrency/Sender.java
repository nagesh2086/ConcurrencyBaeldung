package org.me.concurrency;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender implements Runnable {
	Logger Log = Logger.getLogger("Data");
	private Data data;

	// standard constructors
	public Sender(Data data) {
		this.data = data;
	}

	public void run() {
		String packets[] = { "First packet", "Second packet", "Third packet", "Fourth packet", "End" };

		for (String packet : packets) {
			data.send(packet);// sender will wait if receiver is still processing data

			// Thread.sleep() to mimic heavy server-side processing
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				Log.log(Level.SEVERE, "Thread interrupted", e);
			}
		}
	}
}