package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AppointmentContainsDatePredicateTest {

    @Test
    public void equals() {
        LocalDate firstDate = LocalDate.parse("23-10-2024", DATE_FORMATTER);
        LocalDate secondDate = LocalDate.parse("10-12-2023", DATE_FORMATTER);

        AppointmentContainsDatePredicate firstPredicate = new AppointmentContainsDatePredicate(firstDate);
        AppointmentContainsDatePredicate secondPredicate = new AppointmentContainsDatePredicate(secondDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AppointmentContainsDatePredicate firstPredicateCopy =
                new AppointmentContainsDatePredicate(firstDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords but same field -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_appointmentContainsDate_returnsTrue() {
        // start and end occurs in the given date
        AppointmentContainsDatePredicate predicate =
                new AppointmentContainsDatePredicate(LocalDate.parse("23-10-2024", DATE_FORMATTER));
        Person p = new PersonBuilder()
                .withAppointment("Surgery",
                        LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("23-10-2024-14-00", DATE_TIME_FORMATTER))
                .build();
        assertTrue(predicate.test(p));

        // only start occurs in the given date
        predicate = new AppointmentContainsDatePredicate(LocalDate.parse("23-10-2024", DATE_FORMATTER));
        p = new PersonBuilder()
                .withAppointment("Surgery",
                        LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("24-10-2024-12-00", DATE_TIME_FORMATTER))
                .build();
        assertTrue(predicate.test(p));

        // only end date occurs in the given date
        predicate = new AppointmentContainsDatePredicate(LocalDate.parse("24-10-2024", DATE_FORMATTER));
        p = new PersonBuilder()
                .withAppointment("Surgery",
                        LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("24-10-2024-12-00", DATE_TIME_FORMATTER))
                .build();
        assertTrue(predicate.test(p));

        // date is within the start and end date
        predicate = new AppointmentContainsDatePredicate(LocalDate.parse("24-10-2024", DATE_FORMATTER));
        p = new PersonBuilder()
                .withAppointment("Hospitalization",
                        LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("27-10-2024-12-00", DATE_TIME_FORMATTER))
                .build();
        assertTrue(predicate.test(p));

        // null is given as the date
        predicate = new AppointmentContainsDatePredicate(null);
        p = new PersonBuilder()
                .withAppointment("Surgery",
                        LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("24-10-2024-12-00", DATE_TIME_FORMATTER))
                .build();
        assertTrue(predicate.test(p));
    }

    @Test
    public void test_appointmentContainsDate_returnsFalse() {
        // Appointment does not exist with valid date input
        AppointmentContainsDatePredicate predicate =
                new AppointmentContainsDatePredicate(LocalDate.parse("23-10-2024", DATE_FORMATTER));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Appointment does not exist with null date input
        predicate = new AppointmentContainsDatePredicate(null);
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Date given is before the appointment
        predicate = new AppointmentContainsDatePredicate(LocalDate.parse("01-01-2023", DATE_FORMATTER));
        Person p = new PersonBuilder()
                .withAppointment("Surgery",
                        LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("23-10-2024-14-00", DATE_TIME_FORMATTER))
                .build();
        assertFalse(predicate.test(p));

        // Date given is after the appointment
        predicate = new AppointmentContainsDatePredicate(LocalDate.parse("01-01-2025", DATE_FORMATTER));
        assertFalse(predicate.test(p));
    }

    @Test
    public void toStringMethod() {
        LocalDate date = LocalDate.parse("23-10-2024", DATE_FORMATTER);
        AppointmentContainsDatePredicate predicate = new AppointmentContainsDatePredicate(date);

        String expected = AppointmentContainsDatePredicate.class.getCanonicalName() + "{date=" + "23-10-2024" + "}";
        assertEquals(expected, predicate.toString());
    }
}
