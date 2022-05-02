package tasks.tasks;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic = new Epic("First epic", 0, "to do something");

    @Test
    public void mustBeNewWithAnEmptyList() {
        assertEquals(epic.getStatus(), Status.NEW);
    }

    @Test
    public void mustBeNewWithAllSubtasksNew() {
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 1, "11.11.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 2, "11.11.2022. 11:11", "1 0 0"));
        epic.setStatus();
        assertEquals(epic.getStatus(), Status.NEW);
    }

    @Test
    public void mustBeDoneWithAllSubtasksDone() {
        epic.putSubtaskList(new Subtask("first subtask", "to do something", Status.DONE, 0, 1, "11.11.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", Status.DONE, 0, 2, "11.11.2022. 11:11", "1 0 0"));
        epic.setStatus();
        assertEquals(epic.getStatus(), Status.DONE);
    }

    @Test
    public void mustBeIN_PROGRESSWithSubtasksDoneAndNew() {
        epic.putSubtaskList(new Subtask("first subtask", "to do something", Status.NEW, 0, 1, "11.11.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", Status.DONE, 0, 2, "11.11.2022. 11:11", "1 0 0"));
        epic.setStatus();
        assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void mustBeIN_PROGRESSWithSubtaskIN_PROGRESS() {
        epic.putSubtaskList(new Subtask("first subtask", "to do something", Status.IN_PROGRESS, 0, 1, "11.11.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", Status.DONE, 0, 2, "11.11.2022. 11:11", "1 0 0"));
        epic.setStatus();
        assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void mustSetStartTimeFromFirstSubTask(){
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 1, "11.10.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 2, "11.11.2022. 11:11", "1 0 0"));
        epic.setStartTimeEpic();
        assertEquals(epic.getStartTime(), epic.getSubTaskList().get(1).getStartTime());
    }

    @Test
    public void mustSetStartTimeFromLastSubTask(){
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 1, "11.10.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 2, "11.09.2022. 11:11", "1 0 0"));
        epic.setStartTimeEpic();
        assertEquals(epic.getStartTime(), epic.getSubTaskList().get(2).getStartTime());
    }

    @Test
    public void mustSetDurationFromSubTasks(){
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 1, "11.09.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 2, "12.09.2022. 11:11", "1 0 0"));
        epic.setStartTimeEpic();
        epic.setDurationEpic();
        assertEquals(epic.getDuration(), Duration.between(epic.getSubTaskList().get(1).getStartTime()
                , epic.getSubTaskList().get(2).getEndTime()));
    }

    @Test
    public void mustReturnEndTimeFromSecondSubtask(){
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 1, "11.09.2022. 11:11", "1 0 0"));
        epic.putSubtaskList(new Subtask("first subtask", "to do something", 0, 2, "12.09.2022. 11:11", "1 0 0"));
        epic.setStartTimeEpic();
        epic.setDurationEpic();
        assertEquals(epic.getEndTime(), epic.getSubTaskList().get(2).getEndTime());
    }
}