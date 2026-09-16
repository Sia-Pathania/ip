package sage.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import sage.command.AddDeadlineCommand;
import sage.command.AddEventCommand;
import sage.command.AddTodoCommand;
import sage.command.Command;
import sage.command.DeleteCommand;
import sage.command.DuplicatesCommand;
import sage.command.ExitCommand;
import sage.command.FindCommand;
import sage.command.ListCommand;
import sage.command.MarkCommand;
import sage.command.OnCommand;
import sage.command.UnmarkCommand;
import sage.exception.SageException;

/**
 * Makes the command name in a user's input available to the application.
 */
public class Parser {
    private static final String BY_COMMAND = "by";
    private static final String FROM_COMMAND = "from";
    private static final String TO_COMMAND = "to";

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);

    /** Creates the command object corresponding to the user's input. */
    public Command parseCommand(String command) {
        if (command == null) {
            return null;
        }
        String commandName = getCommandName(command);
        return switch (commandName) {
        case "bye" -> new ExitCommand();
        case "list" -> new ListCommand();
        case "find" -> new FindCommand(getArguments(command));
        case "todo" -> new AddTodoCommand(getTodoDescription(command));
        case "deadline" -> new AddDeadlineCommand(getDeadlineDetails(command));
        case "event" -> new AddEventCommand(getEventDetails(command));
        case "mark" -> new MarkCommand(command);
        case "unmark" -> new UnmarkCommand(command);
        case "delete" -> new DeleteCommand(command);
        case "duplicates" -> new DuplicatesCommand(getArguments(command));
        case "on" -> new OnCommand(getDateInput(command));
        default -> null;
        };
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
        String arguments = getArguments(command);
        if (!arguments.matches("\\d+")) {
            throw new NumberFormatException();
        }
        return Integer.parseInt(arguments);
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
    public LocalDateTime parseDateTime(String dateTime) throws SageException {
        try {
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new SageException("Please enter a valid date and time in the format d/M/yyyy HHmm.");
        }
    }

    /** Parses a date used by the {@code on} command. */
    public LocalDate parseDate(String date) throws SageException {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new SageException("Please enter a valid date in the format d/M/yyyy.");
        }
    }

    /**
     * Splits and validates Deadline input into description and due date.
     *
     * @throws SageException when a required field is missing
     */
    public String[] parseDeadlineDetails(String details) throws SageException {
        int byIndex = details.indexOf(" /" + BY_COMMAND + " ");
        if (byIndex == -1) {
            throw new SageException(
                    "Your deadline is missing a /by date or time. Could you try again?");
        }
        String description = details.substring(0, byIndex);
        if (description.isBlank()) {
            throw new SageException(
                    "Your deadline needs a description. What would you like to add?");
        }
        String by = details.substring(byIndex + BY_COMMAND.length() + 3);
        if (details.indexOf(" /" + BY_COMMAND + " ", byIndex + 1) != -1) {
            throw new SageException("A deadline can only have one /by date or time.");
        }
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
        int fromIndex = details.indexOf("/" + FROM_COMMAND + " ");
        int toIndex = details.indexOf("/" + TO_COMMAND + " ");
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
        String from = details.substring(fromIndex + FROM_COMMAND.length() + 2, toIndex).trim();
        String to = details.substring(toIndex + TO_COMMAND.length() + 2).trim();
        if (details.indexOf("/" + FROM_COMMAND + " ", fromIndex + 1) != -1
                || details.indexOf("/" + TO_COMMAND + " ", toIndex + 1) != -1) {
            throw new SageException("An event can only have one /from and one /to time.");
        }
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
