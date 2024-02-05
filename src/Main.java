public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Buy groceries", "Go to the supermarket and buy the necessary items");
        Task task2 = new Task("Clean the house", "Dust, vacuum, and mop the floors");

        Epic epic1 = new Epic("Plan a party", "Organize a big family celebration");
        SubTask subTask1 = new SubTask(epic1.getId(), "Send invitations", "Create and send invitations to family members");
        SubTask subTask2 = new SubTask(epic1.getId(), "Buy decorations", "Purchase decorations for the party");

        Epic epic2 = new Epic("Buy a new house", "Find and purchase a new house");
        SubTask subTask3 = new SubTask(epic2.getId(), "Research neighborhoods", "Explore different neighborhoods to find a suitable location");

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(epic1);
        taskManager.createTask(subTask1);
        taskManager.createTask(subTask2);
        taskManager.createTask(epic2);
        taskManager.createTask(subTask3);

        // Print lists before status update
        System.out.println("Tasks before status update:");
        for(Task task : taskManager.getAllTasks()){
            System.out.println(task);
        }

        // Update statuses
        task1.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic1.getId());

        // Print lists after status update
        System.out.println("\nTasks after status update:");
        for(Task task : taskManager.getAllTasks()){
            System.out.println(task);
        }

        // Remove a task and an epic
        taskManager.removeTaskById(task2.getId());
        taskManager.removeTaskById(epic2.getId());

        // Print lists after removal
        System.out.println("\nTasks after removal:");
        for(Task task : taskManager.getAllTasks()){
            System.out.println(task);
        }
    }
}
