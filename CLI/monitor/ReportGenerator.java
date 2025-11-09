package monitor;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
public class ReportGenerator {

    public void generateReport(String data) {
        String fileName = "MonitoringReport-" + LocalDate.now() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("=======================================\n");
            writer.write("     MONITORING REPORT - " + LocalDate.now() + " \n");
            writer.write("=======================================\n\n");

            writer.write("Summary Data:\n");
            writer.write(data + "\n\n");

            // TODO: Add more details, e.g., read from log files,
            // get stats from ClientTracker and SystemPerformanceMonitor

            writer.write("========== END OF REPORT ==========\n");

            System.out.println("Report generated successfully: " + fileName);
            ActivityLogger.log("Report generated: " + fileName);

        } catch (IOException e) {
            System.err.println("Could not generate report: " + e.getMessage());
            ActivityLogger.log("Failed to generate report: " + e.getMessage());
        }
    }
}