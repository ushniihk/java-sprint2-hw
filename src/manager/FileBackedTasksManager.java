package tasks.manager;

import tasks.Exceptions.ManagerSaveException;
import tasks.tasks.*;

import java.io.*;
import java.util.*;


public class FileBackedTasksManager extends InMemoryTaskManager {

    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    File file;
    private int counter = 0;
    private Map<Integer, Task> taskList = new HashMap<>();
    private Map<Integer, Epic> epicList = new HashMap<>();
    private Collection<Task> prioritizedTasks = new TreeSet<>();

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {
        try (Writer fileWriter = new FileWriter(file)) {
            for (Task t : getTaskList().values())
                fileWriter.write(t.forSaving());
            for (Epic e : getEpicList().values()) {
                fileWriter.write(e.forSaving());
                for (Subtask s : e.getSubTaskList().values())
                    fileWriter.write(s.forSaving());
            }
            fileWriter.write("\n");
            for (Task t : history()) {
                fileWriter.write(t.getId() + ",");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("something was wrong");
        }
    }

    private static Task taskFromString(String value) {
        String[] arr;
        arr = value.split(",");
        Integer id = Integer.parseInt(arr[0]);
        String name = arr[2];
        Status status = Status.valueOf(arr[3]);
        String description = arr[4];
        String startTime = arr[5];
        String duration = arr[6];
        return new Task(name, description, status, id, startTime, duration);
    }

    private static Epic epicFromString(String value) {
        String[] arr;
        arr = value.split(",");
        int id = Integer.parseInt(arr[0]);
        String name = arr[2];
        Status status = Status.valueOf(arr[3]);
        String description = arr[4];
        return new Epic(name, description, status, id);
    }

    private static Subtask subTaskFromString(String value) {
        String[] arr;
        arr = value.split(",");
        Integer id = Integer.parseInt(arr[0]);
        String name = arr[2];
        Status status = Status.valueOf(arr[3]);
        String description = arr[4];
        int epicId = Integer.parseInt(arr[5]);
        String startTime = arr[6];
        String duration = arr[7];
        return new Subtask(name, description, status, epicId, id, startTime, duration);
    }

    private static List<Integer> listFromString(String value) {
        String[] arr = value.split(",");
        Integer[] arrIntegers = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrIntegers[i] = Integer.parseInt(arr[i]);
        }
        return Arrays.asList(arrIntegers);
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        List<Integer> listOfId = new ArrayList<>();
        List<Subtask> listOfSubtask = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("SUBTASK")) {
                    listOfSubtask.add(subTaskFromString(line));
                } else if (line.contains("TASK")) {
                    Task t = taskFromString(line);
                    taskManager.taskList.put(t.getId(), t);
                    for (Task task : taskManager.taskList.values()) {
                        if (task.getId() >= taskManager.counter)
                            taskManager.counter = task.getId() + 1;
                    }
                } else if (line.contains("EPIC")) {
                    Epic epic = epicFromString(line);
                    taskManager.epicList.put(epic.getId(), epic);
                    for (Epic e : taskManager.epicList.values()) {
                        if (e.getId() >= taskManager.counter)
                            taskManager.counter = e.getId() + 1;
                        for (Subtask subtask : e.getSubTaskList().values()) {
                            if (subtask.getId() >= taskManager.counter)
                                taskManager.counter = subtask.getId() + 1;
                        }
                    }
                } else {
                    if (!(line.isBlank()))
                        listOfId = listFromString(line);
                }
            }
            for (Subtask s : listOfSubtask) {
                taskManager.epicList.get(s.getEpicID()).putSubtaskList(s);
            }

            if (listOfId != null) {

                for (Integer id : listOfId) {
                    if (taskManager.getTaskList().containsKey(id)) {
                        taskManager.add(taskManager.getTaskList().get(id));
                    }
                    if (taskManager.getEpicList().containsKey(id)) {
                        taskManager.add(taskManager.getEpicList().get(id));
                    }
                    boolean answer = false;
                    for (Epic epic : taskManager.getEpicList().values()) {
                        if (epic.getSubTaskList().containsKey(id)) answer = true;
                    }
                    if (answer) {
                        for (Epic epic : taskManager.getEpicList().values()) {
                            if (epic.getSubTaskList().containsKey(id))
                                taskManager.add(epic.getSubTaskList().get(id));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new ManagerSaveException("something was wrong");
        }
        return taskManager;
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
    public Collection<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void addNewEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
        save();
    }

    @Override
    public void changeStatus(Integer id) {
        epicList.get(id).setStatus();
        save();
    }

    @Override
    public void deleteAllTasks() {
        taskList.clear();
        epicList.clear();
        prioritizedTasks.clear();
        save();
    }

    @Override
    public void updateTask(int id, int status) {
        if (taskList.containsKey(id)) {
            taskList.get(id).setStatus(status);
            System.out.println("готово");
        } else System.out.println("нет такой задачи");
        save();
    }

    @Override
    public void add(Task task) {
        inMemoryHistoryManager.add(task);
        save();
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
    public Epic createNewEpic(String name, String description) {
        Epic epic = new Epic(name, counter++, description);
        addToPrioritizedTasks(epic);
        return epic;
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
        if (task != null) {
            taskList.put(task.getId(), task);
        } else System.out.println("не получилось добавить задачу, на это время уже есть делишки");
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
    public Collection<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public void addToPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
    }
}