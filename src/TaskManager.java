import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;
    protected static int nextId = 1;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubTasks() {
        subTasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public SubTask getSubTaskById(int subTaskId) {
        return subTasks.get(subTaskId);
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void createSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
    }

    public void updateSubTask(SubTask updatedSubTask) {
        subTasks.put(updatedSubTask.getId(), updatedSubTask);
    }

    public void updateEpic(Epic updatedEpic) {
        epics.put(updatedEpic.getId(), updatedEpic);
    }

    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public void removeSubTaskById(int subTaskId) {
        subTasks.remove(subTaskId);
    }

    public void removeEpicById(int epicId) {
        epics.remove(epicId);
    }

    public ArrayList<SubTask> getSubTasksForEpic(int epicId) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId() == epicId) {
                epicSubTasks.add(subTask);
            }
        }
        return epicSubTasks;
    }

    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            ArrayList<SubTask> epicSubTasks = getSubTasksForEpic(epicId);
            boolean allSubTasksDone = true;

            for (SubTask subTask : epicSubTasks) {
                if (subTask.getStatus() != Status.DONE) {
                    allSubTasksDone = false;
                    break;
                }
            }

            if (allSubTasksDone) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }

            updateEpic(epic);
        }
    }
}
