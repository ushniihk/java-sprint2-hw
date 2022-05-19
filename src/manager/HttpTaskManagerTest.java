package tasks.manager;

import org.junit.jupiter.api.Test;
import tasks.API.KVServer;
import tasks.tasks.Epic;
import tasks.tasks.Task;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest {
    KVServer kvServer;

    {
        try {
            kvServer = new KVServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void start() {
        kvServer.start();
    }

    void stop() {
        kvServer.stop();
    }


    @Test
    void shouldSaveAndLoadTask() {
        start();
        TaskManager manager = Managers.getDefaultHttpTaskManager("http://localhost:8082/");
        TaskManager manager2 = Managers.getDefaultHttpTaskManager("http://localhost:8082/");
        Task task = new Task("aaa", "aaaaa", 0, "11.06.2000. 06:06", "1 1 1");
        manager.addNewTask(task);
        manager2.load();
        Task task1 = manager2.findTask(0);
        assertEquals(task, task1);
        stop();
    }

    @Test
    void shouldSaveAndLoadEpic() {
        start();
        TaskManager manager = Managers.getDefaultHttpTaskManager("http://localhost:8082/");
        TaskManager manager2 = Managers.getDefaultHttpTaskManager("http://localhost:8082/");
        Epic epic = new Epic("aaa", 2, "des");
        manager.addNewEpic(epic);
        manager2.load();
        Epic epic1 = manager2.findEpic(2);
        assertEquals(epic, epic1);
        stop();
    }
}