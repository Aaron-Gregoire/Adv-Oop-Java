/*
 * This program demonstrates the use of an ExecutorService with a fixed
 * thread pool of 3 threads. Five tasks are submitted and
 * executed concurrently. Each task pauses for a random
 * amount of time to simulate work, allowing us to observe
 * that tasks may complete in different orders. The program
 * also shows how threads are reused from the thread pool.
 * Cocurrency - Multiple threads exist, they progress independently, one may wait while the other runs
 * Not parallel execution- can occur if there are multiple CPU cores
 */
package wk4;
import java.util.concurrent.*;

public class ThreadPoolLoopExample {

    public static void main(String[] args) {

        // Create a thread pool with 3 worker threads
        try (ExecutorService executor
                = Executors.newFixedThreadPool(3)) {

            // Loop to create and submit 5 tasks
            for (int i = 1; i <= 5; i++) {

                // Create a local copy because lambdas
                // require effectively final variables
                int taskNumber = i;

                // Submit task to thread pool
                executor.submit(() -> {

                    try {

                        // Display task number and thread name
                        System.out.println(
                                "Task " + taskNumber
                                + " started on "
                                + Thread.currentThread().getName()
                        );

                        // Pause for a random time
                        Thread.sleep(
                                (long) (Math.random() * 3000)
                        );

                        // Display completion message
                        System.out.println(
                                "Task " + taskNumber
                                + " finished on "
                                + Thread.currentThread().getName()
                        );

                    } catch (InterruptedException e) {

                        // Handle interruption
                        System.out.println(
                                "Task " + taskNumber
                                + " was interrupted"
                        );
                    }
                });
            }
        }
    }
}
