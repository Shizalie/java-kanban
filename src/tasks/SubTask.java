package tasks;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(int epicId, String title, String description) {
        super(title, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
