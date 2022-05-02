package tasks.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Task implements Comparable {
    private String name;
    private String description;
    private Status status = Status.NEW;
    private Integer id;
    private final TypeOfTask typeOfTask = TypeOfTask.TASK;
    private LocalDateTime startTime;
    private Duration duration;

    public Task() {
    }

    public Task(String name, String description, Status status, Integer id, String startTime, String duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        try {
            this.startTime = LocalDateTime.parse(startTime, formatter);
        } catch (NumberFormatException exception) {
            exception.getMessage();
        }
        String[] arrS = duration.split(" ");
        int[] arrI = new int[3];
        try {
            for (int i = 0; i < 3; i++) {
                arrI[i] = Integer.parseInt(arrS[i]);
            }
        } catch (NumberFormatException exception) {
            exception.getMessage();
        }
        this.duration = Duration.between(this.startTime, this.startTime.plusDays(arrI[0])
                .plusHours(arrI[1]).plusMinutes(arrI[2]));
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Task(String name, String description, Integer id, String startTime, String duration) {
        this.name = name;
        this.description = description;
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        try {
            this.startTime = LocalDateTime.parse(startTime, formatter);
        } catch (DateTimeParseException exception) {
            exception.getMessage();
        }
        String[] arrS = duration.split(" ");
        int[] arrI = new int[3];
        try {
            for (int i = 0; i < 3; i++) {
                arrI[i] = Integer.parseInt(arrS[i]);
            }
        } catch (NumberFormatException exception) {
            exception.getMessage();
        }
        this.duration = Duration.between(this.startTime, this.startTime.plusDays(arrI[0])
                .plusHours(arrI[1]).plusMinutes(arrI[2]));
    }

    public void setStatus(int status) {
        try {
            if (status == 1) this.status = Status.NEW;
            if (status == 2) this.status = Status.IN_PROGRESS;
            if (status == 3) this.status = Status.DONE;
            else throw new RuntimeException("status error");
        } catch (RuntimeException runtimeException) {
            runtimeException.getMessage();
        }
    }

    public String forSaving() {
        String dur = duration.toDays() + " " + duration.toHoursPart() + " "
                + duration.toMinutesPart();
        return id + "," + typeOfTask + "," + name + "," + status + "," + description + ","
                + DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm").format(startTime) + "," + dur + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && Objects.equals(id, task.id) && typeOfTask == task.typeOfTask && Objects.equals(startTime, task.startTime) && Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, id, typeOfTask, startTime, duration);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", typeOfTask=" + typeOfTask +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
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

