/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private final String description;
    private final String type;
    private final String by;
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
        return by == null ? task : task + " (by: " + by + ")";
    }
}
