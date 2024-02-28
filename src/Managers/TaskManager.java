package Managers;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    List<SubTask> getAllSubTasks();

    List<Epic> getAllEpics();

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

    List<SubTask> getSubTasksForEpic(int epicId);

    void updateEpicStatus(int epicId);

    List<Task> getHistory();


}


