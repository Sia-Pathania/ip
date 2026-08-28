package sage.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import sage.command.AddDeadlineCommand;
import sage.command.AddEventCommand;
import sage.command.AddTodoCommand;
import sage.command.Command;
import sage.command.DeleteCommand;
import sage.command.ExitCommand;
import sage.command.ListCommand;
import sage.command.MarkCommand;
import sage.command.UnmarkCommand;
import sage.exception.SageException;

/**
 * Makes the command name in a user's input available to the application.
 */
public class Parser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy");

    /** Creates the command object corresponding to the user's input. */
    public Command parseCommand(String command) {
        String commandName = getCommandName(command);
        if (commandName.equals("bye")) {
            return new ExitCommand();
        }
        if (commandName.equals("list")) {
            return new ListCommand();
        }
        if (commandName.equals("todo")) {
            return new AddTodoCommand(getTodoDescription(command));
        }
        if (commandName.equals("deadline")) {
            return new AddDeadlineCommand(getDeadlineDetails(command));
        }
        if (commandName.equals("event")) {
            return new AddEventCommand(getEventDetails(command));
        }
        if (commandName.equals("mark")) {
            return new MarkCommand(command);
        }
        if (commandName.equals("unmark")) {
            return new UnmarkCommand(command);
        }
        if (commandName.equals("delete")) {
            return new DeleteCommand(command);
        }
        return null;
    }

    /**
     * Returns the first whitespace-delimited word in a command.
     *
     * @param command complete user input
     * @return command name, or an empty string for blank input
     */
    public String getCommandName(String command) {
        String trimmedCommand = command.trim();
        int firstSpace = trimmedCommand.indexOf(' ');
        return firstSpace == -1 ? trimmedCommand : trimmedCommand.substring(0, firstSpace);
    }

    /**
     * Returns the text after the command name.
     *
     * @param command complete user input
     * @return command arguments, or an empty string when none were supplied
     */
    public String getArguments(String command) {
        String trimmedCommand = command.trim();
        int firstSpace = trimmedCommand.indexOf(' ');
        return firstSpace == -1 ? "" : trimmedCommand.substring(firstSpace + 1).trim();
    }

    /**
     * Converts the argument of a task-number command into a list number.
     *
     * @param command command containing a numeric task argument
     * @return the requested one-based task number
     * @throws NumberFormatException when the argument is not numeric
     */
    public int parseTaskNumber(String command) {
        return Integer.parseInt(getArguments(command));
    }

    /** Returns the Todo description supplied by the user. */
    public String getTodoDescription(String command) {
        return getArguments(command);
    }

    /** Returns the raw Deadline details supplied by the user. */
    public String getDeadlineDetails(String command) {
        return getArguments(command);
    }

    /** Returns the raw Event details supplied by the user. */
    public String getEventDetails(String command) {
        return getArguments(command);
    }

    /** Returns the date supplied to the {@code on} command. */
    public String getDateInput(String command) {
        return getArguments(command);
    }

    /** Parses a deadline or event date and time. */
    public LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    /** Parses a date used by the {@code on} command. */
    public LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * Splits and validates Deadline input into description and due date.
     *
     * @throws SageException when a required field is missing
     */
    public String[] parseDeadlineDetails(String details) throws SageException {
        int byIndex = details.indexOf(" /by ");
        if (byIndex == -1) {
            throw new SageException(
                    "Your deadline is missing a /by date or time. Could you try again?");
        }
        String description = details.substring(0, byIndex);
        if (description.isBlank()) {
            throw new SageException(
                    "Your deadline needs a description. What would you like to add?");
        }
        String by = details.substring(byIndex + 5);
        if (by.isBlank()) {
            throw new SageException(
                    "Your deadline needs a date or time after /by. Could you try again?");
        }
        return new String[] {description, by};
    }

    /**
     * Splits and validates Event input into description, start, and end values.
     *
     * @throws SageException when a required field is missing
     */
    public String[] parseEventDetails(String details) throws SageException {
        int fromIndex = details.indexOf("/from ");
        int toIndex = details.indexOf("/to ");
        if (fromIndex == -1) {
            throw new SageException(
                    "Your event is missing a /from start time. Could you try again?");
        }
        if (toIndex == -1) {
            throw new SageException(
                    "Your event is missing a /to end time. Could you try again?");
        }
        String description = details.substring(0, fromIndex).trim();
        if (description.isBlank()) {
            throw new SageException(
                    "Your event needs a description. What would you like to add?");
        }
        String from = details.substring(fromIndex + 6, toIndex).trim();
        String to = details.substring(toIndex + 4).trim();
        if (from.isBlank()) {
            throw new SageException(
                    "Your event needs a start time after /from. Could you try again?");
        }
        if (to.isBlank()) {
            throw new SageException(
                    "Your event needs an end time after /to. Could you try again?");
        }
        return new String[] {description, from, to};
    }
}
