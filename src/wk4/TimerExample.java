/*
 * Program: TimerExample
 *
 * Purpose:
 * Demonstrates event handling using a time-based event.
 * A Timer acts as the event source and generates an event
 * after a specified delay (3 seconds). A TimerTask acts
 * as the event listener/handler and automatically responds
 * when the event occurs.
 *
 * The program demonstrates:
 * - registering an event handler
 * - waiting for an event to occur
 * - automatic execution of the event handler
 * - exception handling inside the event handler
 *
 * Event Source: Timer
 * Event: 3 seconds have passed
 * Event Handler: run()
 */
package wk4;
import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {

    public static void main(String[] args) {

        // Create a Timer object.
        // The Timer acts as the event source and creates
        // its own background thread to monitor time events.
        Timer timer = new Timer();

        // Create the event handler.
        // The run() method will automatically execute
        // when the timer event occurs.
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                try {

                    // Event handler code begins here.
                    // This code executes only after
                    // the timer event is triggered.
                    System.out.println(
                            "Timer event occurred."
                    );

                    // Simulate a possible error
                    String message = null;

                    // Causes NullPointerException
                    System.out.println(
                            message.toUpperCase()
                    );

                } catch (Exception e) {

                    // Handle errors inside the event handler
                    // so the Timer thread does not terminate.
                    System.out.println(
                            "Error in event handler: "
                            + e.getMessage()
                    );

                } finally {

                    // Stop timer after event processing
                    timer.cancel();

                    System.out.println(
                            "Timer stopped."
                    );
                }
            }
        };

        try {

            // Register the event handler with the Timer.
            //
            // schedule(task,3000):
            // - task = event handler to execute
            // - 3000 = wait 3000 milliseconds (3 sec)
            //
            // Sequence:
            //
            // Timer created
            //       ↓
            // Handler registered
            //       ↓
            // Wait 3 seconds
            //       ↓
            // Event occurs
            //       ↓
            // run() automatically executes
            timer.schedule(task, 3000);

            System.out.println(
                    "Waiting for timer event..."
            );

        } catch (IllegalArgumentException e) {

            // Runs if invalid delay value supplied
            System.out.println(
                    "Invalid delay: "
                    + e.getMessage()
            );

        } catch (IllegalStateException e) {

            // Runs if timer was already cancelled
            System.out.println(
                    "Timer already cancelled: "
                    + e.getMessage()
            );

        } catch (Exception e) {

            // Handle unexpected scheduling errors
            System.out.println(
                    "Unexpected error: "
                    + e.getMessage()
            );
        }
    }
}
