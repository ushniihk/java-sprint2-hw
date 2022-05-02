package tasks.manager;

import org.junit.jupiter.api.Test;
import tasks.tasks.Task;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Test
    public void shouldAddToHistoryList() {
        Task task = new Task("name", "description", 0, "11.11.2022. 11:11", "1 0 0");
        inMemoryHistoryManager.add(task);
        assertTrue(inMemoryHistoryManager.getHistory().contains(task));
        assertEquals(inMemoryHistoryManager.getHistory().size(), 1);
    }

    @Test
    public void shouldReturnEmptyHistory() {
        assertTrue(inMemoryHistoryManager.getHistory().isEmpty());
    }

    @Test
    public void shouldRemoveDuplicateInBeginningHistory() {
        Task task = new Task("name1", "description1", 0, "11.11.2022. 11:11", "1 0 0");
        Task task2 = new Task("name2", "description2", 1, "11.11.2022. 11:11", "1 0 0");
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task2);
        int counter = 0;
        int i = 0;
        for (Task t: inMemoryHistoryManager.getHistory()) {
            if (t.equals(task))
                i = counter;
            counter++;
        }
        assertEquals(i, 0);
        assertEquals(inMemoryHistoryManager.getHistory().size(), 2);
        inMemoryHistoryManager.add(task);
        counter = 0;
        i = 0;
        for (Task t: inMemoryHistoryManager.getHistory()) {
            if (t.equals(task))
                i = counter;
            counter++;
        }
        assertEquals(i, 1);
        assertEquals(inMemoryHistoryManager.getHistory().size(), 2);
    }
    @Test
    public void shouldRemoveDuplicateInMiddleHistory() {
        Task task = new Task("name1", "description1", 0, "11.11.2022. 11:11", "1 0 0");
        Task task2 = new Task("name2", "description2", 1, "11.11.2022. 11:11", "1 0 0");
        Task task3 = new Task("name3", "description3", 2, "11.11.2022. 11:11", "1 0 0");
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        int counter = 0;
        int i = 0;
        for (Task t: inMemoryHistoryManager.getHistory()) {
            if (t.equals(task2))
                i = counter;
            counter++;
        }
        assertEquals(i, 1);
        assertEquals(inMemoryHistoryManager.getHistory().size(), 3);
        inMemoryHistoryManager.add(task2);
        counter = 0;
        i = 0;
        for (Task t: inMemoryHistoryManager.getHistory()) {
            if (t.equals(task2))
                i = counter;
            counter++;
        }
        assertEquals(i, 2);
        assertEquals(inMemoryHistoryManager.getHistory().size(), 3);
    }

    @Test
    public void removeFromBeginning(){
        Task task = new Task("name1", "description1", 0, "11.11.2022. 11:11", "1 0 0");
        Task task2 = new Task("name2", "description2", 1,"11.11.2022. 11:11", "1 0 0");
        Task task3 = new Task("name3", "description3", 2, "11.11.2022. 11:11", "1 0 0");
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        inMemoryHistoryManager.remove(0);
        assertFalse(inMemoryHistoryManager.getHistory().contains(task));

    }

    @Test
    public void removeFromMiddle(){
        Task task = new Task("name1", "description1", 0, "11.11.2022. 11:11", "1 0 0");
        Task task2 = new Task("name2", "description2", 1, "11.11.2022. 11:11", "1 0 0");
        Task task3 = new Task("name3", "description3", 2, "11.11.2022. 11:11", "1 0 0");
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        inMemoryHistoryManager.remove(1);
        assertFalse(inMemoryHistoryManager.getHistory().contains(task2));
    }

    @Test
    public void removeFromEnd(){
        Task task = new Task("name1", "description1", 0, "11.11.2022. 11:11", "1 0 0");
        Task task2 = new Task("name2", "description2", 1,"11.11.2022. 11:11", "1 0 0");
        Task task3 = new Task("name3", "description3", 2, "11.11.2022. 11:11", "1 0 0");
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        inMemoryHistoryManager.remove(2);
        assertFalse(inMemoryHistoryManager.getHistory().contains(task3));
    }
}