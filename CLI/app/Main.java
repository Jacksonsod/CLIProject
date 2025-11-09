package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import db.*;
import user.*;
import monitor.*;

public class Main extends JFrame {
    private static final String[] activeUser = { null };
    private static final UserRepository repo = new UserRepository();

    private JTextArea terminal;
    private String prompt = "> ";
    private int promptPosition;

    private final List<String> commandHistory = new ArrayList<>();
    private int historyIndex = -1;

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

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> {
                        e.consume();
                        handleCommand();
                    }
                    case KeyEvent.VK_UP -> {
                        e.consume();
                        if (historyIndex > 0) {
                            historyIndex--;
                            replaceCurrentLine(commandHistory.get(historyIndex));
                        }
                    }
                    case KeyEvent.VK_DOWN -> {
                        e.consume();
                        if (historyIndex < commandHistory.size() - 1) {
                            historyIndex++;
                            replaceCurrentLine(commandHistory.get(historyIndex));
                        } else {
                            replaceCurrentLine("");
                        }
                    }
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

        if (!command.isEmpty()) {
            commandHistory.add(command);
            historyIndex = commandHistory.size();
        }

        processCommand(command);
        appendOutput("\n" + prompt);
        promptPosition = terminal.getDocument().getLength();
    }

    private void processCommand(String input) {
        String lower = input.toLowerCase();

        try {
            if (activeUser[0] == null) {
                if (lower.equals("create user")) {
                    String[] creds = CreateUserDialog.promptForCredentials();
                    if (creds != null) {
                        String username = creds[0];
                        String password = creds[1];
                        CreateUserCommand.execute(username + ":" + password, repo, this);
                    } else {
                        appendOutput("User creation canceled.");
                    }
                } else if (lower.equals("login")) {
                    String[] creds = LoginDialog.promptForCredentials();
                    if (creds != null) {
                        String username = creds[0];
                        String password = creds[1];
                        LoginCommand.execute(username + ":" + password, repo, activeUser, this);
                        if (activeUser[0] != null) {
                            appendOutput("Welcome, " + activeUser[0]);
                            updateTitle();
                        }
                    } else {
                        appendOutput("Login canceled.");
                    }
                } else {
                    appendOutput("Available commands: create user, login");
                }
                return;
            }

            if (lower.equals("logout")) {
                LogoutCommand.execute(activeUser, this);
                appendOutput("Session ended. Please log in again.");
                updateTitle();
                return;
            }

            // Monitor commands (available whether logged in or not, except those that require session data)
            if (lower.equals("history")) {
                HistoryCommand.execute(commandHistory, this);
                return;
            } else if (lower.startsWith("log")) {
                String[] parts = input.trim().split("\\s+");
                Integer n = null;
                if (parts.length > 1) {
                    try { n = Integer.parseInt(parts[1]); } catch (NumberFormatException ignored) {}
                }
                LogCommand.execute(this, n);
                return;
            } else if (lower.equals("status")) {
                StatusCommand.execute(activeUser[0], this);
                return;
            } else if (lower.equals("memory")) {
                MemoryCommand.execute(this);
                return;
            }

            if (lower.equals("create user")) {
                String[] creds = CreateUserDialog.promptForCredentials();
                if (creds != null) {
                    String username = creds[0];
                    String password = creds[1];
                    CreateUserCommand.execute(username + ":" + password, repo, this);
                } else {
                    appendOutput("User creation canceled.");
                }
            } else if (lower.equals("login")) {
                String[] creds = LoginDialog.promptForCredentials();
                if (creds != null) {
                    String username = creds[0];
                    String password = creds[1];
                    LoginCommand.execute(username + ":" + password, repo, activeUser, this);
                    if (activeUser[0] != null) {
                        appendOutput("Welcome back, " + activeUser[0]);
                        updateTitle();
                    }
                } else {
                    appendOutput("Login canceled.");
                }
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
                appendOutput("Unknown command.\nTry: create user, login, delete user, list users, switch user, logout, exit");
            }
        } catch (Exception ex) {
            appendOutput("Error: " + ex.getMessage());
        }
    }

    private void replaceCurrentLine(String newText) {
        try {
            int start = promptPosition;
            int end = terminal.getDocument().getLength();
            terminal.getDocument().remove(start, end - start);
            terminal.getDocument().insertString(start, newText, null);
        } catch (Exception ex) {
            appendOutput("Error updating command line: " + ex.getMessage());
        }
    }

    private void updateTitle() {
        if (activeUser[0] != null) {
            setTitle("CLI User Manager — " + activeUser[0]);
        } else {
            setTitle("CLI User Manager");
        }
    }

    public void appendOutput(String message) {
        terminal.append(message + "\n");
        terminal.setCaretPosition(terminal.getDocument().getLength());
        ActivityLogger.getInstance().log(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}