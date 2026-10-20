import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {
    public static String generate(Path logsDir, Path reportFile) throws IOException {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int fileCount = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("===== MiniBank Transaction Report =====\n");

        if (Files.exists(logsDir) && Files.isDirectory(logsDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry) && entry.toString().endsWith(".log")) {
                        fileCount++;
                        BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                        sb.append("Log file: ").append(entry.getFileName())
                          .append(" | Size: ").append(attrs.size()).append(" bytes")
                          .append(" | Modified: ").append(attrs.lastModifiedTime()).append("\n");

                        try (BufferedReader reader = Files.newBufferedReader(entry)) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                line = line.trim();
                                if (line.startsWith("DEPOSIT")) {
                                    String[] parts = line.split("\\s+");
                                    if (parts.length >= 3) {
                                        totalDeposits += Long.parseLong(parts[2]);
                                    }
                                } else if (line.startsWith("WITHDRAW")) {
                                    String[] parts = line.split("\\s+");
                                    if (parts.length >= 3) {
                                        totalWithdrawals += Long.parseLong(parts[2]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        long netChange = totalDeposits - totalWithdrawals;
        sb.append("Total log files processed: ").append(fileCount).append("\n");
        sb.append("Total Deposits: ").append(totalDeposits).append("\n");
        sb.append("Total Withdrawals: ").append(totalWithdrawals).append("\n");
        sb.append("Net Balance Change: ").append(netChange).append("\n");
        sb.append("======================================\n");

        String reportText = sb.toString();
        if (reportFile != null) {
            if (reportFile.getParent() != null && !Files.exists(reportFile.getParent())) {
                Files.createDirectories(reportFile.getParent());
            }
            Files.writeString(reportFile, reportText);
        }
        return reportText;
    }
}
