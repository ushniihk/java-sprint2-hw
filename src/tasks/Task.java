package tasks.tasks;

public class Task {
    private String name;
    private String description;
    private String status = "NEW";
    private Integer id;

    public Task() {
    }

    public Integer getId() {
        return id;
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
                ", ID='" + id + '\'' +
                '}';
    }

    public Task(String name, String description, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }


    public void setStatus(int status) {
        if (status == 1) this.status = "NEW";
        if (status == 2) this.status = "IN_PROGRESS";
        if (status == 3) this.status = "DONE";
    }
}
