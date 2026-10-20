import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 10; i++) {
            final int taskNum = i;
            pool.submit(() -> {
                System.out.println("Task " + taskNum + " running on " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("All tasks done.");
    }
}
