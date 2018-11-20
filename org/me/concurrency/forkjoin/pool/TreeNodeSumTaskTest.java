package org.me.concurrency.forkjoin.pool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * We want to sum all values in a tree in parallel.
 * 
 * @author kekannag
 *
 */
public class TreeNodeSumTaskTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		TreeNode treeNode = new TreeNode(5, new TreeNode(3), new TreeNode(2, new TreeNode(2), new TreeNode(8)));

		TreeNode tree = new TreeNode(5, new TreeNode(3), new TreeNode(2, new TreeNode(2), new TreeNode(8)));

		ForkJoinPool pool = ForkJoinPool.commonPool();
		// ForkJoinTask<Integer> task = pool.submit(new CountingTask(treeNode));
		// ForkJoinTask<Integer> task = pool.submit(new CountingTask(tree));
		// task.get();

		// System.out.println("result --> " + task.get());

		int sum = pool.invoke(new CountingTask(tree));
		System.out.println("result --> " + sum);
	}

}

class TreeNode {
	int value;

	Set<TreeNode> children = new HashSet<>();

	public TreeNode(int value, TreeNode... children) {
		this.value = value;
		for (int i = 0; i < children.length; i++) {
			this.children.add(children[i]);
		}
	}
}

class CountingTask extends RecursiveTask<Integer> {

	private TreeNode treeNode;

	public CountingTask(TreeNode treeNode) {
		this.treeNode = treeNode;
	}

	@Override
	protected Integer compute() {
		Integer collect = treeNode.value + treeNode.children.stream().map(childNode -> new CountingTask(childNode).fork())
				.collect(Collectors.summingInt(ForkJoinTask::join));

		return collect;
	}

	/*private final TreeNode node;
	 
    public CountingTask(TreeNode node) {
        this.node = node;
    }
 
    @Override
    protected Integer compute() {
        return node.value + node.children.stream()
          .map(childNode -> new CountingTask(childNode).fork())
          .collect(Collectors.summingInt(ForkJoinTask::join));
    }*/
}
