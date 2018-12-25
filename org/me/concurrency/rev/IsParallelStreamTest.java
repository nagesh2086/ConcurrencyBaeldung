package org.me.concurrency.rev;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class IsParallelStreamTest {
	public static void main(String[] args) {
		List<? extends Number> list = Arrays.asList(0l, 1l, 2l, 3l, 10l, 11l, 12l, 13l, 14l, 15l, 16l, 17l, 18l, 19l,
				20l, 11l, 12l, 13l, 14l, 15l, 16l, 17l, 18l, 19l, 20l, 21l, 22l, 22l, 22l, 23l, 23l, 23l, 24l, 25l, 25l,
				2l, 1l, 4l, 6l, 28l, 29l, 26l, 26l, 25l, 24l, 27l, 30l, 100l, 100l, 1000l, 12l, 014l, 015l, 016l, 17l,
				15l, 12l, 14l, 11l, 0l, 2l, -1l, -2l, -36l);
		Stream<? extends Number> stream = list.parallelStream();
		System.out.println(stream.isParallel());
	}
}