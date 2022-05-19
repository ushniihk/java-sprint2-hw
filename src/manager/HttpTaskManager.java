package tasks.manager;

import com.google.gson.Gson;
import tasks.API.KVTaskClient;
import tasks.tasks.Epic;
import tasks.tasks.Task;

import java.util.*;

public class HttpTaskManager extends FileBackedTasksManager {
    KVTaskClient client;
    String url;
    Gson gson;

    public HttpTaskManager(String url) {
        super(null);
        this.url = url;
        this.client = new KVTaskClient(url);
        this.gson = new Gson();
    }

    @Override
    public void save() {
        if (!getTaskList().isEmpty()) {
            StringBuilder sbTask = new StringBuilder();
            for (Task task : getTaskList().values()) {
                sbTask.append(gson.toJson(task));
                sbTask.append("&");
            }
            String savedTasks = sbTask.toString();
            client.put("Tasks", savedTasks);
        }
        if (!(getEpicList().size() == 0)) {
            StringBuilder sbEpic = new StringBuilder();
            for (Epic epic : getEpicList().values()) {
                sbEpic.append(gson.toJson(epic));
                sbEpic.append("&");
            }
            String savedEpics = sbEpic.toString();
            client.put("epics", savedEpics);
        }
        if (!(getPrioritizedTasks().size() == 0)) {
            StringBuilder prior = new StringBuilder();
            for (Task task : getPrioritizedTasks()) {
                prior.append(gson.toJson(task));
                prior.append("&");
            }
            String prioritazed = prior.toString();
            client.put("getprioritazed", prioritazed);
        }
        if (!(history().size() == 0)) {
            StringBuilder sbHistory = new StringBuilder();
            for (Task task : history()) {
                sbHistory.append(gson.toJson(task));
                sbHistory.append("&");
            }
            client.put("history", sbHistory.toString());
        }
    }

    public void load() {
        if (!client.load("Tasks").isEmpty()) {
            String s = client.load("Tasks");
            String[] arr = s.split("&");
            for (String t : arr) {
                Task task = gson.fromJson(t, Task.class);
                addNewTask(task);
            }
        }
        if (!client.load("epics").isEmpty()) {
            List<String> epicLine = List.of(client.load("epics").split("&"));
            for (String t : epicLine) {
                Epic epic = gson.fromJson(t, Epic.class);
                addNewEpic(epic);
            }
        }
        if (!client.load("getprioritazed").isEmpty()) {
            List<String> prioritazedLine = List.of(client.load("getprioritazed").split("&"));
            for (String t : prioritazedLine) {
                Task prior = gson.fromJson(t, Task.class);
                addToPrioritizedTasks(prior);
            }
        }
        if (!client.load("history").isEmpty()) {
            List<String> historyLine = List.of(client.load("history").split("&"));
            for (String t : historyLine) {
                Task historyTask = gson.fromJson(t, Task.class);
                add(historyTask);
            }
        }
    }
}
