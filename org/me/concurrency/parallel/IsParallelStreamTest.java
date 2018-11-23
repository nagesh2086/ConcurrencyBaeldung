package org.me.concurrency.parallel;

import java.util.ArrayList;
import java.util.stream.Stream;

public class IsParallelStreamTest {

	public static void main(String[] args) {
		ArrayList<Long> arrayList = new ArrayList<>();
		Stream<Long> parallelStream = arrayList.parallelStream();
		System.out.println(parallelStream.isParallel());
	}

}
