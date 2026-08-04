import java.util.Scanner;

public class VendingMachine {

    enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int price = 15;
        int total = 0;

        while (total < price) {

            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            String input = sc.next().toUpperCase();

            Coin coin = Coin.valueOf(input);

            int value;
            switch (coin) {
                case ONE:
                    value = 1;
                    break;
                case TWO:
                    value = 2;
                    break;
                case FIVE:
                    value = 5;
                    break;
                case TEN:
                    value = 10;
                    break;
                default:
                    value = 0;
            }

            total = total + value;

            System.out.println("Total = " + total);
        }

        System.out.println("Paid. Change: " + (total - price));

        sc.close();
    }
}