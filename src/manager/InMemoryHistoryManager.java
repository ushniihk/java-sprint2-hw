package tasks.manager;

import tasks.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    final private Map<Integer, Node> historyTasks = new HashMap<>();

    @Override
    public void add(Task task) {
        if (!historyTasks.isEmpty()) {
            if (historyTasks.containsKey(task.getId())) {
                remove(task.getId());
            }
        }
        linkLast(task);
    }

    @Override
    public Collection<Task> getHistory() {
        return getTasks();
    }

    private static class Node {
        Task item;
        Node next;
        Node prev;

        Node(Node prev, Task element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node head;
    private Node tail;

    private void linkLast(Task e) {
        final Node l = tail;
        final Node newNode = new Node(l, e, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        historyTasks.put(e.getId(), newNode);
    }

    private void removeNode(Node n) {
        Node prev = n.prev;
        Node next = n.next;
        if (prev != null) {
            prev.next = next;
        } else head = next;
        if (next != null) {
            next.prev = prev;
        } else tail = prev;
    }

    @Override
    public void remove(int id) {
        removeNode(historyTasks.get(id));
        historyTasks.remove(id);
    }

    public Collection<Task> getTasks() {
        Collection<Task> tasksHistory = new ArrayList<>();
        if (head != null) {
            Node first = head;
            while (first != null) {
                tasksHistory.add(first.item);
                first = first.next;
            }
        }
        return tasksHistory;
    }
}