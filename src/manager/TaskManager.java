package tasks.manager;


import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.HashMap;

public interface TaskManager {


    abstract int getCounter();

    abstract HashMap<Integer, Task> getTaskList();

    abstract HashMap<Integer, Epic> getEpicList();

    abstract HashMap<Integer, Task> returnTaskList();

    abstract HashMap<Integer, Epic> returnEpicList();

    abstract HashMap<Integer, Subtask> returnSubTaskList(int id);


    abstract void addNewEpic(Epic epic);

    abstract Epic createNewEpic(String name);

    abstract void changeStatus(Integer id);

    abstract Subtask createNewSubTask(String name, String description, int epicID);

    abstract void addNewTask(Task task);

    abstract Task createNewTask(String name, String description);

    abstract void deleteAllTasks();

    abstract void deleteTask(int id);

    abstract Task findTask(int id);

    abstract Epic findEpic(int id);

    abstract void updateTask(int id, int status);

}
