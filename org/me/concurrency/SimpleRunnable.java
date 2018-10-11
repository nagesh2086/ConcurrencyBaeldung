package org.me.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SimpleRunnable implements Runnable {
     
    private String message;
	
	SimpleRunnable (String message){
		this.message = message;
	}
     
    // standard logger, constructor
     
    @Override
    public void run() {
        System.out.println(message);
    }
	
	public static void givenRunnable_whenRunIt_thenResult()
 throws Exception {
		Thread thread = new Thread(new SimpleRunnable(
		  "SimpleRunnable executed using Thread"));
		thread.start();
		thread.join();
	}
	
	public static void givenARunnable_whenSubmitToES_thenResult()
 throws Exception {
		 
		getSingleThreadExecutor().submit(new SimpleRunnable(
		  "SimpleRunnable executed using ExecutorService")).get();
	}

	private static ExecutorService getSingleThreadExecutor() {
		return Executors.newSingleThreadExecutor();
	}
	
	public static void givenARunnableLambda_whenSubmitToES_thenResult() 
  throws Exception {
		 
		getSingleThreadExecutor().submit(
		  () -> System.out.println("Lambda runnable executed!"));
	}
	
	public static void main(String... args) throws Exception{
		givenRunnable_whenRunIt_thenResult();
		givenARunnable_whenSubmitToES_thenResult();
		givenARunnableLambda_whenSubmitToES_thenResult();
	}
}