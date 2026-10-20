import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class LogAnalyzer {

    public static void main(String[] args) throws Exception {
        Path logFile = Paths.get("app.log");

        List<String> lines = Arrays.asList("INFO: App started", "WARN: Low memory", "ERROR: Null pointer");
        Files.write(logFile, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        List<String> read = Files.readAllLines(logFile);
        System.out.println("Line count: " + read.size());
        for (String line : read) {
            System.out.println(line);
        }
    }
}
