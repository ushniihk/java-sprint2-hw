import java.util.HashMap;

public class Manager {
    private int counter = 0;
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();

    public int getCounter() {
        return counter;
    }

    public HashMap<Integer, Task> getTaskList() {
        return taskList;
    }

    public HashMap<Integer, Epic> getEpicList() {
        return epicList;
    }

    public HashMap<Integer, Task> returnTaskList() {
        return taskList;
    }

    public HashMap<Integer, Epic> returnEpicList() {
        return epicList;
    }

    public HashMap<Integer, Subtask> returnSubTaskList(int id) {
            return epicList.get(id).getSubTaskList();
        }


    public void addNewEpic(Epic epic) {
        epicList.put(counter++, epic);
    }

    public Epic createNewEpic(String name) {
        Epic epic = new Epic(name);
        return epic;
    }

    public void changeStatus(int id) {
        int counterStatus = 0;
        int sum = 0;
        for (int i = 0; i < epicList.get(id).getSubTaskList().size(); i++) {
            if (epicList.get(id).getSubTaskList().get(i).getStatus().equals("NEW")) sum++;
            if (epicList.get(id).getSubTaskList().get(i).getStatus().equals("IN_PROGRESS")) sum = sum + 2;
            if (epicList.get(id).getSubTaskList().get(i).getStatus().equals("DONE")) sum = sum + 3;
            counterStatus++;
        }
        if (sum == (counterStatus * 3)) {
            epicList.get(id).setStatus(3);
        } else if (sum > counterStatus) epicList.get(id).setStatus(2);

    }

    public Subtask createNewSubTask(String name, String description) {
        Subtask subtask = new Subtask(name, description);
        return subtask;
    }

    public void addNewTask(Task task) {
        taskList.put(counter++, task);
    }


    public Task createNewTask(String name, String description) {
        Task task = new Task(name, description);
        return task;
    }

    public void deleteAllTasks() {
        taskList.clear();
        epicList.clear();
    }

    public void deleteTask(int id) {
        taskList.remove(id);
        epicList.remove(id);
    }

    public void findTask(int id) {
        if (taskList.containsKey(id)) System.out.println(taskList.get(id));
        if (epicList.containsKey(id)) System.out.println(epicList.get(id));
    }

    public void updateTask(int id, int status) {
        if (taskList.containsKey(id)) {
            taskList.get(id).setStatus(status);
            System.out.println("готово");
        } else System.out.println("нет такой задачи");
    }
}
