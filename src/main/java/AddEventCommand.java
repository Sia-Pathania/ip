import java.io.IOException;
import java.time.LocalDateTime;

/** Command that creates and saves an Event task. */
public class AddEventCommand extends Command {
    private final String details;

    /** Creates an Event command for raw event details. */
    public AddEventCommand(String details) {
        this.details = details;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, SageException {
        Parser parser = new Parser();
        String[] parts = parser.parseEventDetails(details);
        LocalDateTime from = parser.parseDateTime(parts[1]);
        LocalDateTime to = parser.parseDateTime(parts[2]);
        tasks.add(new Event(parts[0], from, to));
        storage.save(tasks);
        ui.show("Got it. I've added this task:");
        ui.show("  " + tasks.get(tasks.size() - 1));
        ui.show("Now you have " + tasks.size() + " task"
                + (tasks.size() == 1 ? "" : "s") + " in the list.");
    }
}
