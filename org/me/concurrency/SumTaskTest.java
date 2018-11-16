package org.me.concurrency;

public class SumTaskTest {

	public static void main(String[] args) throws InterruptedException {
		SumCalculateTask sc_t = new SumCalculateTask();
		// new Thread(sc_t, "th_SUM_TASK").start();

		/*
		 * synchronized (st) { while (st.getSum() == 0) {
		 * System.out.println(Thread.currentThread().getName() + " is waiting on " + st
		 * + " to complete sum"); st.wait(); }
		 * System.out.println(Thread.currentThread().getName() +
		 * " is completed sum calc and sum is " + st.getSum()); }
		 */

		SumReadTask sr_t = new SumReadTask(sc_t);

		new Thread(sr_t, "sum_read_thread").start();
		new Thread(sc_t, "sum_calculate_thread").start();
	}

}
