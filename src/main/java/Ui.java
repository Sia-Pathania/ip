import java.util.Scanner;

/**
 * Handles all direct interaction between Sage and the user.
 */
public class Ui implements AutoCloseable {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String BANNER = " ____                   \n"
            + "/ ___|  __ _  __ _  ___ \n"
            + "\\___ \\ / _` |/ _` |/ _ \\\n"
            + " ___) | (_| | (_| |  __/\n"
            + "|____/ \\__,_|\\__, |\\___|\n"
            + "             |___/      \n";

    private final Scanner scanner;

    /** Creates a UI that reads commands from standard input. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Displays Sage's standard welcome message. */
    public void showWelcome() {
        showDivider();
        System.out.print(BANNER);
        show("Hello! I'm Sage.");
        show("I'm here whenever you feel like chatting!");
        showDivider();
        show("What can I do for you?");
        showDivider();
    }

    /** Displays the divider used to separate user commands and responses. */
    public void showDivider() {
        show(DIVIDER);
    }

    /** Displays one line of text to the user. */
    public void show(String message) {
        System.out.println(message);
    }

    /** Returns the next command, or {@code null} when input has ended. */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    /** Closes the input scanner. */
    @Override
    public void close() {
        scanner.close();
    }
}
