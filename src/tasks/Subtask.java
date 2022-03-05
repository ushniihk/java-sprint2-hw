package tasks.tasks;

public class Subtask extends Task {
    private String name;
    private String description;
    private Status status = Status.NEW;
    private int epicID;
    private Integer id;

    public Subtask(String name, String description, int epicID, int id) {
        this.name = name;
        this.description = description;
        this.epicID = epicID;
        this.id = id;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Status getStatus() {
        return status;
    }


    public void setStatus(int status) {
        if (status == 1) this.status = Status.NEW;
        if (status == 2) this.status = Status.IN_PROGRESS;
        if (status == 3) this.status = Status.DONE;
    }

    @Override
    public String toString() {
        return "Подзадача {" +
                "Название ='" + name + '\'' +
                ", Описание ='" + description + '\'' +
                ", Статус ='" + status + '\'' +
                ", Номер Эпика ='" + epicID + '\'' +
                ", ID ='" + id + '\'' +
                '}';
    }
}