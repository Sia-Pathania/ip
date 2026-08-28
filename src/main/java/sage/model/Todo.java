package sage.model;

/** Represents a task without a deadline or event time range. */
public class Todo extends Task {
    /** Creates a todo task. */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
