/**
 * Makes the command name in a user's input available to the application.
 */
public class Parser {
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
}
