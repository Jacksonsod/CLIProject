package help;

import app.Main;
import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelpCommand {

    private static final String QUERY =
            "SELECT command_name, description FROM commands ORDER BY command_name ASC";

    public void execute(Main ui) {
        if (ui == null) return;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY);
             ResultSet rs = ps.executeQuery()) {

            ui.appendOutput("=== AVAILABLE COMMANDS ===\n");

            while (rs.next()) {
                String name = rs.getString("command_name");
                String desc = rs.getString("description");

                ui.appendOutput(String.format("%-18s - %s", name, desc));
            }

            ui.appendOutput("\nUse 'manual <command>' to view detailed command documentation.");

        } catch (SQLException e) {
            ui.appendOutput("Error loading help commands: " + e.getMessage());
        }
    }
}
