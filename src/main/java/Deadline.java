import java.time.LocalDate;
import java.time.format.DateTimeFormatter;




public class Deadline extends Task {

    protected String by;
    protected LocalDate date;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.date = LocalDate.parse(by);
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM d yyyy");
        return "[D]" + super.toString() + " (by: " + date.format(formatter) + ")";
    }
}
