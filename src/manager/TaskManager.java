package tasks.manager;


import tasks.tasks.Epic;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {


        public int getCounter();

        public HashMap<Integer, Task> getTaskList();

        public HashMap<Integer, Epic> getEpicList();

        public HashMap<Integer, Task> returnTaskList();

        public HashMap<Integer, Epic> returnEpicList();

        public HashMap<Integer, Subtask> returnSubTaskList(int id);


        public void addNewEpic(Epic epic);

        public Epic createNewEpic(String name);

        public void changeStatus(int id);

        public Subtask createNewSubTask(String name, String description, int epicID);

        public void addNewTask(Task task);

        public Task createNewTask(String name, String description);

        public void deleteAllTasks();

        public void deleteTask(int id);

        public Task findTask(int id);

        public Epic findEpic(int id);

        public void updateTask(int id, int status);

        public List<Task> history();

        public void addTaskInTasksHistory(Task task);
}
