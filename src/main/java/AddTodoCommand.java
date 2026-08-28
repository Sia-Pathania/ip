import java.io.IOException;

/** Command that creates and saves a Todo task. */
public class AddTodoCommand extends Command {
    private final String description;

    /** Creates a Todo command for the supplied description. */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, SageException {
        if (description.isBlank()) {
            throw new SageException(
                    "Your todo needs a description. What would you like to add?");
        }

        tasks.add(new Todo(description));
        storage.save(tasks);

        ui.show("Got it. I've added this task:");
        ui.show("  " + tasks.get(tasks.size() - 1));
        ui.show("Now you have " + tasks.size() + " task"
                + (tasks.size() == 1 ? "" : "s") + " in the list.");
    }
}
