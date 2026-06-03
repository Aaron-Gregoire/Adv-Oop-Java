/*
 * This program demonstrates concurrent task execution using a fixed thread pool
 * together with event handling using CompletionService.
 *
 * Each task performs REAL WORK (file writing), then completes after a
 * random delay. CompletionService acts as an event system that detects
 * when tasks finish and allows the main thread to respond in completion order.
 *
 * This program demonstrates:
 *
 * 1. Concurrency:
 *    Multiple tasks execute independently using a thread pool.
 *
 * 2. Real Work:
 *    Each task writes to a file and simulates processing time.
 *
 * 3. Event Handling:
 *    The main thread reacts to task completion events using:
 *    - take() → waits for next completed task (event trigger)
 *    - get()  → retrieves task result
 *
 * 4. Ordered Event Logging:
 *    log.txt is written ONLY by the event handler (main thread),
 *    ensuring it matches actual completion order.
 *
 * Event Model:
 *
 * Event Source   : Worker threads (tasks)
 * Event          : Task completion
 * Event Handler  : Main thread using CompletionService
 *
 * Real-life examples:
 *
 * - Order processing system:
 *   Kitchen prepares orders (tasks) and a display system updates
 *   when each order is completed.
 *
 * - Warehouse logistics:
 *   Packages are processed in parallel and tracking system updates
 *   when each package is finished.
 *
 * - Cloud computing:
 *   Jobs run on multiple servers and monitoring system reacts when
 *   each job completes.
 *
 * Concurrency vs Parallelism:
 * - Concurrency: tasks progress independently
 * - Parallelism: tasks may run simultaneously on multi-core CPUs
 */
package wk4;
import java.nio.file.*;
import java.util.concurrent.*;

public class ConcurrentTaskEventExample {

    public static void main(String[] args) {

        // Thread pool with 3 worker threads
        try (ExecutorService executor
                = Executors.newFixedThreadPool(3)) {

            /*
             * CompletionService acts like an event queue.
             * It automatically stores completed tasks.
             */
            CompletionService<String> service
                    = new ExecutorCompletionService<>(executor);

            /*
             * Submit multiple tasks to the thread pool
             */
            for (int i = 1; i <= 5; i++) {

                int taskId = i;

                service.submit(() -> {

                    try {

                        // -------------------------------
                        // REAL WORK (done by worker thread)
                        // -------------------------------
                        System.out.println(
                                "Task " + taskId
                                + " started on "
                                + Thread.currentThread().getName()
                        );

                        // File writing = real I/O work
                        Files.writeString(
                                Path.of("task-data.txt"),
                                "Task " + taskId + " executed\n",
                                StandardOpenOption.CREATE,
                                StandardOpenOption.APPEND
                        );

                        // Simulate processing time
                        Thread.sleep(
                                (long) (Math.random() * 3000)
                        );

                        System.out.println(
                                "Task " + taskId + " finished"
                        );

                        // Task result (sent to CompletionService)
                        return "Task " + taskId + " completed successfully";

                    } catch (Exception e) {

                        return "Task " + taskId + " failed";
                    }
                });
            }

            System.out.println("\nWaiting for completion events...\n");

            /*
             * EVENT HANDLING LOOP
             *
             * This is where the main thread reacts to task completion.
             */
            for (int i = 1; i <= 5; i++) {

                /*
                 * take():
                 * Waits for the next completed task (event trigger)
                 */
                Future<String> completedTask = service.take();

                /*
                 * get():
                 * Retrieves the result of the completed task
                 */
                String result = completedTask.get();

                /*
                 * EVENT ACTION (handler response)
                 * Only the main thread writes to log.txt,
                 * ensuring correct completion order.
                 */
                Files.writeString(
                        Path.of("log.txt"),
                        result + "\n",
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );

                System.out.println(
                        "Completion event received: " + result
                );
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}
