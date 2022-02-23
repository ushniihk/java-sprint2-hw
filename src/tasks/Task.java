package tasks.tasks;

public class Task {
    private String name = "";
    private String description = "";
    private String status = "NEW";

    public Task() {
    }


    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Задача {" +
                "Название='" + name + '\'' +
                ", Описание='" + description + '\'' +
                ", Статус='" + status + '\'' +
                '}';
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public void setStatus(int status) {
        if (status == 1) this.status = "NEW";
        if (status == 2) this.status = "IN_PROGRESS";
        if (status == 3) this.status = "DONE";
    }
}
