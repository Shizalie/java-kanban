import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> storageOfViewedTasks;

    public InMemoryHistoryManager() {
        this.storageOfViewedTasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if(storageOfViewedTasks.size() > 9){
            storageOfViewedTasks.remove(0);
        }
        storageOfViewedTasks.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return storageOfViewedTasks;
    }
}
