package tasks.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Subtask extends Task {
    private final String name;
    private final String description;
    private Status status = Status.NEW;
    private final int epicID;
    private final Integer id;
    private final TypeOfTask typeOfTask = TypeOfTask.SUBTASK;
    private LocalDateTime startTime;
    private final Duration duration;

    public Subtask(String name, String description, Status status, int epicID, int id, String startTime, String duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicID = epicID;
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

    public Subtask(String name, String description, int epicID, int id, String startTime, String duration) {
        this.name = name;
        this.description = description;
        this.epicID = epicID;
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

    public int getEpicID() {
        return epicID;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
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

    @Override
    public String forSaving() {
        String dur = duration.toDays() + " " + duration.toHoursPart() + " "
                + duration.toMinutesPart();
        return id + "," + typeOfTask + "," + name + "," + status + "," + description + "," + epicID + ","
                + DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm").format(startTime) + "," + dur + "\n";
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epicID=" + epicID +
                ", id=" + id +
                ", typeOfTask=" + typeOfTask +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicID == subtask.epicID && Objects.equals(name, subtask.name) && Objects.equals(description, subtask.description) && status == subtask.status && Objects.equals(id, subtask.id) && typeOfTask == subtask.typeOfTask && Objects.equals(startTime, subtask.startTime) && Objects.equals(duration, subtask.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, status, epicID, id, typeOfTask, startTime, duration);
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