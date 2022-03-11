package tasks.manager;


import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {


    int getCounter();

    HashMap<Integer, Task> getTaskList();

    HashMap<Integer, Epic> getEpicList();

    HashMap<Integer, Task> returnTaskList();

    HashMap<Integer, Epic> returnEpicList();

    HashMap<Integer, Subtask> returnSubTaskList(int id);


    void addNewEpic(Epic epic);

    Epic createNewEpic(String name);

    void changeStatus(Integer id);

    Subtask createNewSubTask(String name, String description, int epicID);

    void addNewTask(Task task);

    Task createNewTask(String name, String description);

    void deleteAllTasks();

    void deleteTask(int id);

    Task findTask(int id);

    Epic findEpic(int id);

    void updateTask(int id, int status);

    public List<Task> history();


}
