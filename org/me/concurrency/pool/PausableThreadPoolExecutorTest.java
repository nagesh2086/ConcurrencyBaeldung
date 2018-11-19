package org.me.concurrency.thread.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausableThreadPoolExecutorTest {

	public static void main(String[] args) {
		Executors.newCachedThreadPool();
		final PausableThreadPoolExecutor threadPoolExecutor = new PausableThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		final Runnable runnable_1 = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + " executed");
			}
		};

		threadPoolExecutor.submit(runnable_1);
	}

}

class PausableThreadPoolExecutor extends ThreadPoolExecutor {
	private boolean isPaused;
	private ReentrantLock pauseLock = new ReentrantLock();
	private Condition unpaused = pauseLock.newCondition();

	public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		System.out.println(Thread.currentThread().getName() + " going to pause");
		pauseLock.lock();
		try {
			while (isPaused) {
				System.out.println(Thread.currentThread().getName() + " paused");
				unpaused.await();
			}
		} catch (InterruptedException ie) {
			t.interrupt();
		} finally {
			pauseLock.unlock();
			System.out.println(Thread.currentThread().getName() + " resumed");
		}
	}

	public void pause() {
		System.out.println(Thread.currentThread().getName() + " pause command to be executed");
		pauseLock.lock();
		try {
			isPaused = true;
		} finally {
			pauseLock.unlock();
		}
		System.out.println(Thread.currentThread().getName() + " pause command executed");
	}

	public void resume() {
		System.out.println(Thread.currentThread().getName() + " resume command to be executed");
		pauseLock.lock();
		try {
			unpaused.signalAll();
			isPaused = false;
		} finally {
			pauseLock.unlock();
		}
		System.out.println(Thread.currentThread().getName() + " resume command executed");
	}
}