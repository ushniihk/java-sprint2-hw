package tasks.manager;


import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.Collection;
import java.util.HashMap;

public interface TaskManager {


    int getCounter();

    HashMap<Integer, Task> getTaskList();

    HashMap<Integer, Epic> getEpicList();

    HashMap<Integer, Task> returnTaskList();

    HashMap<Integer, Epic> returnEpicList();

    HashMap<Integer, Subtask> returnSubTaskList(int id);


    void addNewEpic(Epic epic);

    Epic createNewEpic(String name, String description);

    void changeStatus(Integer id);

    Subtask createNewSubTask(String name, String description, int epicID);

    void addNewTask(Task task);

    Task createNewTask(String name, String description);

    void deleteAllTasks();

    void deleteTask(int id);

    Task findTask(int id);

    Epic findEpic(int id);

    void updateTask(int id, int status);

    Collection<Task> history();

    void add(Task task);


    void setTaskList(HashMap<Integer, Task> taskList);

    void setEpicList(HashMap<Integer, Epic> taskList);
}
