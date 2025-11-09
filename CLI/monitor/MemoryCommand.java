package monitor;

import app.Main;

public class MemoryCommand {
    public static void execute(Main ui) {
        Runtime rt = Runtime.getRuntime();
        long max = rt.maxMemory();
        long total = rt.totalMemory();
        long free = rt.freeMemory();
        long used = total - free;
        ui.appendOutput("Memory:");
        ui.appendOutput("- Used: " + formatBytes(used));
        ui.appendOutput("- Free: " + formatBytes(free));
        ui.appendOutput("- Total: " + formatBytes(total));
        ui.appendOutput("- Max: " + formatBytes(max));
    }

    private static String formatBytes(long b) {
        double kb = b / 1024.0;
        double mb = kb / 1024.0;
        double gb = mb / 1024.0;
        if (gb >= 1) return String.format("%.2f GB", gb);
        if (mb >= 1) return String.format("%.2f MB", mb);
        if (kb >= 1) return String.format("%.2f KB", kb);
        return b + " B";
    }
}
