/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private final String description;
    private final String type;
    private final String by;
    private final String from;
    private final String to;
    private boolean isDone;

    /**
     * Creates a task that is initially not done.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.type = "T";
        this.by = null;
        this.from = null;
        this.to = null;
        this.isDone = false;
    }

    /**
     * Creates a deadline task that is initially not done.
     *
     * @param description the task description
     * @param by the deadline text supplied by the user
     */
    public Task(String description, String by) {
        this.description = description;
        this.type = "D";
        this.by = by;
        this.from = null;
        this.to = null;
        this.isDone = false;
    }

    /**
     * Creates an event task that is initially not done.
     *
     * @param description the task description
     * @param from the event start text supplied by the user
     * @param to the event end text supplied by the user
     */
    public Task(String description, String from, String to) {
        this.description = description;
        this.type = "E";
        this.by = null;
        this.from = from;
        this.to = to;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the icon representing this task's completion status.
     *
     * @return {@code "X"} when done, or a space when not done
     */
    public String getStatusIcon() {
        return isDone
                ? "X"
                : " ";
    }

    /**
     * Returns this task in the format displayed in Sage's task list.
     *
     * @return this task's type and status icons followed by its details
     */
    @Override
    public String toString() {
        String task = "[" + type + "][" + getStatusIcon() + "] " + description;
        if (by != null) {
            return task + " (by: " + by + ")";
        }
        if (from != null) {
            return task + " (from: " + from + " to: " + to + ")";
        }
        return task;
    }
}
