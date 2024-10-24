package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.shared.Date;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APPOINTMENT_1 = new Appointment(
            TypicalDoctors.ALICE, TypicalPatients.AMY, new Date("12-12-2023"), new Time("0900"));

    public static final Appointment APPOINTMENT_2 = new Appointment(
            TypicalDoctors.BOB, TypicalPatients.BENSON, new Date("13-12-2023"), new Time("1000"));

    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns a list of typical appointments to be used in tests.
     */
    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(
                APPOINTMENT_1, APPOINTMENT_2));
    }
}
