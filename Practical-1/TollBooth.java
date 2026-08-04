import java.util.Scanner;

class Vehicle {
    private String number;
    private String type;

    public Vehicle(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }
}

public class TollBooth {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int totalToll = 0;
        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        while (true) {
            System.out.print("Enter vehicle number (or 'done' to stop): ");
            String number = sc.nextLine();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = sc.nextLine().toLowerCase();

            Vehicle v = new Vehicle(number, type);

            int toll;
            switch (v.getType()) {
                case "bike":
                    toll = 20;
                    bikeCount++;
                    break;
                case "car":
                    toll = 50;
                    carCount++;
                    break;
                case "truck":
                    toll = 150;
                    truckCount++;
                    break;
                default:
                    toll = 0;
                    break;
            }

            totalToll += toll;
        }

        String mostFrequent = "bike";

        if (carCount >= bikeCount && carCount >= truckCount) {
            mostFrequent = "car";
        } else if (truckCount >= bikeCount && truckCount >= carCount) {
            mostFrequent = "truck";
        }

        System.out.println("Total toll: " + totalToll);
        System.out.println("Most frequent: " + mostFrequent);

        sc.close();
    }
}