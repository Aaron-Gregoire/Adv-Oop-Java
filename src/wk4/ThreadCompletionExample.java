
/*
 * This program demonstrates a pull-based event mechanism using Future.
 * A background task acts as an event producer while the
 * main thread acts as a listener waiting for task
 * completion. The main thread retrieves the event result
 * using future.get() rather than receiving automatic
 * notifications.
 * This program demonstrates concurrency and task
 * scheduling, not event handling.
 */
package wk4;
import java.util.concurrent.*;

public class ThreadCompletionExample {

    public static void main(String[] args) {

        // Create an ExecutorService with one worker thread.
        // newSingleThreadExecutor() creates a thread pool
        // that executes one task at a time.
        ExecutorService executor
                = Executors.newSingleThreadExecutor();

        try {

            // submit()
            // Sends a task to the executor to run in a
            // background thread and returns a Future object.
            //
            // Future<String> acts as a placeholder for a
            // result that will be available later.
            Future<String> future = executor.submit(() -> {

                try {

                    // Simulate a time-consuming task.
                    // sleep() pauses the current thread
                    // for 2000 milliseconds (2 seconds).
                    //
                    // In real applications, a task might:
                    // - read a file
                    // - perform calculations
                    // - process data
                    // - handle network communication
                    Thread.sleep(2000);

                } catch (InterruptedException e) {

                    // Runs if the worker thread is interrupted while sleeping
                    System.out.println(
                            "Task was interrupted"
                    );

                    return "Task interrupted";
                }

                // Returned value becomes the result
                // stored in the Future object.
                return "Task finished!";
            });

            // Main thread continues running while the
            // background task executes.
            System.out.println("Waiting...");

            try {

                // get()
                // Waits (blocks) until the background
                // task finishes execution.
                //
                // If the task is already complete,
                // get() immediately returns the result.
                //
                // If not complete, the main thread pauses
                // until completion.
                String result = future.get();

                // Display returned task result
                System.out.println(result);

            } catch (InterruptedException e) {

                // Runs if the main thread is interrupted
                // while waiting for get().
                System.out.println(
                        "Main thread interrupted while waiting"
                );

            } catch (ExecutionException e) {

                // Runs if an exception occurred inside
                // the task itself.
                System.out.println(
                        "Task failed: "
                        + e.getCause()
                );
            }

        } catch (Exception e) {

            // Handle unexpected program errors
            System.out.println(
                    "Unexpected error: "
                    + e.getMessage()
            );

        } finally {

            // shutdown()
            // Stops the executor from accepting
            // new tasks and allows current tasks
            // to finish before terminating.
            executor.shutdown();
        }
    }
}
