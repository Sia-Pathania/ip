package sage.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** A task that must be completed by a specified date and time. */
public class Deadline extends Task {

    protected LocalDateTime by;

    /** Creates a deadline with its description and due date. */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /** Returns the date and time by which this task should be completed. */
    public LocalDateTime getBy() {
        return by;
    }

    /** Returns this deadline in Sage's display format. */
    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mma");

        return "[D]" + super.toString()
                + " (by: " + by.format(formatter) + ")";
    }
}
