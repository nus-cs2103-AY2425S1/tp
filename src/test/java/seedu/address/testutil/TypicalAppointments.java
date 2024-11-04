package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APPOINTMENT_ALICE = new AppointmentBuilder()
            .withName("Alice Pauline")
            .withDate(LocalDate.of(2024, 11, 1))
            .withStartTime(LocalTime.of(9, 0))
            .withEndTime(LocalTime.of(10, 0))
            .build();

    public static final Appointment APPOINTMENT_BENSON = new AppointmentBuilder()
            .withName("Benson Meier")
            .withDate(LocalDate.of(2024, 11, 2))
            .withStartTime(LocalTime.of(10, 0))
            .withEndTime(LocalTime.of(11, 0))
            .build();

    public static final Appointment APPOINTMENT_CARL = new AppointmentBuilder()
            .withName("Carl Kurz")
            .withDate(LocalDate.of(2024, 11, 3))
            .withStartTime(LocalTime.of(11, 0))
            .withEndTime(LocalTime.of(12, 0))
            .build();

    public static final Appointment APPOINTMENT_DANIEL = new AppointmentBuilder()
            .withName("Daniel Meier")
            .withDate(LocalDate.of(2024, 11, 4))
            .withStartTime(LocalTime.of(12, 0))
            .withEndTime(LocalTime.of(13, 0))
            .build();

    public static final Appointment APPOINTMENT_ELLE = new AppointmentBuilder()
            .withName("Elle Meyer")
            .withDate(LocalDate.of(2024, 11, 5))
            .withStartTime(LocalTime.of(13, 0))
            .withEndTime(LocalTime.of(14, 0))
            .build();

    public static final Appointment APPOINTMENT_FIONA = new AppointmentBuilder()
            .withName("Fiona Kunz")
            .withDate(LocalDate.of(2024, 11, 6))
            .withStartTime(LocalTime.of(14, 0))
            .withEndTime(LocalTime.of(15, 0))
            .build();

    public static final Appointment APPOINTMENT_GEORGE = new AppointmentBuilder()
            .withName("George Best")
            .withDate(LocalDate.of(2024, 11, 7))
            .withStartTime(LocalTime.of(15, 0))
            .withEndTime(LocalTime.of(16, 0))
            .build();

    private TypicalAppointments() {} // prevents instantiation

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(APPOINTMENT_ALICE, APPOINTMENT_BENSON, APPOINTMENT_CARL,
                APPOINTMENT_DANIEL, APPOINTMENT_ELLE, APPOINTMENT_FIONA, APPOINTMENT_GEORGE));
    }
}
