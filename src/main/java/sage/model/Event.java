package sage.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/** A task scheduled between a start date and an end date. */

/** Represents a task that takes place during a specified time range. */

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;


    /** Creates an event with its description and start and end times. */
    /** Creates an event task. */

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    /** Returns the date and time when this event starts. */

    /** Returns the event start date and time. */

    public LocalDateTime getFrom() {
        return from;
    }


    /** Returns the date and time when this event ends. */

    /** Returns the event end date and time. */

    public LocalDateTime getTo() {
        return to;
    }

    /** Returns this event in Sage's display format. */
    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mma");

        return "[E]" + super.toString()
                + " (from: " + from.format(formatter)
                + " to: " + to.format(formatter) + ")";
    }
}
