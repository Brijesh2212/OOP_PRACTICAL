public class ProducerConsumer {

    static class SharedBuffer {
        private int[] buffer;
        private int count = 0;
        private int capacity;

        SharedBuffer(int capacity) {
            this.capacity = capacity;
            this.buffer = new int[capacity];
        }

        synchronized void add(int value) throws InterruptedException {
            while (count == capacity) {
                wait();
            }
            buffer[count++] = value;
            System.out.println("Produced: " + value + " | Buffer size: " + count);
            notify();
        }

        synchronized int remove() throws InterruptedException {
            while (count == 0) {
                wait();
            }
            int val = buffer[--count];
            System.out.println("Consumed: " + val + " | Buffer size: " + count);
            notify();
            return val;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SharedBuffer buf = new SharedBuffer(3);

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    buf.add(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    buf.remove();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Producer-Consumer done.");
    }
}
