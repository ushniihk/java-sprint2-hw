package tasks.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private final String name;
    private final String description;
    private HashMap<Integer, Subtask> subTaskList = new HashMap<>();
    private Status status = Status.NEW;
    private final Integer id;
    private final TypeOfTask typeOfTask = TypeOfTask.EPIC;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
    private LocalDateTime startTime = LocalDateTime.parse("31.12.9999. 23:59", formatter);

    private Duration duration = Duration.ZERO;

    public void setStartTimeEpic() {
        if (!subTaskList.isEmpty()) {
            LocalDateTime time = LocalDateTime.MAX;
            for (Subtask start : subTaskList.values()) {
                if (start.getStartTime().isBefore(time))
                    time = start.getStartTime();
            }
            this.startTime = time;
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public void setDurationEpic() {
        if (!subTaskList.isEmpty()) {
            LocalDateTime time = LocalDateTime.MIN;
            for (Subtask s : subTaskList.values()) {
                if (s.getEndTime().isAfter(time))
                    time = s.getEndTime();
            }
            duration = Duration.between(getStartTime(), time);
        }
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
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
        return status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Epic(String name, Integer id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public Epic(String name, String description, Status status, int id) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.status = status;
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
            status = Status.DONE;
        } else if (sum > counterStatus) {
            status = Status.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subTaskList=" + subTaskList +
                ", status=" + status +
                ", id=" + id +
                ", typeOfTask=" + typeOfTask +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }

    public String forSaving() {
        String dur = duration.toDays() + " " + duration.toHoursPart() + " "
                + duration.toMinutesPart();
        return id + "," + typeOfTask + "," + name + "," + status + "," + description + ","
                + DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm").format(startTime) + ","
                + dur + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(name, epic.name) && Objects.equals(description, epic.description) && Objects.equals(subTaskList, epic.subTaskList) && status == epic.status && Objects.equals(id, epic.id) && typeOfTask == epic.typeOfTask && Objects.equals(startTime, epic.startTime) && Objects.equals(duration, epic.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, subTaskList, status, id, typeOfTask, startTime, duration);
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

