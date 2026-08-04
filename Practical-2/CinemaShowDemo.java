class CinemaShow
{
    private String title;
    private int seatsAvailable;
    private final int capacity;

    private static int totalBooked = 0;

    CinemaShow(String title, int capacity)
    {
        this.title = title;
        this.capacity = capacity;
        seatsAvailable = capacity;
    }

    CinemaShow(String title)
    {
        this(title, 100);
    }

    boolean book(int n)
    {
        if(n <= seatsAvailable)
        {
            seatsAvailable = seatsAvailable - n;
            totalBooked = totalBooked + n;
            return true;
        }
        else
        {
            return false;
        }
    }

    void cancel(int n)
    {
        seatsAvailable = seatsAvailable + n;

        if(seatsAvailable > capacity)
        {
            seatsAvailable = capacity;
        }
    }

    int getSeatsAvailable()
    {
        return seatsAvailable;
    }

    String getTitle()
    {
        return title;
    }

    static int getTotalBooked()
    {
        return totalBooked;
    }
}

public class CinemaShowDemo
{
    public static void main(String[] args)
    {
        CinemaShow show = new CinemaShow("Avengers", 50);

        System.out.println("Title = " + show.getTitle());
        System.out.println(show.book(20));
        System.out.println("Seats Available = " + show.getSeatsAvailable());

        System.out.println(show.book(15));
        System.out.println("Seats Available = " + show.getSeatsAvailable());

        System.out.println(show.book(20));
        System.out.println("Seats Available = " + show.getSeatsAvailable());

        show.cancel(10);
        System.out.println("Seats Available = " + show.getSeatsAvailable());

        System.out.println(show.book(15));
        System.out.println("Seats Available = " + show.getSeatsAvailable());

        show.cancel(50);
        System.out.println("Seats Available = " + show.getSeatsAvailable());

        System.out.println("Total Booked = " + CinemaShow.getTotalBooked());
    }
}