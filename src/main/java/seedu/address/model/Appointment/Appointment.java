package seedu.address.model.Appointment;

import java.time.LocalDateTime;

import seedu.address.model.person.Nric;

public class Appointment {

    private final String Name;
    private final Nric nric;

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private boolean isCompleted = false;

    public Appointment(String name, Nric nric, LocalDateTime startTime, LocalDateTime endTime) {
        this.Name = name;
        this.nric = nric;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return Name;
    }

    public Nric getNric() {
        return nric;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }   

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        isCompleted = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment: " + Name + "\n");
        sb.append("Nric: " + nric.maskNric() + "\n");
        sb.append("Start Time: " + startTime + "\n");
        sb.append("End Time: " + endTime + "\n");
        return sb.toString();
    }


    
}
