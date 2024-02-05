import java.util.ArrayList;
import java.util.Hashtable;

public class TaskManager {

    private final Hashtable<Integer, Task> tasks;

    public TaskManager() {
        this.tasks = new Hashtable<>();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
    }

    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public ArrayList<SubTask> getSubTasksForEpic(int epicId) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task instanceof SubTask && ((SubTask) task).getEpicId() == epicId) {
                subTasks.add((SubTask) task);
            }
        }
        return subTasks;
    }

    public void updateEpicStatus(int epicId) {
        Epic epic = (Epic) tasks.get(epicId);
        if (epic != null) {
            ArrayList<SubTask> subTasks = getSubTasksForEpic(epicId);
            boolean allSubTasksDone = true;

            for (SubTask subTask : subTasks) {
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
        }
    }
}
