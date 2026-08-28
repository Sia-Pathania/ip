import java.util.ArrayList;
import java.util.Collection;

/**
 * Owns the collection of tasks used by Sage.
 *
 * <p>This class currently specializes {@link ArrayList} so existing task
 * operations remain simple while the collection gains a clear responsibility
 * boundary for future task-list behavior.</p>
 */
public class TaskList extends ArrayList<Task> {
    /** Creates an empty task list. */
    public TaskList() {
        super();
    }

    /**
     * Creates a task list containing the tasks loaded from storage.
     *
     * @param tasks tasks to copy into this list
     */
    public TaskList(Collection<Task> tasks) {
        super(tasks);
    }
}
