package sage.command;

import java.io.IOException;

import sage.exception.SageException;
import sage.model.TaskList;
import sage.storage.Storage;
import sage.ui.Ui;

/** Represents one executable user command. */
public abstract class Command {
    /** Executes this command using the application's collaborators. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws IOException, SageException;

    /** Returns whether executing this command should end the application. */
    public boolean isExit() {
        return false;
    }
}
