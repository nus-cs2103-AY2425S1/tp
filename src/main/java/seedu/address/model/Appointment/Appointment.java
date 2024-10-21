package seedu.address.model.Appointment;

import java.time.LocalDateTime;

import seedu.address.model.person.Nric;

public class Appointment {

    private final String Name;
    private final Nric nric;

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Appointment(String name, Nric nric, LocalDateTime startTime, LocalDateTime endTime) {
        this.Name = name;
        this.nric = nric;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    
}
