import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;


public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        Task task1 = new Task("Buy groceries", "Go to the supermarket and buy the necessary items");
        Task task2 = new Task("Clean the house", "Dust, vacuum, and mop the floors");
        Task task3 = new Task("Cook breakfast", "Make omlete for 3 people");
        Task task4 = new Task("Education", "Go to school");
        Task task5 = new Task("Hangout", "Party with friends");
        Task task6 = new Task("Shopping", "Buy new clothes");

        Epic epic1 = new Epic("Plan a party", "Organize a big family celebration");
        SubTask subTask1 = new SubTask(epic1.getId(), "Send invitations", "Create and send invitations to family members");
        SubTask subTask2 = new SubTask(epic1.getId(), "Buy decorations", "Purchase decorations for the party");

        Epic epic2 = new Epic("Buy a new house", "Find and purchase a new house");
        SubTask subTask3 = new SubTask(epic2.getId(), "Research neighborhoods", "Explore different neighborhoods to find a suitable location");

        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createTask(task3);
        inMemoryTaskManager.createTask(task4);
        inMemoryTaskManager.createTask(task5);
        inMemoryTaskManager.createTask(task6);
        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createSubTask(subTask1);
        inMemoryTaskManager.createSubTask(subTask2);
        inMemoryTaskManager.createEpic(epic2);
        inMemoryTaskManager.createSubTask(subTask3);

        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(4);
        inMemoryTaskManager.getTaskById(6);
        inMemoryTaskManager.getTaskById(5);
        inMemoryTaskManager.getTaskById(3);
        inMemoryTaskManager.getSubTaskById(8);
        inMemoryTaskManager.getSubTaskById(11);
        inMemoryTaskManager.getSubTaskById(9);
        inMemoryTaskManager.getEpicById(10);
        inMemoryTaskManager.getEpicById(7);
        inMemoryTaskManager.getSubTaskById(8);
        inMemoryTaskManager.getSubTaskById(11);
        inMemoryTaskManager.getSubTaskById(9);


        for(Task task : inMemoryTaskManager.getHistory()){
            System.out.println(task);
        }

        // Print lists before status update
        System.out.println("Tasks before status update:");
        for(Task task : inMemoryTaskManager.getAllTasks()){
            System.out.println(task);
        }

        System.out.println("SubTasks before status update:");
        for(SubTask subTask : inMemoryTaskManager.getAllSubTasks()){
            System.out.println(subTask);
        }

        System.out.println("Epıcs before status update:");
        for(Epic epic : inMemoryTaskManager.getAllEpics()){
            System.out.println(epic);
        }

        // Update statuses
        task1.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateEpicStatus(epic1.getId());
        inMemoryTaskManager.removeEpicById(epic1.getId());

        // Print lists after status update
        System.out.println("\nTasks after status update:");
        for(Task task : inMemoryTaskManager.getAllTasks()){
            System.out.println(task);
        }

        System.out.println("SubTasks after status update:");
        for(SubTask subTask : inMemoryTaskManager.getAllSubTasks()){
            System.out.println(subTask);
        }

        System.out.println("Epıcs after status update:");
        for(Epic epic : inMemoryTaskManager.getAllEpics()){
            System.out.println(epic);
        }

        // Remove a task and an epic
        inMemoryTaskManager.removeTaskById(task2.getId());
        inMemoryTaskManager.removeEpicById(epic2.getId());

        // Print lists after removal
        System.out.println("\nTasks after removal:");
        for(Task task : inMemoryTaskManager.getAllTasks()){
            System.out.println(task);
        }

        System.out.println("SubTasks after removal:");
        for(SubTask subTask : inMemoryTaskManager.getAllSubTasks()){
            System.out.println(subTask);
        }

        System.out.println("Epıcs after removal:");
        for(Epic epic : inMemoryTaskManager.getAllEpics()){
            System.out.println(epic);
        }

    }
}
