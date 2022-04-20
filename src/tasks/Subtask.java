package tasks.tasks;

public class Subtask extends Task {
    private final String name;
    private final String description;
    private Status status = Status.NEW;
    private final int epicID;
    private final Integer id;
    private final TypeOfTask typeOfTask = TypeOfTask.SUBTASK;

    public Subtask(String name, String description, Status status, int epicID, int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicID = epicID;
        this.id = id;
    }

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
    @Override
    public String forSaving(){
        return id + "," + typeOfTask + "," + name + "," + status + "," + description + "," + epicID + "\n";
    }
}