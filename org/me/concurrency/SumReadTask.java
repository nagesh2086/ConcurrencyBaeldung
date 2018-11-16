package org.me.concurrency;

public class SumReadTask implements Runnable {

	private final SumCalculateTask st;

	public SumReadTask(SumCalculateTask st) {
		this.st = st;
	}

	@Override
	public void run() {
		synchronized (st) {
			while (st.getSum() == 0) {
				System.out.println(this + " --> " + Thread.currentThread().getName() + " is waiting on " + st + " to complete sum");
				try {
					st.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(this + " --> " + Thread.currentThread().getName() + " back from wait and sum is " + st.getSum());
		}
	}

}
