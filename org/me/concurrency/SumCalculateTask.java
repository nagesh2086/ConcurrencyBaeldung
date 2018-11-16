package org.me.concurrency;

public class SumCalculateTask implements Runnable {
	private int sum;
	
	public int getSum() {
		return sum;
	}

	@Override
	public void run() {
		synchronized (this) {
			int i = 0;
			while (i < 100000) {
				if (i%10 == 0) {
					goToSleep(1);
				}
				sum += i;
				i++;
			}
			System.out.println(this + " --> " + Thread.currentThread().getName() + " is completed and notifying now");
			notify();
		}
	}

	private void goToSleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String... args) {
		SumCalculateTask st = new SumCalculateTask();
		new Thread(st, "th_SUM_TASK").start();
	}*/
}
