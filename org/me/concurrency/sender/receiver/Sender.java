package org.me.concurrency.sender.receiver;

public class Sender implements Runnable {

	private final Data data;

	public Sender(Data data) {
		this.data = data;
	}

	@Override
	public void run() {
		String[] packets = { "1st packet", "2nd packet", "3rd packet", "4th packet", "5th packet", "6th packet",
				"7th packet", "8th packet", "9th packet", "END" };
		synchronized (data) {
			for (String packet : packets) {
				try {
					data.send(packet);
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
