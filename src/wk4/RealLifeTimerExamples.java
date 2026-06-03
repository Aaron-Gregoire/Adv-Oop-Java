/*
 * This program demonstrates callback-based event handling using
 * Timer and TimerTask.
 * 
 * TimerTask acts as the event handler.
 * Timer acts as the event source and scheduler.
 * When the scheduled time arrives,
 * Timer automatically invokes the
 * run() method of the TimerTask.
 * Similar to a push-based Observer pattern because
 * handlers are automatically notified when events occur.
 *
 * Demonstrates real-life uses of Timer and TimerTask
 * for time-based event handling.
 *
 * Examples:
 * 1. Reminder notification
 * 2. Auto-save feature
 * 3. Session timeout
 * 4. Game event
 * 5. Scheduled email
 * 6. Background maintenance
 */
package wk4;
import java.util.Timer;
import java.util.TimerTask;

public class RealLifeTimerExamples {

    public static void main(String[] args) {

        // Create timer (event source)
        Timer timer = new Timer();

        // --------------------------------------------------
        // Example 1: Reminder Notification
        // Event: 3 seconds pass
        // Action: Show reminder
        // --------------------------------------------------
        TimerTask reminderTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(
                        "Reminder: Time to take a break!"
                );
            }
        };

        timer.schedule(reminderTask, 3000);

        // --------------------------------------------------
        // Example 2: Auto-save Document
        // Event: Every 5 seconds
        // Action: Save file automatically
        // --------------------------------------------------
        TimerTask autoSaveTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(
                        "Document auto-saved."
                );
            }
        };
//Start auto-save after 2 seconds and attempt to save every 5 seconds on a regular schedule;scheduleAtFixedRate(task, initialDelay, period)
        timer.scheduleAtFixedRate(
                autoSaveTask,
                2000,
                5000
        );

        // --------------------------------------------------
        // Example 3: Session Timeout
        // Event: User inactive for 8 seconds
        // Action: Log out user
        // --------------------------------------------------
        TimerTask logoutTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(
                        "Session expired. User logged out."
                );
            }
        };

        timer.schedule(logoutTask, 8000);

        // --------------------------------------------------
        // Example 4: Game Event
        // Event: 6 seconds pass
        // Action: Spawn enemy
        // --------------------------------------------------
        TimerTask gameTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(
                        "Enemy spawned!"
                );
            }
        };

        timer.schedule(gameTask, 6000);

        // --------------------------------------------------
        // Example 5: Scheduled Email
        // Event: 10 seconds pass
        // Action: Send email
        // --------------------------------------------------
        TimerTask emailTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(
                        "Scheduled email sent."
                );
            }
        };

        timer.schedule(emailTask, 10000);

        // --------------------------------------------------
        // Example 6: Background Maintenance
        // Event: Every 7 seconds
        // Action: Clean temporary files
        // --------------------------------------------------
        TimerTask maintenanceTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(
                        "Temporary files cleaned."
                );
            }
        };

        timer.scheduleAtFixedRate(
                maintenanceTask,
                1000,
                7000
        );
        /*
// Stop everything after 20 seconds
timer.schedule(new TimerTask() {

    @Override
    public void run() {

        System.out.println(
                "Stopping timer..."
        );

        timer.cancel();
    }

}, 20000); */
        System.out.println(
                "Waiting for scheduled events..."
        );
    }
}
