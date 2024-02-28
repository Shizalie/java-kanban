package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> storageOfViewedTasks;

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
    public List<Task> getHistory() {
        return storageOfViewedTasks;
    }
}
