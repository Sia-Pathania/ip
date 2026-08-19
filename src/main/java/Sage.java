import java.util.Scanner;

/**
 * Starts the Sage chatbot application.
 */
public class Sage {
    /**
     * Displays Sage's greeting, stores tasks entered by the user, lists them on
     * request, and ends the chat when the user enters {@code bye}.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        String banner = " ____                   \n"
                + "/ ___|  __ _  __ _  ___ \n"
                + "\\___ \\ / _` |/ _` |/ _ \\\n"
                + " ___) | (_| | (_| |  __/\n"
                + "|____/ \\__,_|\\__, |\\___|\n"
                + "             |___/      \n";

        System.out.println(divider);
        System.out.print(banner);
        System.out.println("Hello! I'm Sage.");
        System.out.println("I'm here whenever you feel like chatting!");
        System.out.println(divider);
        System.out.println("What can I do for you?");
        System.out.println(divider);

        String[] tasks = new String[100];
        int taskCount = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                System.out.println(divider);

                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(divider);
                    break;
                } else if (command.equals("list")) {
                    for (int index = 0; index < taskCount; index++) {
                        System.out.println((index + 1) + ". " + tasks[index]);
                    }
                } else {
                    tasks[taskCount] = command;
                    taskCount++;
                    System.out.println("added: " + command);
                }

                System.out.println(divider);
            }
        }
    }
}
