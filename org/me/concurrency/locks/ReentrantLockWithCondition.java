package org.me.concurrency.locks;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* Condition testing with ReentrantLock */
class RLConditionOnStackTest {
	public static void main(String... args) throws InterruptedException {
		System.out.println("Last-In-First-Out with condition and Reentrantlock...");
		ReentrantLockWithCondition s = new ReentrantLockWithCondition();
		s.pushToStack("Pune");// 1
		s.pushToStack("Mumbai");// 2
		s.pushToStack("Nashik");// 3
		s.pushToStack("Solapur");// 4
		s.pushToStack("Karmala");// 5

		System.out.println(s.popFromStack());
		System.out.println(s.popFromStack());
		System.out.println(s.popFromStack());
		System.out.println(s.popFromStack());
		System.out.println(s.popFromStack());
	}
}

public class ReentrantLockWithCondition {

	Stack<String> stack = new Stack<>();
	int CAPACITY = 5;

	ReentrantLock lock = new ReentrantLock();
	Condition stackEmptyCondition = lock.newCondition();
	Condition stackFullCondition = lock.newCondition();

	public void pushToStack(String item) throws InterruptedException {
		try {
			lock.lock();
			while (stack.size() == CAPACITY) {
				stackFullCondition.await();
			}
			stack.push(item);
			System.out.println("Pushed " + item);
			stackEmptyCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public String popFromStack() throws InterruptedException {
		try {
			lock.lock();
			while (stack.size() == 0) {
				stackEmptyCondition.await();
			}
			return stack.pop();
		} finally {
			System.out.println("poped... ");
			stackFullCondition.signalAll();
			lock.unlock();
		}
	}
}