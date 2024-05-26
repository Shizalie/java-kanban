package managers;

import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;


public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    private final Path filePath;

    public FileBackedTaskManager(String filePath) {
        super();
        this.filePath = Paths.get(filePath);
        loadFromFile();
    }

    private void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("id,type,title,status,description,epic");
            writer.newLine();
            for (Task task : getAllTasks()) {
                writer.write(taskToString(task));
                writer.newLine();
            }
            for (Epic epic : getAllEpics()) {
                writer.write(epicToString(epic));
                writer.newLine();
                for (SubTask subTask : getSubTasksForEpic(epic.getId())) {
                    writer.write(subTaskToString(subTask));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving tasks to file", e);
        }
    }

    private void loadFromFile() {
        if (!Files.exists(filePath)) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String header = reader.readLine(); // Чтение заголовка
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = stringToTask(line);
                if (task instanceof Epic) {
                    super.createEpic((Epic) task);
                } else if (task instanceof SubTask) {
                    super.createSubTask((SubTask) task);
                } else {
                    super.createTask(task);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading tasks from file", e);
        }
    }

    private String taskToString(Task task) {
        return String.format("%d,TASK,%s,%s,%s,",
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getDescription());
    }

    private String epicToString(Epic epic) {
        return String.format("%d,EPIC,%s,%s,%s,",
                epic.getId(),
                epic.getTitle(),
                epic.getStatus(),
                epic.getDescription());
    }

    private String subTaskToString(SubTask subTask) {
        return String.format("%d,SUBTASK,%s,%s,%s,%d",
                subTask.getId(),
                subTask.getTitle(),
                subTask.getStatus(),
                subTask.getDescription(),
                subTask.getEpicId());
    }

    private Task stringToTask(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        String type = fields[1];
        String title = fields[2];
        Status status = Status.valueOf(fields[3]);
        String description = fields[4];

        switch (type) {
            case "TASK":
                Task task = new Task(title, description);
                task.setId(id);
                task.setStatus(status);
                return task;
            case "EPIC":
                Epic epic = new Epic(title, description);
                epic.setId(id);
                epic.setStatus(status);
                return epic;
            case "SUBTASK":
                int epicId = Integer.parseInt(fields[5]);
                SubTask subTask = new SubTask(epicId, title, description);
                subTask.setId(id);
                subTask.setStatus(status);
                return subTask;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask updatedSubTask) {
        super.updateSubTask(updatedSubTask);
        save();
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        super.updateEpic(updatedEpic);
        save();
    }

    @Override
    public void removeTaskById(int taskId) {
        super.removeTaskById(taskId);
        save();
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        super.removeSubTaskById(subTaskId);
        save();
    }

    @Override
    public void removeEpicById(int epicId) {
        super.removeEpicById(epicId);
        save();
    }

    public static void main(String[] args) {
        try {
            File tempFile = File.createTempFile("task_manager", ".csv");
            tempFile.deleteOnExit();

            FileBackedTaskManager taskManager = new FileBackedTaskManager(tempFile.getAbsolutePath());

            // Создаем задачи
            Task task1 = new Task("Buy groceries", "Go to the supermarket and buy the necessary items");
            Task task2 = new Task("Clean the house", "Dust, vacuum, and mop the floors");
            Task task3 = new Task("Cook breakfast", "Make omlete for 3 people");

            Epic epic1 = new Epic("Plan a party", "Organize a big family celebration");
            SubTask subTask1 = new SubTask(epic1.getId(), "Send invitations", "Create and send invitations to family members");
            SubTask subTask2 = new SubTask(epic1.getId(), "Buy decorations", "Purchase decorations for the party");

            Epic epic2 = new Epic("Buy a new house", "Find and purchase a new house");
            SubTask subTask3 = new SubTask(epic2.getId(), "Research neighborhoods", "Explore different neighborhoods to find a suitable location");

            // Добавляем задачи в менеджер
            taskManager.createTask(task1);
            taskManager.createTask(task2);
            taskManager.createTask(task3);
            taskManager.createEpic(epic1);
            taskManager.createSubTask(subTask1);
            taskManager.createSubTask(subTask2);
            taskManager.createEpic(epic2);
            taskManager.createSubTask(subTask3);

            // Изменяем статус задач
            task1.setStatus(Status.IN_PROGRESS);
            subTask1.setStatus(Status.DONE);
            subTask2.setStatus(Status.DONE);
            taskManager.updateEpicStatus(epic1.getId());

            // Проверяем сохранение и загрузку
            FileBackedTaskManager loadedTaskManager = new FileBackedTaskManager(tempFile.getAbsolutePath());

            // Проверка, что все задачи, эпики и подзадачи из старого менеджера есть в новом
            System.out.println("Tasks after loading from file:");
            for (Task task : loadedTaskManager.getAllTasks()) {
                System.out.println(task);
            }

            System.out.println("SubTasks after loading from file:");
            for (SubTask subTask : loadedTaskManager.getAllSubTasks()) {
                System.out.println(subTask);
            }

            System.out.println("Epics after loading from file:");
            for (Epic epic : loadedTaskManager.getAllEpics()) {
                System.out.println(epic);
            }

            // Проверка удаления задач и эпиков
            loadedTaskManager.removeTaskById(task2.getId());
            loadedTaskManager.removeEpicById(epic1.getId());

            System.out.println("\nTasks after removal:");
            for (Task task : loadedTaskManager.getAllTasks()) {
                System.out.println(task);
            }

            System.out.println("SubTasks after removal:");
            for (SubTask subTask : loadedTaskManager.getAllSubTasks()) {
                System.out.println(subTask);
            }

            System.out.println("Epics after removal:");
            for (Epic epic : loadedTaskManager.getAllEpics()) {
                System.out.println(epic);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
