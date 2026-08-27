import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

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
    public static void main(String[] args) throws IOException {
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

        Storage storage = new Storage("data/sage.txt");
        ArrayList<Task> tasks = storage.load();


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
                        for (int index = 0; index < tasks.size(); index++) {
                            System.out.println((index + 1) + "." + tasks.get(index));
                        }

                    } else if (command.startsWith("mark ")) {
                        String taskNumberText = command.substring(5);

                        try {
                            int taskNumber = Integer.parseInt(taskNumberText);
                            int taskIndex = taskNumber - 1;

                            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                                throw new SageException(
                                        "I couldn't find task " + taskNumber
                                                + ". Please choose a task number from your list."
                                );
                            }

                            tasks.get(taskIndex).markAsDone();
                            storage.save(tasks);

                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println("  " + tasks.get(taskIndex));
                        } catch (NumberFormatException e) {
                            throw new SageException(
                                    "The task number should be a number, like `mark 2`."
                            );
                        }

                    } else if (command.startsWith("unmark ")) {
                        String taskNumberText = command.substring(7);

                        try {
                            int taskNumber = Integer.parseInt(taskNumberText);
                            int taskIndex = taskNumber - 1;

                            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                                throw new SageException(
                                        "I couldn't find task " + taskNumber
                                                + ". Please choose a task number from your list."
                                );
                            }
                            tasks.get(taskIndex).markAsNotDone();
                            storage.save(tasks);

                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println("  " + tasks.get(taskIndex));
                        } catch (NumberFormatException e) {
                            throw new SageException(
                                    "The task number should be a number, like `unmark 2`."
                            );
                        }

                    } else if (command.equals("deadline") || command.startsWith("deadline ")) {
                        String deadlineDetails = command.length() > 8
                                ? command.substring(9)
                                : "";

                        int byIndex = deadlineDetails.indexOf(" /by ");

                        if (byIndex == -1) {
                            throw new SageException(
                                    "Your deadline is missing a /by date or time. Could you try again?"
                            );
                        }

                        String description = deadlineDetails.substring(0, byIndex);

                        if (description.isBlank()) {
                            throw new SageException(
                                    "Your deadline needs a description. What would you like to add?"
                            );
                        }

                        String by = deadlineDetails.substring(byIndex + 5);

                        if (by.isBlank()) {
                            throw new SageException(
                                    "Your deadline needs a date or time after /by. Could you try again?"
                            );
                        }


                        LocalDate date = LocalDate.parse(by);


                        tasks.add(new Deadline(description, date));
                        storage.save(tasks);

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                    } else if (command.equals("todo") || command.startsWith("todo ")) {
                        String description = command.length() > 4
                                ? command.substring(5).trim()
                                : "";

                        if (description.isBlank()) {
                            throw new SageException(
                                    "Your todo needs a description. What would you like to add?"
                            );
                        }

                        tasks.add(new Todo(description));
                        storage.save(tasks);


                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));

                        if (tasks.size() == 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        }
                    }else if (command.equals("event") || command.startsWith("event ")) {
                        String eventDetails = command.length() > 5
                                ? command.substring(6)
                                : "";

                        int fromIndex = eventDetails.indexOf("/from ");
                        int toIndex = eventDetails.indexOf("/to ");

                        if (fromIndex == -1) {
                            throw new SageException(
                                    "Your event is missing a /from start time. Could you try again?"
                            );
                        }

                        if (toIndex == -1) {
                            throw new SageException(
                                    "Your event is missing a /to end time. Could you try again?"
                            );
                        }

                    String description = eventDetails.substring(0, fromIndex).trim();

                        if (description.isBlank()) {
                            throw new SageException(
                                    "Your event needs a description. What would you like to add?"
                            );
                        }


                        String from = eventDetails.substring(fromIndex + 6, toIndex).trim();
                        String to = eventDetails.substring(toIndex + 4).trim();


                        if (from.isBlank()) {
                            throw new SageException(
                                    "Your event needs a start time after /from. Could you try again?"
                            );
                        }

                        if (to.isBlank()) {
                            throw new SageException(
                                    "Your event needs an end time after /to. Could you try again?"
                            );
                        }

                        tasks.add(new Event(description, from, to));
                        storage.save(tasks);

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));

                        if (tasks.size()== 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        }

                    } else if (command.startsWith("delete ")) {
                        int taskNumber = Integer.parseInt(command.substring(7));
                        int taskIndex = taskNumber - 1;

                        if (taskIndex < 0 || taskIndex >= tasks.size()) {
                            throw new SageException(
                                    "I couldn't find task " + taskNumber
                                            + ". Please choose a task number from your list."
                            );
                        }


                        Task deletedTask = tasks.remove(taskIndex);
                        storage.save(tasks);

                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + deletedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        throw new SageException(
                                "I didn't quite catch that. You can add a todo, deadline, or event whenever you're ready. Could you try again?"
                        );
                    }

                } catch (SageException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println(divider);
            }
        }
    }
}
