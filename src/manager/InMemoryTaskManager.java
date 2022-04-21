package tasks.manager;

import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    private int counter = 0;
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();

    public void setTaskList(HashMap<Integer, Task> taskList) {
        this.taskList = taskList;
    }

    public void setEpicList(HashMap<Integer, Epic> epicList) {
        this.epicList = epicList;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public Map<Integer, Task> getTaskList() {
        return taskList;
    }

    @Override
    public Map<Integer, Epic> getEpicList() {
        return epicList;
    }

    @Override
    public Map<Integer, Task> returnTaskList() {
        return taskList;
    }

    @Override
    public Map<Integer, Epic> returnEpicList() {
        return epicList;
    }

    @Override
    public HashMap<Integer, Subtask> returnSubTaskList(int id) {
        return epicList.get(id).getSubTaskList();
    }

    @Override
    public void addNewEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
    }

    @Override
    public Epic createNewEpic(String name, String description) {
        return new Epic(name, counter++, description);
    }

    @Override
    public void changeStatus(Integer id) {
        epicList.get(id).setStatus();
    }

    @Override
    public Subtask createNewSubTask(String name, String description, int epicID) {
        return new Subtask(name, description, epicID, counter++);
    }

    @Override
    public void addNewTask(Task task) {
        taskList.put(task.getId(), task);
    }

    @Override
    public Task createNewTask(String name, String description) {
        return new Task(name, description, counter++);
    }

    @Override
    public void deleteAllTasks() {
        taskList.clear();
        epicList.clear();
    }

    @Override
    public void deleteTask(int id) {
        taskList.remove(id);
        epicList.remove(id);
        for (Epic epic : epicList.values()) {
            epic.getSubTaskList().remove(id);
        }
    }

    @Override
    public Task findTask(int id) {
        return taskList.get(id);
    }

    @Override
    public Epic findEpic(int id) {
        return epicList.get(id);
    }

    @Override
    public void updateTask(int id, int status) {
        if (taskList.containsKey(id)) {
            taskList.get(id).setStatus(status);
            System.out.println("готово");
        } else System.out.println("нет такой задачи");
    }

    @Override
    public Collection<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void add(Task task) {
        inMemoryHistoryManager.add(task);
    }

}
