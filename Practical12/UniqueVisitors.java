import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
            "alice", "bob", "charlie", "alice", "david", "bob", "eva", "alice"
        );

        Set<String> uniqueSet = new HashSet<>(logins);
        System.out.println("Unique visitor count: " + uniqueSet.size());

        Set<String> orderedSet = new LinkedHashSet<>(logins);
        System.out.println("First three distinct visitors:");
        int count = 0;
        for (String user : orderedSet) {
            System.out.println(user);
            count++;
            if (count == 3) {
                break;
            }
        }
    }
}
