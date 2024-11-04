package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2024, 11, 1);
    public static final LocalTime DEFAULT_START_TIME = LocalTime.of(9, 0);
    public static final LocalTime DEFAULT_END_TIME = LocalTime.of(10, 0);

    private Name name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Creates an {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        name = new Name(DEFAULT_NAME);
        date = DEFAULT_DATE;
        startTime = DEFAULT_START_TIME;
        endTime = DEFAULT_END_TIME;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        name = appointmentToCopy.name();
        date = appointmentToCopy.date();
        startTime = appointmentToCopy.startTime();
        endTime = appointmentToCopy.endTime();
    }

    /**
     * Sets the {@code Name} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Builds the {@code Appointment}.
     */
    public Appointment build() {
        return new Appointment(name, date, startTime, endTime);
    }
}
