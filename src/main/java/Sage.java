import java.util.Scanner;

/**
 * Starts the Sage chatbot application.
 */
public class Sage {
    /**
     * Displays Sage's greeting, stores tasks entered by the user, lists, marks,
     * and unmarks them on request, and ends the chat when the user enters
     * {@code bye}.
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

        Task[] tasks = new Task[100];
        int taskCount = 0;


        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                System.out.println(divider);

                try {
                    if (command.equals("bye")) {
                        System.out.println("Bye. Hope to see you again soon!");
                        System.out.println(divider);
                        break;

                    } else if (command.equals("list")) {
                        System.out.println("Here are the tasks in your list:");
                        for (int index = 0; index < taskCount; index++) {
                            System.out.println((index + 1) + "." + tasks[index]);
                        }

                    } else if (command.startsWith("mark ")) {
                        int taskNumber = Integer.parseInt(command.substring(5));
                        int taskIndex = taskNumber - 1;
                        tasks[taskIndex].markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks[taskIndex]);

                    } else if (command.startsWith("unmark ")) {
                        int taskNumber = Integer.parseInt(command.substring(7));
                        int taskIndex = taskNumber - 1;
                        tasks[taskIndex].markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks[taskIndex]);

                    } else if (command.startsWith("deadline ")) {
                        String deadlineDetails = command.substring(9);

                        int byIndex = deadlineDetails.indexOf(" /by ");
                        String description = deadlineDetails.substring(0, byIndex);
                        String by = deadlineDetails.substring(byIndex + 5);

                        tasks[taskCount] = new Deadline(description, by);
                        taskCount++;

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks[taskCount - 1]);

                        if (taskCount == 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + taskCount + " tasks in the list.");
                        }

                    } else if (command.equals("todo")) {
                        throw new SageException(
                                "Your todo needs a description. Could you tell me what you would like me to add?"
                        );

                    } else if (command.startsWith("todo ")) {
                        String description = command.substring(5);

                        tasks[taskCount] = new Todo(description);
                        taskCount++;

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks[taskCount - 1]);

                        if (taskCount == 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + taskCount + " tasks in the list.");
                        }

                    } else if (command.startsWith("event ")) {
                        String eventDetails = command.substring(6);

                        int fromIndex = eventDetails.indexOf(" /from ");
                        int toIndex = eventDetails.indexOf(" /to ");

                        String description = eventDetails.substring(0, fromIndex);
                        String from = eventDetails.substring(fromIndex + 7, toIndex);
                        String to = eventDetails.substring(toIndex + 5);

                        tasks[taskCount] = new Event(description, from, to);
                        taskCount++;

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks[taskCount - 1]);

                        if (taskCount == 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + taskCount + " tasks in the list.");
                        }

                    } else {
                        tasks[taskCount] = new Task(command);
                        taskCount++;
                        System.out.println("added: " + command);
                    }

                } catch (SageException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println(divider);
            }
        }
    }
}