package mvc.model;

public class Todo {
    private int id;
    private String name;
    private String description;
    private boolean isDone;

    public Todo() {
        isDone = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String isDone() {
        return isDone ? "Finished" : "In progress";
    }

    public void changeStatus() {
        isDone = !isDone;
    }
}
