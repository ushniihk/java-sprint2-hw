import java.util.HashMap;

public class Epic extends Task {
    private String name = "";
    private HashMap<Integer, Subtask> subTaskList = new HashMap<>();
    String status = "NEW";


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
    public String toString() {
        return "Эпик{" +
                "имя ='" + name + '\'' +
                ", Список подзадач =" + subTaskList +
                ", Статус = " + status +
                '}';
    }
}
