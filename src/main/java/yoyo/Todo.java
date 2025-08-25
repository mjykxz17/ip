package yoyo;

public class Todo extends Task {

    public Todo(String description) {
        super(TaskType.TODO, description);
    }

    @Override
    public String toString() {
        return baseString();
    }

    @Override
    public String serialize() {
        return String.format("%c | %d | %s", type.code(), isDone() ? 1 : 0, description);
    }
}
