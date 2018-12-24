package org.me.concurrency.sender.receiver;

public class Receiver implements Runnable {

	private final Data data;

	public Receiver(Data data) {
		this.data = data;
	}

	@Override
	public void run() {
		synchronized (data) {
			try {
				for (String packet = data.receive(); !"END".equals(packet); packet = data.receive()) {
					System.out.println(packet + "\n");
					Thread.sleep(400);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}
