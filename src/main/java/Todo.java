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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }
}