package sage.command;

import java.io.IOException;

import sage.exception.SageException;
import sage.model.TaskList;
import sage.model.Todo;
import sage.storage.Storage;
import sage.ui.Ui;

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
                    "I need a description for this task. Let's try adding one.");
        }

        Todo todo = new Todo(description);
        if (tasks.containsEquivalent(todo)) {
            throw new SageException("This task is already in your list. Nothing more is needed.");
        }
        tasks.add(todo);
        storage.save(tasks);

        ui.show("Got it. I've added this task:");
        ui.show("  " + tasks.get(tasks.size() - 1));
        ui.show("Now you have " + tasks.size() + " task"
                + (tasks.size() == 1 ? "" : "s") + " in the list.");
    }
}
