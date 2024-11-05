package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class AppointmentTest {

    @Test
    public void equals() {
        try {
            Appointment appointment = new Appointment("Surgery",
                    ParserUtil.parseLocalDateTime("03-11-2024-12-00"),
                    ParserUtil.parseLocalDateTime("03-11-2024-14-00"));

            // same object -> returns true
            assertTrue(appointment.equals(appointment));

            // same values -> return true
            assertTrue(appointment.equals(new Appointment("Surgery",
                    ParserUtil.parseLocalDateTime("03-11-2024-12-00"),
                    ParserUtil.parseLocalDateTime("03-11-2024-14-00"))));

            // null -> return false
            assertFalse(appointment.equals(null));

            // different types -> return false
            assertFalse(appointment.equals(5.0f));

            // different description -> return false
            assertFalse(appointment.equals(new Appointment("Consultation",
                    ParserUtil.parseLocalDateTime("03-11-2024-12-00"),
                    ParserUtil.parseLocalDateTime("03-11-2024-14-00"))));

            // different dates -> return false
            assertFalse(appointment.equals(new Appointment("Surgery",
                    ParserUtil.parseLocalDateTime("03-11-2024-14-00"),
                    ParserUtil.parseLocalDateTime("03-11-2024-16-00"))));

        } catch (ParseException e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, null, null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = " ";
        assertThrows(IllegalArgumentException.class, ()-> new Appointment(invalidDescription,
                ParserUtil.parseLocalDateTime("03-11-2024-14-00"),
                ParserUtil.parseLocalDateTime("03-11-2024-16-00")));
    }

    @Test
    public void isValidDescription() {

        // null description
        assertThrows(NullPointerException.class, ()-> Appointment.isValidDescription(null));

        // invalid description
        assertFalse(Appointment.isValidDescription("")); // empty string
        assertFalse(Appointment.isValidDescription("      ")); // spaces only
        assertFalse(Appointment.isValidDescription("1VgV6jNGQ*mz!W7TTE51GX#RH81UOWM0G&t+7ssTtBuf9bpFYkn8XWKcdrAOss"
                + "EVSMRb#KqMvghtO9#V7bkkGkRXT2O2jG20tXB6d")); // 101 characters

        // valid description
        assertTrue(Appointment.isValidDescription("Surgery")); // alphabetic characters only
        assertTrue(Appointment.isValidDescription("L4-L5 spine check")); // with special characters and numbers
        // 50 characters
        assertTrue(Appointment.isValidDescription("VgV6jNGQ*mz!W7TTE51GX#RH81UOWM0Gadsfdkn8XWKcdrAOss"));
    }
}
