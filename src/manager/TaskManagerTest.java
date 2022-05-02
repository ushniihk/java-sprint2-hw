package tasks.manager;

import org.junit.jupiter.api.Test;
import tasks.tasks.Epic;
import tasks.tasks.Status;
import tasks.tasks.Subtask;
import tasks.tasks.Task;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


abstract class TaskManagerTest<T extends TaskManager> {

    TaskManager taskManager = new InMemoryTaskManager();


    @Test
    public void shouldCreateNewEpic() {
        Epic epic = new Epic("first epic", 0, "to do something");
        assertEquals(taskManager.createNewEpic("first epic", "to do something"), epic);
    }

    @Test
    public void shouldAddNewEpic() {
        Epic epic = new Epic("first epic", 0, "to do something");
        taskManager.addNewEpic(epic);
        assertEquals(epic, taskManager.getEpicList().get(0));
    }

    @Test
    public void shouldChangeEpicStatus() {
        Epic epic = new Epic("first epic", 0, "to do something");
        epic.putSubtaskList(new Subtask("first subtask", "to do", Status.IN_PROGRESS, 0, 1,
                "11.11.2022. 11:11", "1 0 0"));
        epic.setStatus();
        assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void shouldCreateNewSubtask() {
        Epic epic = taskManager.createNewEpic("first epic", "to do something");
        taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("First subtask", "to do", Status.NEW, 0, 1, "11.11.2022. 11:11", "1 0 0");
        assertEquals(subtask, taskManager.createNewSubTask("First subtask", "to do", 0, "11.11.2022. 11:11", "1 0 0"));
        assertTrue(taskManager.getEpicList().containsKey(subtask.getEpicID()));

    }

    @Test
    public void shouldCreateNewTask() {
        Task task = new Task("Task1", "to do", Status.NEW, 0, "11.11.2022. 11:11", "1 0 0");
        assertEquals(task, taskManager.createNewTask("Task1", "to do", "11.11.2022. 11:11", "1 0 0"));
    }

    @Test
    public void shouldAddNewTask() {
        Task task = new Task("Task1", "to do", Status.NEW, 0, "11.11.2022. 11:11", "1 0 0");
        taskManager.addNewTask(task);
        assertEquals(task, taskManager.getTaskList().get(0));
    }

    @Test
    public void shouldDeleteAllTasks() {
        taskManager.addNewEpic(taskManager.createNewEpic("First epic", "to do"));
        Task task = taskManager.createNewTask("First task", "to do", "11.11.2022. 11:11", "1 0 0");
        taskManager.addNewTask(task);
        assertEquals(taskManager.getTaskList().size(), 1);
        assertEquals(taskManager.getEpicList().size(), 1);
        taskManager.deleteAllTasks();
        assertEquals(taskManager.getTaskList().size(), 0);
        assertEquals(taskManager.getEpicList().size(), 0);
    }

    @Test
    public void shouldDeleteTask() {
        Epic epic = new Epic("First epic", 0, "to do");
        taskManager.addNewEpic(epic);
        assertTrue(taskManager.getEpicList().containsValue(epic));
        taskManager.deleteTask(0);
        assertFalse(taskManager.getEpicList().containsValue(epic));
    }

    @Test
    public void shouldFindTask() {
        Task task = new Task("Task1", "to do", Status.NEW, 0, "11.11.2022. 11:11", "1 0 0");
        taskManager.addNewTask(task);
        assertEquals(taskManager.findTask(0), task);
    }

    @Test
    public void shouldFindEpic() {
        Epic epic = new Epic("first epic", 0, "to do something");
        taskManager.addNewEpic(epic);
        assertEquals(epic, taskManager.findEpic(0));
    }

    @Test
    public void shouldChangeStatusInTaskToIN_PROGRESS() {
        Task task = new Task("Task1", "to do", Status.NEW, 0, "11.11.2022. 11:11", "1 0 0");
        taskManager.addNewTask(task);
        taskManager.updateTask(0, 2);
        assertEquals(task.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void shouldAddTaskInHistory() {
        Task task = new Task("Task1", "to do", Status.NEW, 0, "11.11.2022. 11:11", "1 0 0");
        assertFalse(taskManager.history().contains(task));
        taskManager.add(task);
        assertTrue(taskManager.history().contains(task));
    }

    @Test
    public void shouldReturnRightHistory() {
        Task task = new Task("Task1", "to do", Status.NEW, 0, "11.11.2022. 11:11", "1 0 0");
        assertTrue(taskManager.history().isEmpty());
        taskManager.add(task);
        assertTrue(taskManager.history().contains(task));
        assertEquals(taskManager.history().size(), 1);
    }

    @Test
    public void shouldReturnSortedTasks() {
        Task task = taskManager.createNewTask("Task1", "to do", "11.11.2022. 11:11", "1 0 0");
        taskManager.addNewTask(task);
        Epic epic = taskManager.createNewEpic("first epic", "to do something");
        taskManager.addNewEpic(epic);
        Epic epic1 = taskManager.createNewEpic("first epic", "to do something");
        taskManager.addNewEpic(epic1);
        Subtask subtask = taskManager.createNewSubTask("one", "to do", 2, "05.05.2022. 11:11", "1 0 0");
        epic1.putSubtaskList(subtask);
        TreeSet<Task> set = (TreeSet<Task>) taskManager.getPrioritizedTasks();
        assertEquals(set.first(), subtask);
        assertEquals(set.last(), epic);
    }
}
