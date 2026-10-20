import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        String[] logs = {
                "10:05 alice Hello there",
                "10:06 bob Hi everyone",
                "10:07 charlie Hello bob",
                "10:08 malformed",
                "10:09 dave Good morning"
        };

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        String result = ChatFilter.filter(logs, keyword);

        System.out.println();
        System.out.println(result);

        scanner.close();
    }
}
