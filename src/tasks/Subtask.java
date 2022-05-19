package tasks.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Subtask extends Task {
    private final String subtaskName;
    private final String subtaskDescription;
    private Status subtaskStatus = Status.NEW;
    private final int epicID;
    private final Integer subtaskId;
    private final TypeOfTask subtaskTypeOfTask = TypeOfTask.SUBTASK;
    private LocalDateTime subtaskStartTime;
    private final Duration subtaskDuration;

    public Subtask(String name, String description, Status status, int epicID, int id, String startTime, String duration) {
        this.subtaskName = name;
        this.subtaskDescription = description;
        this.subtaskStatus = status;
        this.epicID = epicID;
        this.subtaskId = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        try {
            this.subtaskStartTime = LocalDateTime.parse(startTime, formatter);
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
        this.subtaskDuration = Duration.between(this.subtaskStartTime, this.subtaskStartTime.plusDays(arrI[0])
                .plusHours(arrI[1]).plusMinutes(arrI[2]));
    }

    public Subtask(String name, String description, int epicID, int id, String startTime, String duration) {
        this.subtaskName = name;
        this.subtaskDescription = description;
        this.epicID = epicID;
        this.subtaskId = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        try {
            this.subtaskStartTime = LocalDateTime.parse(startTime, formatter);
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
        this.subtaskDuration = Duration.between(this.subtaskStartTime, this.subtaskStartTime.plusDays(arrI[0])
                .plusHours(arrI[1]).plusMinutes(arrI[2]));
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public Integer getId() {
        return subtaskId;
    }

    @Override
    public Status getStatus() {
        return subtaskStatus;
    }

    @Override
    public LocalDateTime getStartTime() {
        return subtaskStartTime;
    }

    @Override
    public Duration getDuration() {
        return subtaskDuration;
    }

    @Override
    public LocalDateTime getEndTime() {
        return subtaskStartTime.plus(subtaskDuration);
    }

    public void setStatus(int status) {
        try {
            if (status == 1) this.subtaskStatus = Status.NEW;
            if (status == 2) this.subtaskStatus = Status.IN_PROGRESS;
            if (status == 3) this.subtaskStatus = Status.DONE;
            else throw new RuntimeException("status error");
        } catch (RuntimeException runtimeException) {
            runtimeException.getMessage();
        }
    }

    @Override
    public String forSaving() {
        String dur = subtaskDuration.toDays() + " " + subtaskDuration.toHoursPart() + " "
                + subtaskDuration.toMinutesPart();
        return subtaskId + "," + subtaskTypeOfTask + "," + subtaskName + "," + subtaskStatus + "," + subtaskDescription + "," + epicID + ","
                + DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm").format(subtaskStartTime) + "," + dur + "\n";
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + subtaskName + '\'' +
                ", description='" + subtaskDescription + '\'' +
                ", status=" + subtaskStatus +
                ", epicID=" + epicID +
                ", id=" + subtaskId +
                ", typeOfTask=" + subtaskTypeOfTask +
                ", startTime=" + subtaskStartTime +
                ", duration=" + subtaskDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicID == subtask.epicID && Objects.equals(subtaskName, subtask.subtaskName)
                && Objects.equals(subtaskDescription, subtask.subtaskDescription)
                && subtaskStatus == subtask.subtaskStatus && Objects.equals(subtaskId, subtask.subtaskId)
                && subtaskTypeOfTask == subtask.subtaskTypeOfTask
                && Objects.equals(subtaskStartTime, subtask.subtaskStartTime)
                && Objects.equals(subtaskDuration, subtask.subtaskDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskName, subtaskDescription, subtaskStatus, epicID, subtaskId,
                subtaskTypeOfTask, subtaskStartTime, subtaskDuration);
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