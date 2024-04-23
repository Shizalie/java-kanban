package managers;

import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;

    HistoryManager historyManager;

    protected int nextId = 1;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyManager =  Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubTasks() {
        subTasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }
    @Override
    public SubTask getSubTaskById(int subTaskId) {
        historyManager.add(subTasks.get(subTaskId));
        return subTasks.get(subTaskId);
    }
    @Override
    public Epic getEpicById(int epicId) {
        historyManager.add(epics.get(epicId));
        return epics.get(epicId);
    }

    @Override
    public void createTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        subTask.setId(nextId++);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
    }

    @Override
    public void updateSubTask(SubTask updatedSubTask) {
        subTasks.put(updatedSubTask.getId(), updatedSubTask);
        Epic epic = epics.get(updatedSubTask.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        epics.put(updatedEpic.getId(), updatedEpic);
    }

    @Override
    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        subTasks.remove(subTaskId);
        historyManager.remove(subTaskId);
    }

    @Override
    public void removeEpicById(int epicId) {
        epics.remove(epicId);
        historyManager.remove(epicId);
        ArrayList<SubTask> tasksToRemove = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId() == epicId) {
                tasksToRemove.add(subTask);
            }
        }
        for (SubTask subTask : tasksToRemove) {
            subTasks.remove(subTask.getId());
        }
    }

    @Override
    public List<SubTask> getSubTasksForEpic(int epicId) {
        List<SubTask> epicSubTasks = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId() == epicId) {
                epicSubTasks.add(subTask);
            }
        }
        return epicSubTasks;
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            List<SubTask> epicSubTasks = getSubTasksForEpic(epicId);
            boolean hasNewSubTasks = false;
            for (SubTask subTask : epicSubTasks) {
                if (subTask.getStatus() == Status.NEW) {
                    hasNewSubTasks = true;
                    break;
                }
            }
            if (epicSubTasks.isEmpty() || hasNewSubTasks) {
                epic.setStatus(Status.NEW);
            } else {
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
            }
        }
    }

    @Override
    public List<Task> getHistory(){
       return historyManager.getHistory();
    }
}
