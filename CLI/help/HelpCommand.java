package help;

import app.Main;
import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelpCommand {
    public void execute(Main ui) {
        if (ui == null) {
            return;
        }

        String sql = "SELECT command_alias, description FROM command_map ORDER BY command_alias";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean any = false;
            ui.appendOutput("Available commands:");

            while (rs.next()) {
                any = true;
                String alias = rs.getString("command_alias");
                String desc = rs.getString("description");
                if (alias == null) alias = "";
                if (desc == null) desc = "";
                ui.appendOutput(String.format("%-15s - %s", alias, desc));
            }

            if (!any) {
                ui.appendOutput("No command definitions found in command_map table.");
            }

        } catch (SQLException e) {
            ui.appendOutput("Failed to load help from database: " + e.getMessage());
        }
    }
}
