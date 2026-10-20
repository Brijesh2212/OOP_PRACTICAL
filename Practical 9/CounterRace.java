class UnsafeCounter {
    int count = 0;

    void increment() {
        count++;
    }
}

class SafeCounter {
    int count = 0;

    synchronized void increment() {
        count++;
    }
}

public class CounterRace {

    public static void main(String[] args) throws InterruptedException {
        UnsafeCounter unsafe = new UnsafeCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) unsafe.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) unsafe.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Unsafe counter (expected 2000): " + unsafe.count);

        SafeCounter safe = new SafeCounter();

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) safe.increment();
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) safe.increment();
        });

        t3.start();
        t4.start();
        t3.join();
        t4.join();

        System.out.println("Safe counter (expected 2000): " + safe.count);
    }
}
