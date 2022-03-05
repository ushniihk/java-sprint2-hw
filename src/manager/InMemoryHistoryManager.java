package tasks.manager;

import tasks.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> tasksHistory = new ArrayList<>();


    @Override
    public void add(Task task) {
        tasksHistory.add(task);
        if (tasksHistory.size() > 10) tasksHistory.remove(0);
    }

    @Override
    public List<Task> getHistory() {
        return tasksHistory;
    }
}
