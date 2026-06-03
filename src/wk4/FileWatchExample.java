/*
In event-driven systems, decoupling in event handling means designing components so they don’t depend directly on each other to work together.
Instead of one part of a system calling another part directly, it emits an event, and any interested components can respond to that event independently.

The event producer should not need to know who consumes the event.
That separation is what “decoupling” means in event handling.

Why this is useful
1. Easier maintenance
You can change one component without breaking others.
2. Better scalability
Multiple systems can react to the same event.
3. Reusability
Components become more independent and modular.
4. Cleaner architecture
Business logic stays separated.

The provided code is an event driven file monitoring system

This Java program is a file system watcher. Its purpose is to monitor a specific directory and react when new files are created, updated, deleted

WatchService → the main watcher (interface)
FileSystems → factory to create it
Path / Paths → represent file system paths
WatchKey → represents a registered watch
WatchEvent → represents an event (like file creation)
StandardWatchEventKinds → predefined event types (create, modify, delete)
 */
package wk4;
import java.nio.file.*;

public class FileWatchExample {

    public static void main(String[] args) {
// the folder data must exist else will execute the catch block
        Path path = Paths.get("C:\\data");

        //try/resources- all resources can be put inside try 'parameters'seperated by semilcolon; the resource can be declared directly inside try(...) or before it and the object put in try(...)
        //Every object inside try(...) must implement the AutoCloseable interface (or its child interface Closeable). WatchService, Scanner, file streams, database connections, and many other Java resources implement it.
        // WatchService is an interface in the Java NIO (java.nio.file) package.
        // Obtain an implementation of it using FileSystems factory
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

            // Register ALL event types
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.OVERFLOW
            );

            System.out.println("Watching for file events...");

            //take() method is what actually waits for something to happen in the directory
            //It blocks (pauses the program) until a file event occurs (like a file being created).
            // When an event happens, it returns a WatchKey that contains those events.
            while (true) {

                WatchKey key;

                try {
                    key = watchService.take(); // waits for event, pauses execution
                } catch (InterruptedException e) {
                    System.out.println("Watcher interrupted. Exiting...");
                    return;
                }
                //multiple events may have occured, for example, copying 5 files into that directory, so loop through and process all 'events'
                for (WatchEvent<?> event : key.pollEvents()) {

                    WatchEvent.Kind<?> kind = event.kind();
                    Path fileName = (Path) event.context();

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("File CREATED: " + fileName);
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.println("File MODIFIED: " + fileName);
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("File DELETED: " + fileName);
                    } else if (kind == StandardWatchEventKinds.OVERFLOW) {
                        System.out.println("Event overflow occurred (some events may be lost)");
                    } else {
                        System.out.println("Unknown event: " + kind);
                    }
                }

                //handled the events, keep watching
                //Re-enables the key so future events continue being received. Without reset(), the watcher would stop after processing the first batch of events.
                boolean valid = key.reset();

                // This prevents your program from running forever on a broken watch
                // or trying to monitor a directory that no longer exists
                if (!valid) {
                    System.out.println("Watch key no longer valid. Stopping...");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to start file watcher: " + e.getMessage());
        }
    }
}
