package user;

import javax.swing.*;
import java.awt.*;

public class CreateAdminDialog {

    public static String[] promptForAdminCredentials() {
        JTextField usernameField = new JTextField(16);
        JPasswordField passwordField = new JPasswordField(16);

        Font headerFont = new Font("SansSerif", Font.BOLD, 16);
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font fieldFont = new Font("Monospaced", Font.PLAIN, 14);

        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.GREEN);
        usernameField.setCaretColor(Color.GREEN);
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.GREEN);
        passwordField.setCaretColor(Color.GREEN);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        panel.setBackground(new Color(20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.LINE_END;

        // Header spanning two columns
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel header = new JLabel("Create Admin User");
        header.setFont(headerFont);
        header.setForeground(Color.GREEN);
        panel.add(header, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(passwordField, gbc);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Create Admin User",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                return new String[] { username, password };
            }
        }
        return null;
    }
}
