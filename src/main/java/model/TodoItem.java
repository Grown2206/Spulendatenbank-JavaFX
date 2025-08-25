package model;

public class TodoItem {
    private final int id;
    private final String task;
    private boolean isDone;

    public TodoItem(int id, String task, boolean isDone) {
        this.id = id;
        this.task = task;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
