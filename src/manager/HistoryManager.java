package tasks.manager;

import tasks.tasks.Task;

import java.util.Collection;


public interface HistoryManager {
    void add(Task task);

    void remove(int id);

    Collection<Task> getHistory();
}
