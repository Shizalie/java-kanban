import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager{
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;
    protected int nextId = 1;

    private final ArrayList<Task> storageOfViewedTasks;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.storageOfViewedTasks = new ArrayList<>();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }
    @Override
    public ArrayList<Epic> getAllEpics() {
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
        if(storageOfViewedTasks.size() > 10){
            storageOfViewedTasks.remove(0);
        }
        else{
            storageOfViewedTasks.add(tasks.get(taskId));
        }

        return tasks.get(taskId);
    }
    @Override
    public SubTask getSubTaskById(int subTaskId) {
        if(storageOfViewedTasks.size() > 10){
            storageOfViewedTasks.remove(0);
        }
        else{
            storageOfViewedTasks.add(subTasks.get(subTaskId));
        }
        return subTasks.get(subTaskId);
    }
    @Override
    public Epic getEpicById(int epicId) {
        if(storageOfViewedTasks.size() > 10){
            storageOfViewedTasks.remove(0);
        }
        else{
            storageOfViewedTasks.add(epics.get(epicId));
        }
        return epics.get(epicId);
    }
    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
        task.setId(nextId++);
    }
    @Override
    public void createSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic.getId());
        }
        subTask.setId(nextId++);
    }
    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.setId(nextId++);
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
    }
    @Override
    public void removeSubTaskById(int subTaskId) {
        subTasks.remove(subTaskId);
    }
    @Override
    public void removeEpicById(int epicId) {
        epics.remove(epicId);
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
    public ArrayList<SubTask> getSubTasksForEpic(int epicId) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
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
            ArrayList<SubTask> epicSubTasks = getSubTasksForEpic(epicId);
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

    //5-ый спринт
    //=====================================================================
    @Override
    public ArrayList<Task> getHistory(){
        return storageOfViewedTasks;
    }

    @Override
    public void printAllTasks(TaskManager manager){
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubTasksForEpic(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
