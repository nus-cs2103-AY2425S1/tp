package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    private static final String VALID_DATE = "121224";
    private static final String VALID_FROM = "0800";
    private static final String VALID_TO = "1000";
    private static final String OTHER_DATE = "131224";
    private static final String OTHER_FROM = "0900";
    private static final String OTHER_TO = "1100";


    @Test
    public void equals() {
        Appointment appointment = new Appointment(new Date(VALID_DATE), new From(VALID_FROM), new To(VALID_TO));

        // null value -> return false
        assertFalse(appointment.equals(null));

        // different type -> return false
        assertFalse(appointment.equals(5));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // same values -> returns true
        Appointment otherAppointment = new Appointment(new Date(VALID_DATE), new From(VALID_FROM), new To(VALID_TO));
        assertTrue(appointment.equals(otherAppointment));

        // different date -> returns false
        otherAppointment = new Appointment(new Date(OTHER_DATE), new From(VALID_FROM), new To(VALID_TO));
        assertFalse(appointment.equals(otherAppointment));

        // different from -> returns false
        otherAppointment = new Appointment(new Date(VALID_DATE), new From(OTHER_FROM), new To(VALID_TO));
        assertFalse(appointment.equals(otherAppointment));

        // different to -> returns false
        otherAppointment = new Appointment(new Date(VALID_DATE), new From(VALID_FROM), new To(OTHER_TO));
        assertFalse(appointment.equals(otherAppointment));

    }
}
