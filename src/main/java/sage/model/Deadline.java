package sage.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a task that must be completed by a specified date and time. */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mma");

    private final LocalDateTime by;

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
        return "[D]" + super.toString()
                + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }
}
