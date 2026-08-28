import org.junit.jupiter.api.Test;
import sage.model.Task;


import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    void markAsDone_changesStatus() {
        Task task = new Task("read book");

        task.markAsDone();

        assertTrue(task.isDone());
    }
}