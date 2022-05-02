package tasks.manager;


import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public interface TaskManager {


    int getCounter();

    Map<Integer, Task> getTaskList();

    Map<Integer, Epic> getEpicList();

    Map<Integer, Task> returnTaskList();

    Map<Integer, Epic> returnEpicList();

    HashMap<Integer, Subtask> returnSubTaskList(int id);


    void addNewEpic(Epic epic);

    Epic createNewEpic(String name, String description);

    void changeStatus(Integer id);

    Subtask createNewSubTask(String name, String description, int epicID, String startTime, String duration);

    void addNewTask(Task task);

    Task createNewTask(String name, String description, String startTime, String duration);

    void deleteAllTasks();

    void deleteTask(int id);

    Task findTask(int id);

    Epic findEpic(int id);

    void updateTask(int id, int status);

    Collection<Task> history();

    void add(Task task);

    TreeSet getPrioritizedTasks();

    void addToPrioritizedTasks(Task task);

}
