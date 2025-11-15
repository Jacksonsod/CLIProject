public class HelpCommand {
    public static void showHelp() {
        System.out.println("""
                ==========================================
                       Help & Learning - Command List
                ==========================================

                1.  help              → List all available commands
                2.  manual [cmd]      → Show manual for a specific command
                3.  exit              → Quit the program
                4.  about             → Display information about this program
                5.  contact           → Show support or contact details
                6.  learn [topic]     → Get learning resources for a topic
                7.  version           → Show current version of the program
                8.  clear             → Clear the console screen
                9.  feedback          → Submit your feedback
                10. history           → Show list of previous commands
                11. settings          → View or change system settings
                12. user [name]       → Set or display your username
                13. time              → Display current system time
                14. date              → Display today’s date
                15. topics            → List all available learning topics
                16. quote             → Show an inspirational quote
                17. tips              → Show study or productivity tips
                18. support           → Get help from the support center
                19. restart           → Restart the program
                20. exit              → Exit the Help & Learning system

                ==========================================
                Type "manual [command]" for details about any command.
                ==========================================
                """);
    }
}
