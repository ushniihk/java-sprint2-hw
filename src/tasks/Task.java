package tasks.tasks;

public class Task {
    private String name;
    private String description;
    private Status status = Status.NEW;
    private Integer id;
    private final TypeOfTask typeOfTask = TypeOfTask.TASK;

    public Task() {
    }

    public Task(String name, String description, Status status, Integer id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public Status getStatus() {
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
        if (status == 1) this.status = Status.NEW;
        if (status == 2) this.status = Status.IN_PROGRESS;
        if (status == 3) this.status = Status.DONE;
    }

    public String forSaving (){
        return id + "," + typeOfTask + "," + name + "," + status + "," + description + "," + "\n";
    }
}
