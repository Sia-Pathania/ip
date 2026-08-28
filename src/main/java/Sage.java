import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        Ui ui = new Ui();
        Parser parser = new Parser();
        ui.showWelcome();
        Storage storage = new Storage("data/sage.txt");
        TaskList tasks = new TaskList(storage.load());

        try (ui) {
            String command;
            while ((command = ui.readCommand()) != null) {
                ui.showDivider();
                String commandName = parser.getCommandName(command);

                try {
                    if (commandName.equals("bye")) {
                        System.out.println("Bye. Hope to see you again soon!");
                        ui.showDivider();
                        break;

                    } else if (commandName.equals("list")) {
                        System.out.println("Here are the tasks in your list:");
                        for (int index = 0; index < tasks.size(); index++) {
                            System.out.println((index + 1) + "." + tasks.get(index));
                        }

                    } else if (commandName.equals("mark")) {
                        try {
                            int taskNumber = parser.parseTaskNumber(command);
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

                    } else if (commandName.equals("unmark")) {
                        try {
                            int taskNumber = parser.parseTaskNumber(command);
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

                    } else if (commandName.equals("deadline")) {
                        String deadlineDetails = parser.getDeadlineDetails(command);

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

                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

                        LocalDateTime dateTime = LocalDateTime.parse(by, formatter);

                        tasks.add(new Deadline(description, dateTime));
                        storage.save(tasks);


                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));

                        if (tasks.size()== 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        }

                    } else if (commandName.equals("todo")) {
                        String description = parser.getTodoDescription(command);

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

                    }else if (commandName.equals("event")) {
                        String eventDetails = parser.getEventDetails(command);

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

                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

                        LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
                        LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);


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

                        tasks.add(new Event(description, fromDateTime, toDateTime));
                        storage.save(tasks);

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));

                        if (tasks.size()== 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        }

                    } else if (commandName.equals("delete")) {
                        int taskNumber = parser.parseTaskNumber(command);
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
                    } else if (commandName.equals("on")) {
                        String dateInput = parser.getDateInput(command);

                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern("d/M/yyyy");

                        LocalDate date = LocalDate.parse(dateInput, formatter);

                        boolean found = false;

                        for (Task task : tasks) {
                            if (task instanceof Deadline) {
                                Deadline deadline = (Deadline) task;

                                if (deadline.getBy().toLocalDate().equals(date)) {
                                    System.out.println(deadline);
                                    found = true;
                                }

                            } else if (task instanceof Event) {
                                Event event = (Event) task;

                                LocalDate from = event.getFrom().toLocalDate();
                                LocalDate to = event.getTo().toLocalDate();

                                if (!date.isBefore(from) && !date.isAfter(to)) {
                                    System.out.println(event);
                                    found = true;
                                }
                            }
                        }

                        if (!found) {
                            System.out.println("There are no deadlines or events on " + date + ".");
                        }
                    } else {
                        throw new SageException(
                                "I didn't quite catch that. You can add a todo, deadline, or event whenever you're ready. Could you try again?"
                        );
                    }

                } catch (SageException e) {
                    System.out.println(e.getMessage());
                }

                ui.showDivider();
            }
        }
    }
}
