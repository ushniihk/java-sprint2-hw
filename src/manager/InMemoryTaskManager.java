package tasks.manager;

import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    private int counter = 0;
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private Collection<Task> prioritizedTasks = new TreeSet<>();


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
        Epic epic = new Epic(name, counter++, description);
        addToPrioritizedTasks(epic);
        return epic;
    }

    @Override
    public void changeStatus(Integer id) {
        epicList.get(id).setStatus();
    }

    @Override
    public Subtask createNewSubTask(String name, String description, int epicID, String startTime, String duration) {
        Subtask s = new Subtask(name, description, epicID, counter++, startTime, duration);
        if (!prioritizedTasks.isEmpty()) {
            for (Task t : prioritizedTasks) {
                if (((t.getEndTime().isAfter(s.getStartTime()) || t.getEndTime().isEqual(s.getStartTime())))
                        && t.getEndTime().isBefore(s.getEndTime()))
                    return null;
            }
        }
        addToPrioritizedTasks(s);
        return s;
    }

    @Override
    public void addNewTask(Task task) {
        if (task != null)
            taskList.put(task.getId(), task);
    }

    @Override
    public Task createNewTask(String name, String description, String startTime, String duration) {
        Task task = new Task(name, description, counter++, startTime, duration);
        if (!prioritizedTasks.isEmpty()) {
            for (Task t : prioritizedTasks) {
                if ((t.getEndTime().isAfter(task.getStartTime()) || t.getEndTime().isEqual(task.getStartTime()))
                        && t.getEndTime().isBefore(task.getEndTime()))
                    return null;
            }
        }
        addToPrioritizedTasks(task);
        return task;
    }

    @Override
    public void deleteAllTasks() {
        taskList.clear();
        epicList.clear();
        prioritizedTasks.clear();
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

    @Override
    public Collection<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public void addToPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
    }
}
