package tasks.Tests;

import org.junit.jupiter.api.Test;
import tasks.manager.FileBackedTasksManager;
import tasks.manager.InMemoryTaskManager;
import tasks.tasks.Epic;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    File file = new File("savingTest.csv");

    FileBackedTasksManager manager = FileBackedTasksManager.load(file);

    @Test
    public void shouldSaveAndLoad() {
        Epic epic = manager.createNewEpic("name", "description");
        manager.addNewEpic(epic);
        manager.add(epic);
        manager.save();
        FileBackedTasksManager manager1 = FileBackedTasksManager.load(file);
        assertAll(
                () -> assertTrue(manager1.history().contains(epic)),
                () -> assertEquals(manager1.history().size(), manager1.getCounter()),
                () -> assertTrue(manager1.getEpicList().containsValue(epic)),
                () -> assertEquals(manager1.getEpicList().size(), manager1.getCounter()),
                () -> assertTrue(manager1.getTaskList().isEmpty()),
                () -> assertTrue(manager1.getEpicList().get(0).getSubTaskList().isEmpty())
        );
    }
}