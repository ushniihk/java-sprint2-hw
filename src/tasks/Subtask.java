package tasks.tasks;

public class Subtask extends Task {
    private String name = "";
    private String description = "";
    private String status = "NEW";

    public Subtask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (status == 1) this.status = "NEW";
        if (status == 2) this.status = "IN_PROGRESS";
        if (status == 3) this.status = "DONE";
    }

    @Override
    public String toString() {
        return "Подзадача {" +
                "Название ='" + name + '\'' +
                ", Описание ='" + description + '\'' +
                ", Статус ='" + status + '\'' +
                '}';
    }
}