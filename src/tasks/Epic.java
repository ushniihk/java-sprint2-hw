package tasks.tasks;

import java.util.HashMap;

public class Epic extends Task {
    private final String name;
    private final String description;
    private HashMap<Integer, Subtask> subTaskList = new HashMap<>();
    private Status status = Status.NEW;
    private final Integer id;
    private final TypeOfTask typeOfTask = TypeOfTask.EPIC;

    public void setSubTaskList(HashMap<Integer, Subtask> subTaskList) {
        this.subTaskList = subTaskList;
    }
    public void putSubtaskList(Subtask task) {
        subTaskList.put(task.getId(), task);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Epic(String name, Integer id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public Epic(String name, String description, Status status, int id) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.status = status;

    }


    public HashMap<Integer, Subtask> getSubTaskList() {
        return subTaskList;
    }

    @Override
    public void setStatus(int status) {
    }

    public void setStatus() {
        int counterStatus = 0;
        int sum = 0;
        for (Subtask subtask : subTaskList.values()) {
            if (subtask.getStatus() == Status.NEW) sum++;
            if (subtask.getStatus() == Status.IN_PROGRESS) sum = sum + 2;
            if (subtask.getStatus() == Status.DONE) sum = sum + 3;
            counterStatus++;
        }
        if (sum == (counterStatus * 3)) {
            status = Status.DONE;
        } else if (sum > counterStatus) {
            status = Status.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "имя ='" + name + '\'' +
                ", Статус = " + status +
                ", ID = " + id +
                '}';
    }
    public String forSaving (){
        return id + "," + typeOfTask + "," + name + "," + status + "," + description + "," + "\n";
    }
}

