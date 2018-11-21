package org.me.concurrency;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Data {

	private String packet;
	Logger Log = Logger.getLogger("Data");

	// True if receiver should wait
	// False if sender should wait
	private boolean transfer = true;

	public synchronized void send(String packet) {
		// False if sender should wait
		while (!transfer) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				Log.log(Level.SEVERE, "Thread interrupted", e);
			}
		}
		transfer = false;

		this.packet = packet;
		notifyAll();
	}

	public synchronized String receive() {
		// True if receiver should wait
		while (transfer) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				Log.log(Level.SEVERE, "Thread interrupted", e);
			}
		}
		transfer = true;
		notifyAll();
		return this.packet;
	}

}