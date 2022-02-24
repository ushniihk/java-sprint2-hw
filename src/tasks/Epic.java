package tasks.tasks;

import java.util.HashMap;

public class Epic extends Task {
    private String name;
    private HashMap<Integer, Subtask> subTaskList = new HashMap<>();
    private String status;

    public void setSubTaskList(HashMap<Integer, Subtask> subTaskList) {
        this.subTaskList = subTaskList;
    }


    public Epic(String name) {
        this.name = name;
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
        for (int i = 0; i < subTaskList.size(); i++) {
            if (subTaskList.get(i).getStatus().equals("NEW")) sum++;
            if (subTaskList.get(i).getStatus().equals("IN_PROGRESS")) sum = sum + 2;
            if (subTaskList.get(i).getStatus().equals("DONE")) sum = sum + 3;
            counterStatus++;
        }
        if (sum == (counterStatus * 3)) {
            status = "DONE";
        } else if (sum > counterStatus) {
            status = "IN_PROGRESS";
        }
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "имя ='" + name + '\'' +
                ", Список подзадач =" + subTaskList +
                ", Статус = " + status +
                '}';
    }
}
