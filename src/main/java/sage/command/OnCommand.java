package sage.command;

import java.io.IOException;
import java.time.LocalDate;

import sage.exception.SageException;
import sage.model.Deadline;
import sage.model.Event;
import sage.model.Task;
import sage.model.TaskList;
import sage.parser.Parser;
import sage.storage.Storage;
import sage.ui.Ui;

/** Command that displays deadlines and events occurring on a date. */
public class OnCommand extends Command {
    private final String dateInput;

    /** Creates a date query command. */
    public OnCommand(String dateInput) {
        this.dateInput = dateInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, SageException {
        LocalDate date;
        try {
            date = new Parser().parseDate(dateInput);
        } catch (RuntimeException e) {
            throw new SageException("Please enter a date in the format d/M/yyyy, like `on 25/12/2025`.");
        }
        boolean found = false;
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (!deadline.getBy().toLocalDate().equals(date)) {
                    continue;
                }
                ui.show(task.toString());
                found = true;
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (isOnDate(event, date)) {
                    ui.show(task.toString());
                    found = true;
                }
            }
        }
        if (!found) {
            ui.show("There are no deadlines or events on " + date + ".");
        }
    }

    /** Returns whether an event includes the supplied date. */
    private boolean isOnDate(Event event, LocalDate date) {
        LocalDate startDate = event.getFrom().toLocalDate();
        LocalDate endDate = event.getTo().toLocalDate();
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
