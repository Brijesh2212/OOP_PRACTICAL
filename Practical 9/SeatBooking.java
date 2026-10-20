public class SeatBooking {

    private static final int TOTAL_SEATS = 5;
    private static int availableSeats = TOTAL_SEATS;

    public static synchronized boolean bookSeat(String passengerName) {
        if (availableSeats > 0) {
            availableSeats--;
            System.out.println(Thread.currentThread().getName() + " - " + passengerName
                    + " booked a seat. Remaining: " + availableSeats);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " - " + passengerName
                    + " could not book. No seats left.");
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] passengers = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace"};

        Thread[] threads = new Thread[passengers.length];

        for (int i = 0; i < passengers.length; i++) {
            final String name = passengers[i];
            threads[i] = new Thread(() -> bookSeat(name), "Booking-Thread");
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Final available seats: " + availableSeats);
    }
}
