package sage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;


import sage.command.Command;
import sage.exception.SageException;
import sage.model.Deadline;
import sage.model.Event;
import sage.model.Task;
import sage.model.TaskList;
import sage.parser.Parser;
import sage.storage.Storage;
import sage.ui.Ui;

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
        TaskList tasks = storage.load();

        try (ui) {
            String command;
            while ((command = ui.readCommand()) != null) {
                ui.showDivider();
                String commandName = parser.getCommandName(command);

                try {
                    Command parsedCommand = parser.parseCommand(command);
                    if (parsedCommand != null) {
                        parsedCommand.execute(tasks, ui, storage);
                        if (parsedCommand.isExit()) {
                            ui.showDivider();
                            break;
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

                        String[] deadlineParts = parser.parseDeadlineDetails(deadlineDetails);
                        String description = deadlineParts[0];
                        String by = deadlineParts[1];

                        LocalDateTime dateTime = parser.parseDateTime(by);

                        tasks.add(new Deadline(description, dateTime));
                        storage.save(tasks);


                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));

                        if (tasks.size() == 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        }

                    } else if (commandName.equals("event")) {
                        String eventDetails = parser.getEventDetails(command);

                        String[] eventParts = parser.parseEventDetails(eventDetails);
                        String description = eventParts[0];
                        String from = eventParts[1];
                        String to = eventParts[2];

                        LocalDateTime fromDateTime = parser.parseDateTime(from);
                        LocalDateTime toDateTime = parser.parseDateTime(to);


                        tasks.add(new Event(description, fromDateTime, toDateTime));
                        storage.save(tasks);

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));

                        if (tasks.size() == 1) {
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

                        LocalDate date = parser.parseDate(dateInput);

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
