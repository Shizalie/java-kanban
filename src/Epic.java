import java.util.ArrayList;

public class Epic extends Task{
    protected static ArrayList<Integer> subTaskIds;

    public Epic(String title, String description) {
        super(title, description);
        this.subTaskIds = new ArrayList<>();
    }

    public static ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public static void addSubTaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }
}
