package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import db.*;
import user.*;
import monitor.*;
import system.*;
import fileops.*;

public class Main extends JFrame {
    private static final String[] activeUser = {null};
    private static final UserRepository repo = new UserRepository();

    private JTextArea terminal;
    private String prompt = "> ";
    private int promptPosition;

    private final List<String> commandHistory = new ArrayList<>();
    private int historyIndex = -1;
    private final long appStartTime;

    public Main() {
        super("CLI User Manager");
        appStartTime = System.currentTimeMillis();
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
                    try {
                        n = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException ignored) {
                    }
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

            // System commands
            if (lower.equals("clear")) {
                new ClearCommand().execute(this);
                return;
            } else if (lower.equals("date")) {
                new DateCommand().execute(this);
                return;
            } else if (lower.equals("time")) {
                new TimeCommand().execute(this);
                return;
            } else if (lower.equals("uptime")) {
                new UptimeCommand(appStartTime).execute(this);
                return;
            } else if (lower.equals("restart")) {
                new RestartCommand().execute(this);
                return;
            } else if (lower.equals("shutdown")) {
                new ShutdownCommand().execute(repo, this);
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
            }else if (lower.startsWith("create folder ")) {
                String folderName = input.substring(14).trim();
                CreateFolderCommand.execute(folderName, activeUser[0], this);


            }
            else if (lower.startsWith("delete folder ")) {
                String folderName = input.substring(14).trim(); // same as before
                DeleteFolderCommand.execute(folderName, activeUser[0], this);
            }
            else if (lower.startsWith("change folder ")) {
                String folderName = input.substring(14).trim(); // same as CreateFolderCommand
                ChangeFolderCommand.execute(folderName, activeUser[0], this);
            } else if (lower.startsWith("list contents ")) {
                String folderName = input.substring(14).trim(); // get folder name after "list contents "
                ListContentsCommand.execute(folderName, activeUser[0], this);
            }
            else if (lower.startsWith("create file ")) {
                String fileName = input.substring("create file ".length()).trim();
                CreateFileCommand.execute(fileName, activeUser[0], this);
            }
            else if (lower.startsWith("delete file ")) {
                String fileName = input.substring("delete file ".length()).trim();
                DeleteFileCommand.execute(fileName, activeUser[0], this);
            }
            else if (lower.startsWith("rename folder ")) {
                String[] parts = input.substring("rename folder ".length()).trim().split("\\s+");
                if (parts.length < 2) {
                    appendOutput("Usage: rename folder <oldName> <newName>");
                } else {
                    String oldName = parts[0];
                    String newName = parts[1];
                    RenameFolderCommand.execute(oldName, newName, activeUser[0], this);
                }
            }
            else if (lower.startsWith("copy folder ")) {
                String[] parts = input.substring("copy folder ".length()).trim().split("\\s+");
                if (parts.length < 2) {
                    appendOutput("Usage: copy folder <sourceName> <destName>");
                } else {
                    String sourceName = parts[0];
                    String destName = parts[1];
                    CopyFolderCommand.execute(sourceName, destName, activeUser[0], this);
                }
            }

            else if (lower.startsWith("read file ")) {
                String fileName = input.substring("read file ".length()).trim();
                ReadFileCommand.execute(fileName, activeUser[0], this);
            }
            else if (lower.startsWith("write file ")) {
                String[] parts = input.substring("write file ".length()).split(" ", 2);
                if (parts.length < 2) {
                    appendOutput("Usage: write file <fileName> <text>");
                } else {
                    String fileName = parts[0].trim();
                    String content = parts[1].trim();
                    WriteFileCommand.execute(fileName, content, activeUser[0], this);
                }
            }



            else if (lower.equals("exit")) {
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

    // Clears the terminal and resets the prompt
    public void clearScreen() {
        // Remove all content
        terminal.setText("");
        // Reprint prompt and reset caret/prompt position
        terminal.append(prompt);
        terminal.setCaretPosition(terminal.getDocument().getLength());
        promptPosition = terminal.getDocument().getLength();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}