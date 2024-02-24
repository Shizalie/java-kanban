import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    ArrayList<SubTask> getAllSubTasks();

    ArrayList<Epic> getAllEpics();

    void removeAllTasks();

    void removeAllSubTasks();

    void removeAllEpics();

    Task getTaskById(int taskId);

    SubTask getSubTaskById(int subTaskId);

    Epic getEpicById(int epicId);

    void createTask(Task task);

    void createSubTask(SubTask subTask);

    void createEpic(Epic epic);

    void updateTask(Task updatedTask);

    void updateSubTask(SubTask updatedSubTask);

    void updateEpic(Epic updatedEpic);

    void removeTaskById(int taskId);

    void removeSubTaskById(int subTaskId);

    void removeEpicById(int epicId);

    ArrayList<SubTask> getSubTasksForEpic(int epicId);

    void updateEpicStatus(int epicId);



    //5-ый спринт
    //=====================================================================
    ArrayList<Task>  getHistory();

    void printAllTasks(TaskManager manager);
}
