
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;


    public Deadline(String description, String by) {
        super(description);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        this.by = LocalDateTime.parse(by, formatter);
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mma");



        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}
