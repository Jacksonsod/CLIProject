package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import db.*;
import user.*;

public class Main extends JFrame {
    private static final String[] activeUser = { null };
    private static final UserRepository repo = new UserRepository();

    private JTextArea terminal;
    private String prompt = "> ";
    private int promptPosition;

    public Main() {
        super("CLI User Manager");

        CommandSeeder.seedCommands();

        terminal = new JTextArea();
        terminal.setFont(new Font("serif", Font.PLAIN, 18));
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setCaretColor(Color.GREEN);
        terminal.setLineWrap(true);
        terminal.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(terminal);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        appendOutput("Booting CLI User Manager...\nPlease create a user and log in.\n" + prompt);
        promptPosition = terminal.getDocument().getLength();

        terminal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int caret = terminal.getCaretPosition();
                if (caret < promptPosition) {
                    terminal.setCaretPosition(terminal.getDocument().getLength());
                    e.consume();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    handleCommand();
                }
            }
        });

        terminal.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (terminal.getCaretPosition() < promptPosition) {
                        terminal.setCaretPosition(terminal.getDocument().getLength());
                    }
                });
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        terminal.requestFocusInWindow();
    }

    private void handleCommand() {
        String text = terminal.getText();
        String command = text.substring(promptPosition).trim();

        appendOutput("\n");
        processCommand(command);
        appendOutput("\n" + prompt);
        promptPosition = terminal.getDocument().getLength();
    }

    private void processCommand(String input) {
        String lower = input.toLowerCase();

        try {
            if (activeUser[0] == null) {
                if (lower.startsWith("create user ")) {
                    CreateUserCommand.execute(input.substring(12), repo, this);
                } else if (lower.startsWith("login ")) {
                    LoginCommand.execute(input.substring(6), repo, activeUser, this);
                } else {
                    appendOutput("Available commands: create user username:password, login username:password");
                }
                if (activeUser[0] != null) {
                    appendOutput("Welcome, " + activeUser[0]);
                }
                return;
            }

            if (lower.equals("logout")) {
                LogoutCommand.execute(activeUser, this);
                appendOutput("Session ended. Please log in again.");
                return;
            }

            if (lower.startsWith("login ")) {
                LoginCommand.execute(input.substring(6), repo, activeUser, this);
                if (activeUser[0] != null) {
                    appendOutput("Welcome back, " + activeUser[0]);
                }
            } else if (lower.startsWith("create user ")) {
                CreateUserCommand.execute(input.substring(12), repo, this);
            } else if (lower.startsWith("delete user ")) {
                DeleteUserCommand.execute(input.substring(12), repo, activeUser, this);
            } else if (lower.equals("list users")) {
                ListUsersCommand.execute(repo, this);
            } else if (lower.startsWith("switch user ")) {
                SwitchUserCommand.execute(input.substring(12), repo, activeUser, this);
            } else if (lower.startsWith("promote user ")) {
                PromoteUserCommand.execute(input.substring(13), repo, activeUser, this);
            } else if (lower.startsWith("change password ")) {
                User current = repo.findUser(activeUser[0]);
                if (current != null) {
                    ChangePasswordCommand.execute(input.substring(16), current, repo, this);
                } else {
                    appendOutput("No active user found.");
                }
            } else if (lower.startsWith("reset password ")) {
                User current = repo.findUser(activeUser[0]);
                if (current != null) {
                    ResetPasswordCommand.execute(input.substring(15), current, repo, this);
                }
            } else if (lower.equals("exit")) {
                appendOutput("Goodbye.");
                System.exit(0);
            } else {
                appendOutput("Unknown command.\nTry: create user, delete user, list users, switch user, logout, exit");
            }
        } catch (Exception ex) {
            appendOutput("Error: " + ex.getMessage());
        }
    }

    public void appendOutput(String message) {
        terminal.append(message + "\n");
        terminal.setCaretPosition(terminal.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
