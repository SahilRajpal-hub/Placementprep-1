package Placementprep.LLDs.SMSService;
import java.util.List;
import java.util.concurrent.*;

public class SmsServiceWithCustomExecutor {

    public static void main(String[] args) {
        List<String> phoneNumbers = List.of("1234567890", "9876543210", "5556667777");

        // ThreadPoolExecutor executor = new Executors.newFixedThreadPool(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            4,                       // Core threads
            8,                       // Max threads
            60, TimeUnit.SECONDS,    // Keep-alive time
            new ArrayBlockingQueue<>(20), // A blocking queue that supports FIFO (First In First Out) operations with capacity limits, designed for thread coordination (e.g., producer-consumer patterns).
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy() // Rejection policy
        );

        executor.allowCoreThreadTimeOut(true);  // set core thread timeout to true

        for (String number : phoneNumbers) {
            sendWithRetry(number, 0, executor);
        }

        executor.shutdown();
    }

    static void sendWithRetry(String phoneNumber, int attempt, Executor executor) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100); // Simulate sending delay
                if (Math.random() < 0.5) {
                    throw new RuntimeException("Simulated failure for " + phoneNumber);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }, executor).handleAsync((res, ex) -> {
            if (ex != null) {
                if (attempt < 2) {
                    System.out.println("Retrying " + phoneNumber + " (Attempt " + (attempt + 2) + ")");
                    sendWithRetry(phoneNumber, attempt + 1, executor);
                } else {
                    System.out.println(" Failed to send SMS to " + phoneNumber + " after 3 attempts");
                }
            } else {
                System.out.println(" SMS sent to " + phoneNumber);
            }
            return null;
        }, executor);
    }
}
