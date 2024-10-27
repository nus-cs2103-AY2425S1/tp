package seedu.address.model.appointmentdatefilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Address;

/**
 * Represents a AppointmentDate object used for filtering patients based on their appointment dates and healthservice;
 */
public class AppointmentDateFilterTest {

    @Test
    public void constructor_nullEndDate_throwsNullPointerException() {
        LocalDate exampleDate = LocalDate.parse("2000-10-10");
        HealthService exampleService = new HealthService("blood test");
        assertThrows(NullPointerException.class, () ->
            new AppointmentDateFilter(null, null, null));
        assertThrows(NullPointerException.class, () ->
                new AppointmentDateFilter(exampleDate, null, null));
        assertThrows(NullPointerException.class, () ->
                new AppointmentDateFilter(null, null, exampleService));
        assertThrows(NullPointerException.class, () ->
                new AppointmentDateFilter(exampleDate, null, exampleService));
    }

    @Test
    public void isValidAppointmentDateFilter() {

        assertThrows(NullPointerException.class, () -> AppointmentDateFilter.isValidDate(null));

        assertTrue(AppointmentDateFilter.isValidDate("2000-10-10"));
        assertFalse(AppointmentDateFilter.isValidDate("2000/10/10"));
        assertFalse(AppointmentDateFilter.isValidDate(""));

        LocalDate startDate = LocalDate.parse("2000-10-10");
        LocalDate invalidEndDate = LocalDate.parse("1990-10-10");
        LocalDate validEndDate = LocalDate.parse("2000-10-11");

        assertThrows(NullPointerException.class, () -> AppointmentDateFilter.isValidStartAndEndDate(null, null));

        assertFalse(AppointmentDateFilter.isValidStartAndEndDate(startDate, invalidEndDate));
        assertTrue(AppointmentDateFilter.isValidStartAndEndDate(startDate, validEndDate));
    }
}
