import java.util.ArrayList;

public class Epic extends Task{
    private final ArrayList<Integer> subTaskIds;

    public Epic(String title, String description) {
        super(title, description);
        this.subTaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }
}
