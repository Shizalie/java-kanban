import java.util.ArrayList;

public class Managers {

    TaskManager getDefault() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    static HistoryManager getDefaultHistory(){
        HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}

