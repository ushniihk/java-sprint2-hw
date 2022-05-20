package tasks.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.tasks.Status;
import tasks.tasks.Subtask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void shouldSetStatusNew() {
        Subtask subtask = new Subtask("one", "to do", 0, 1, "11.11.2022. 11:11", "1 0 0");
        Assertions.assertEquals(subtask.getStatus(), Status.NEW);
        subtask.setStatus(1);
        assertEquals(subtask.getStatus(), Status.NEW);
    }

    @Test
    void shouldSetStatusIN_PROGRESS() {
        Subtask subtask = new Subtask("one", "to do", 0, 1, "11.11.2022. 11:11", "1 0 0");
        assertEquals(subtask.getStatus(), Status.NEW);
        subtask.setStatus(2);
        assertEquals(subtask.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    void shouldSetStatusDONE() {
        Subtask subtask = new Subtask("one", "to do", 0, 1, "11.11.2022. 11:11", "1 0 0");
        assertEquals(subtask.getStatus(), Status.NEW);
        subtask.setStatus(3);
        assertEquals(subtask.getStatus(), Status.DONE);
    }

    @Test
    void shouldSetStatusNewIfNumberMoreThan3() {
        Subtask subtask = new Subtask("one", "to do", 0, 1, "11.11.2022. 11:11", "1 0 0");
        assertEquals(subtask.getStatus(), Status.NEW);
        subtask.setStatus(4);
        assertEquals(subtask.getStatus(), Status.NEW);
    }

    @Test
    void shouldSetStatusNewIfNumberLessThan1() {
        Subtask subtask = new Subtask("one", "to do", 0, 1, "11.11.2022. 11:11", "1 0 0");
        assertEquals(subtask.getStatus(), Status.NEW);
        subtask.setStatus(0);
        assertEquals(subtask.getStatus(), Status.NEW);
    }

    @Test
    public void shouldReturnEndTime(){
        Subtask subtask = new Subtask("one", "to do", 0, 1, "11.11.2022. 11:11", "1 0 0");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        assertEquals(subtask.getEndTime(), LocalDateTime.parse("12.11.2022. 11:11", formatter));
    }


}