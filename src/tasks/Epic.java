package tasks.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private final String epicName;
    private final String epicDescription;
    private HashMap<Integer, Subtask> subTaskList = new HashMap<>();
    private Status epicStatus;
    private final Integer epicId;
    private TypeOfTask epicTypeOfTask;
    private LocalDateTime epicStartTime;
    private Duration epicDuration = Duration.ZERO;

    public void setStartTimeEpic() {
        if (!subTaskList.isEmpty()) {
            LocalDateTime time = LocalDateTime.MAX;
            for (Subtask start : subTaskList.values()) {
                if (start.getStartTime().isBefore(time))
                    time = start.getStartTime();
            }
            this.epicStartTime = time;
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        return epicStartTime.plus(epicDuration);
    }

    public void setDurationEpic() {
        if (!subTaskList.isEmpty()) {
            LocalDateTime time = LocalDateTime.MIN;
            for (Subtask s : subTaskList.values()) {
                if (s.getEndTime().isAfter(time))
                    time = s.getEndTime();
            }
            epicDuration = Duration.between(getStartTime(), time);
        }
    }

    @Override
    public LocalDateTime getStartTime() {
        return epicStartTime;
    }

    public Duration getDuration() {
        return epicDuration;
    }

    public void setSubTaskList(HashMap<Integer, Subtask> subTaskList) {
        this.subTaskList = subTaskList;
        setStartTimeEpic();
        setDurationEpic();
    }

    public void putSubtaskList(Subtask task) {
        if (task != null) {
            subTaskList.put(task.getId(), task);
        } else System.out.println("не получилось добавить задачу, на это время уже есть делишки");
    }

    @Override
    public Status getStatus() {
        return epicStatus;
    }

    @Override
    public Integer getId() {
        return epicId;
    }

    public Epic(String name, Integer id, String description) {
        this.epicName = name;
        this.epicId = id;
        this.epicDescription = description;
        this.epicStatus = Status.NEW;
        this.epicTypeOfTask = TypeOfTask.EPIC;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        this.epicStartTime = LocalDateTime.parse("31.12.9999. 23:59", formatter);
        this.epicDuration = Duration.ZERO;
    }

    public Epic(String name, String description, Status status, int id) {
        this.epicName = name;
        this.epicId = id;
        this.epicDescription = description;
        this.epicStatus = status;
        this.epicTypeOfTask = TypeOfTask.EPIC;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        this.epicStartTime = LocalDateTime.parse("31.12.9999. 23:59", formatter);
        this.epicDuration = Duration.ZERO;
    }


    public HashMap<Integer, Subtask> getSubTaskList() {
        return subTaskList;
    }

    @Override
    public void setStatus(int status) {
    }

    public void setStatus() {
        int counterStatus = 0;
        int sum = 0;
        for (Subtask subtask : subTaskList.values()) {
            if (subtask.getStatus() == Status.NEW) sum++;
            if (subtask.getStatus() == Status.IN_PROGRESS) sum = sum + 2;
            if (subtask.getStatus() == Status.DONE) sum = sum + 3;
            counterStatus++;
        }
        if (sum == (counterStatus * 3) && sum != 0) {
            epicStatus = Status.DONE;
        } else if (sum > counterStatus) {
            epicStatus = Status.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + epicName + '\'' +
                ", description='" + epicDescription + '\'' +
                ", subTaskList=" + subTaskList +
                ", status=" + epicStatus +
                ", id=" + epicId +
                ", typeOfTask=" + epicTypeOfTask +
                ", startTime=" + epicStartTime +
                ", duration=" + epicDuration +
                '}';
    }

    public String forSaving() {
        String dur = epicDuration.toDays() + " " + epicDuration.toHoursPart() + " "
                + epicDuration.toMinutesPart();
        return epicId + "," + epicTypeOfTask + "," + epicName + "," + epicStatus + "," + epicDescription + ","
                + DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm").format(epicStartTime) + ","
                + dur + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(epicName, epic.epicName) && Objects.equals(epicDescription, epic.epicDescription)
                && Objects.equals(subTaskList, epic.subTaskList) && epicStatus == epic.epicStatus
                && Objects.equals(epicId, epic.epicId) && epicTypeOfTask == epic.epicTypeOfTask
                && Objects.equals(epicStartTime, epic.epicStartTime) && Objects.equals(epicDuration, epic.epicDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicName, epicDescription, subTaskList, epicStatus, epicId,
                epicTypeOfTask, epicStartTime, epicDuration);
    }

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        if (getStartTime().isAfter(task.getStartTime()))
            return 1;
        if (getStartTime().isBefore(task.getStartTime()))
            return -1;
        return 0;
    }
}

