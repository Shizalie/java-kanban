public abstract class Managers implements TaskManager {
    TaskManager taskManager;

    public TaskManager getDefault(){
        return taskManager;
    }
}
