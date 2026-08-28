import java.io.IOException;

/** Command that marks a task as not done. */
public class UnmarkCommand extends Command {
    private final String input;

    /** Creates an unmark command from the raw command input. */
    public UnmarkCommand(String input) { this.input = input; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, SageException {
        int number;
        try { number = new Parser().parseTaskNumber(input); }
        catch (NumberFormatException e) {
            throw new SageException("The task number should be a number, like `unmark 2`.");
        }
        if (number < 1 || number > tasks.size()) {
            throw new SageException("I couldn't find task " + number
                    + ". Please choose a task number from your list.");
        }
        Task task = tasks.get(number - 1);
        task.markAsNotDone();
        storage.save(tasks);
        ui.show("OK, I've marked this task as not done yet:");
        ui.show("  " + task);
    }
}
