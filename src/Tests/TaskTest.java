package Tests;

import Tasks.Status;
import Tasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void testEqualsAndHashCode() {
        Task task1 = new Task("Title 1", "Description 1");
        Task task2 = new Task("Title 2", "Description 2");
        Task task3 = new Task("Title 1", "Description 1");

        assertNotEquals(task1, task2);
        assertEquals(task1, task3);
        assertNotEquals(task2, task3);

        assertNotEquals(task1.hashCode(), task2.hashCode());
        assertEquals(task1.hashCode(), task3.hashCode());
        assertNotEquals(task2.hashCode(), task3.hashCode());
    }

    @Test
    void testToString() {
        Task task = new Task("Title", "Description");
        assertEquals("Tasks.Task ID: 0, Title: Title, Description: Description, Tasks.Status: NEW", task.toString());
    }

    @Test
    void testGettersAndSetters() {
        Task task = new Task("Title", "Description");
        assertEquals("Title", task.getTitle());
        assertEquals("Description", task.getDescription());
        assertEquals(Status.NEW, task.getStatus());

        task.setTitle("New Title");
        task.setDescription("New Description");
        task.setStatus(Status.IN_PROGRESS);

        assertEquals("New Title", task.getTitle());
        assertEquals("New Description", task.getDescription());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

}
