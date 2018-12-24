package org.me.concurrency.sender.receiver;

public class Data {
	private String packet;
	private boolean canTransferNow;

	public Data() {
		this.canTransferNow = true;
	}

	public void send(String packet) throws InterruptedException {
		while (!canTransferNow) {
			System.out.println(Thread.currentThread().getName() + " waiting for RECEIVER");
			wait();
		}
		this.packet = packet;
		canTransferNow = false;
		notifyAll();
		System.out.println(Thread.currentThread().getName() + " packet - \"" + packet + "\" has been sent. ");
	}

	public String receive() throws InterruptedException {
		while (canTransferNow) {
			System.out.println(Thread.currentThread().getName() + " waiting for SENDER");
			wait();
		}
		canTransferNow = true;
		notifyAll();
		System.out.println(
				Thread.currentThread().getName() + " packet - \"" + packet + "\" has been received and processed. ");
		return packet;
	}
}
