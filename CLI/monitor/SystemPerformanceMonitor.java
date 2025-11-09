package monitor;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
public class SystemPerformanceMonitor implements Runnable {

    private volatile boolean isRunning = true;

    @Override
    public void run() {
        System.out.println("System Performance Monitor started.");
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        while (isRunning) {
            try {
                // Get Heap Memory Usage
                MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
                long usedMemory = heapUsage.getUsed() / (1024 * 1024); // in MB
                long maxMemory = heapUsage.getMax() / (1024 * 1024); // in MB

                String memLog = String.format("Heap Memory Usage: %d MB / %d MB", usedMemory, maxMemory);

                // Log this info
                ActivityLogger.log(memLog);

                // Optional: Print to console
                // System.out.println("[Perf] " + memLog);

                // TODO: Check CPU load (more complex, may require a third-party library like OSHI)

                // Wait for 30 seconds before next check
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interruption status
                System.out.println("Performance monitor thread interrupted.");
            }
        }
        System.out.println("System Performance Monitor stopped.");
    }

    public void stop() {
        this.isRunning = false;
    }
}