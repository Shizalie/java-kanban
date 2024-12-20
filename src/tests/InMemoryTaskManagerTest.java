package tests;

import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    static private Epic epic;
    static private SubTask subTask;
    static private Task task;

    static private InMemoryTaskManager taskManager;

    static private HistoryManager historyManager;

    @BeforeAll
    static void setUpBeforeAll() {
        epic = new Epic("Plan a party", "Organize a big family celebration");
        task = new Task("Buy groceries", "Go to the supermarket and buy the necessary items");
        subTask = new SubTask(epic.getId(), "Send invitations", "Create and send invitations to family members");
    }

    @BeforeEach
    void setUpBeforeEach(){
        taskManager = new InMemoryTaskManager();
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testCreateTask() {
        taskManager.createTask(task);
        List<Task> allTasks = taskManager.getAllTasks();
        assertEquals(1, allTasks.size());
        assertEquals(task, allTasks.get(0));
    }

    @Test
    void testCreateSubTask() {
        taskManager.createEpic(epic);
        taskManager.updateEpicStatus(epic.getId());
        taskManager.createSubTask(subTask);
        List<SubTask> allSubTasks = taskManager.getAllSubTasks();
        assertEquals(1, allSubTasks.size());
        assertEquals(subTask, allSubTasks.get(0));
    }

    @Test
    void testCreateEpic() {
        taskManager.createEpic(epic);
        List<Epic> allEpics = taskManager.getAllEpics();
        assertEquals(1, allEpics.size());
        assertEquals(epic, allEpics.get(0));
    }

    @Test
    void testGetTaskById() {
        taskManager.createTask(task);
        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertEquals(task, retrievedTask);
    }

    @Test
    void testGetSubTaskById() {
        taskManager.createSubTask(subTask);
        SubTask retrievedSubTask = taskManager.getSubTaskById(subTask.getId());
        assertEquals(subTask, retrievedSubTask);
    }

    @Test
    void testGetEpicById() {
        taskManager.createEpic(epic);
        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        assertEquals(epic, retrievedEpic);
    }

    @Test
    void testRemoveTaskById() {
        taskManager.createTask(task);
        taskManager.removeTaskById(task.getId());
        List<Task> allTasks = taskManager.getAllTasks();
        assertTrue(allTasks.isEmpty());
    }

    @Test
    void testRemoveSubTaskById() {
        taskManager.createSubTask(subTask);
        taskManager.removeSubTaskById(subTask.getId());
        List<SubTask> allSubTasks = taskManager.getAllSubTasks();
        assertTrue(allSubTasks.isEmpty());
    }

    @Test
    void testRemoveEpicById() {
        taskManager.createEpic(epic);
        taskManager.removeEpicById(epic.getId());
        List<Epic> allEpics = taskManager.getAllEpics();
        assertTrue(allEpics.isEmpty());
    }

    @Test
    void testUpdateTask() {
        taskManager.createTask(task);
        task.setDescription("Updated description");
        taskManager.updateTask(task);
        Task updatedTask = taskManager.getTaskById(task.getId());
        assertEquals("Updated description", updatedTask.getDescription());
    }

    @Test
    void testUpdateSubTask() {
        taskManager.createSubTask(subTask);
        subTask.setDescription("Updated description");
        taskManager.updateSubTask(subTask);
        SubTask updatedSubTask = taskManager.getSubTaskById(subTask.getId());
        assertEquals("Updated description", updatedSubTask.getDescription());
    }

    @Test
    void testUpdateEpic() {
        taskManager.createEpic(epic);
        epic.setDescription("Updated description");
        taskManager.updateEpic(epic);
        Epic updatedEpic = taskManager.getEpicById(epic.getId());
        assertEquals("Updated description", updatedEpic.getDescription());
    }

    @Test
    void testAddTaskToHistory() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    void testAddExistingTaskToHistory() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        historyManager.add(task1);
        historyManager.add(task2);

        Task task1Updated = new Task("Task 1 Updated", "Description 1 Updated");
        task1Updated.setId(task1.getId());
        historyManager.add(task1Updated);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1Updated, history.get(1));
        assertEquals(task2, history.get(0));
    }

    @Test
    void testRemoveTaskFromHistory() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        historyManager.add(task1);
        historyManager.add(task2);

        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task2, history.get(0));
    }
}
