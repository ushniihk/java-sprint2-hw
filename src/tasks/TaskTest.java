package tasks.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void shouldSetStatusNew() {
        Task task = new Task("one", "to do", 0, "11.11.2022. 11:11", "1 0 0");
        assertEquals(task.getStatus(), Status.NEW);
        task.setStatus(1);
        assertEquals(task.getStatus(), Status.NEW);
    }

    @Test
    void shouldSetStatusIN_PROGRESS() {
        Task task = new Task("one", "to do", 0, "11.11.2022. 11:11", "1 0 0");
        assertEquals(task.getStatus(), Status.NEW);
        task.setStatus(2);
        assertEquals(task.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    void shouldSetStatusDONE() {
        Task task = new Task("one", "to do", 0, "11.11.2022. 11:11", "1 0 0");
        assertEquals(task.getStatus(), Status.NEW);
        task.setStatus(3);
        assertEquals(task.getStatus(), Status.DONE);
    }

    @Test
    void shouldSetStatusNewIfNumberMoreThan3() {
        Task task = new Task("one", "to do", 0, "11.11.2022. 11:11", "1 0 0");
        assertEquals(task.getStatus(), Status.NEW);
        task.setStatus(4);
        assertEquals(task.getStatus(), Status.NEW);
    }

    @Test
    void shouldSetStatusNewIfNumberLessThan1() {
        Task task = new Task("one", "to do", 0, "11.11.2022. 11:11", "1 0 0");
        assertEquals(task.getStatus(), Status.NEW);
        task.setStatus(0);
        assertEquals(task.getStatus(), Status.NEW);
    }

    @Test
    public void shouldReturnEndTime(){
        Task task = new Task("one", "to do", 0, "11.11.2022. 11:11", "1 0 0");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        assertEquals(task.getEndTime(), LocalDateTime.parse("12.11.2022. 11:11", formatter));
    }

}