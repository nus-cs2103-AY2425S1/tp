package seedu.address.model.filteredappointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Patient;


public class FilteredAppointmentTest {
    private static Appt appt = new Appt(LocalDateTime.parse("2000-10-10T14:00"));
    private static Patient patient = AMY;
    private static Patient patient2 = BOB;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilteredAppointment(appt, null));
        assertThrows(NullPointerException.class, () -> new FilteredAppointment(null, patient));
        assertThrows(NullPointerException.class, () -> new FilteredAppointment(null, null));
    }

    @Test
    public void equals() {

        FilteredAppointment filteredAppointment = new FilteredAppointment(appt, patient);
        FilteredAppointment filteredAppointmentCopy = new FilteredAppointment(appt, patient);
        FilteredAppointment filteredAppointmentDifferentPatient = new FilteredAppointment(appt, patient2);

        // same values -> return true
        assertTrue(filteredAppointment.equals(filteredAppointmentCopy));

        // same object -> returns true
        assertTrue(filteredAppointment.equals(filteredAppointment));

        // null -> returns false
        assertFalse(filteredAppointment.equals(null));

        // different types -> returns false
        assertFalse(filteredAppointment.equals(5));

        // different values -> returns false
        assertFalse(filteredAppointment.equals(filteredAppointmentDifferentPatient));
    }


}
