package tasks.tasks;

import java.util.HashMap;

public class Epic extends Task {
    private String name;
    private HashMap<Integer, Subtask> subTaskList = new HashMap<>();
    private Status status = Status.NEW;
    private Integer id;

    public void setSubTaskList(HashMap<Integer, Subtask> subTaskList) {
        this.subTaskList = subTaskList;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Epic(String name, Integer id) {
        this.name = name;
        this.id = id;
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
        for (Subtask subtask: subTaskList.values()) {
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
}
