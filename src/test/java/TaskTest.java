import org.junit.jupiter.api.Test;
import sage.model.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void newTask_isNotDone() {
        Task task = new Task("read book");

        assertFalse(task.isDone());
        assertEquals("read book", task.getDescription());
        assertEquals("[ ] read book", task.toString());
    }
    @Test
    void markAsDone_changesStatusAndString() {
        Task task = new Task("read book");

        task.markAsDone();

        assertTrue(task.isDone());
        assertEquals("[X] read book", task.toString());
    }

    @Test
    void markAsNotDone_changesCompletedTaskBackToIncomplete() {
        Task task = new Task("read book");
        task.markAsDone();

        task.markAsNotDone();

        assertFalse(task.isDone());
        assertEquals("[ ] read book", task.toString());
    }
}