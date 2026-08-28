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
}
