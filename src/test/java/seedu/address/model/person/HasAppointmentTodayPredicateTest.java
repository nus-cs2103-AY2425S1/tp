package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class HasAppointmentTodayPredicateTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy");

    @Test
    public void equals() {
        HasAppointmentTodayPredicate firstPredicate = new HasAppointmentTodayPredicate();
        HasAppointmentTodayPredicate secondPredicate = new HasAppointmentTodayPredicate();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        HasAppointmentTodayPredicate firstPredicateCopy = new HasAppointmentTodayPredicate();
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_hasAppointmentToday_returnsTrue() {
        // Today's date in "dd-MM-yy" format
        String todayDate = LocalDate.now().format(DATE_FORMATTER);

        // Person with an appointment scheduled for today
        Person personWithTodayAppointment = new PersonBuilder()
                .withAppointment(todayDate, "0800", "0900")
                .buildBuyer();

        HasAppointmentTodayPredicate predicate = new HasAppointmentTodayPredicate();

        // Test should return true because the person has an appointment today
        assertTrue(predicate.test(personWithTodayAppointment));
    }

    @Test
    public void test_doesNotHaveAppointmentToday_returnsFalse() {
        // Yesterday's date in "dd-MM-yy" format
        String yesterdayDate = LocalDate.now().minusDays(1).format(DATE_FORMATTER);

        // Person with an appointment scheduled for yesterday
        Person personWithYesterdayAppointment = new PersonBuilder()
                .withAppointment(yesterdayDate, "0800", "0900")
                .buildBuyer();

        HasAppointmentTodayPredicate predicate = new HasAppointmentTodayPredicate();

        // Test should return false because the person does not have an appointment today
        assertFalse(predicate.test(personWithYesterdayAppointment));
    }

    @Test
    public void test_noAppointment_returnsFalse() {
        // Person with an appointment scheduled on a future date
        String futureDate = LocalDate.now().plusDays(1).format(DATE_FORMATTER);
        Person personWithoutTodayAppointment = new PersonBuilder()
                .withAppointment(futureDate, "0800", "0900")
                .buildBuyer();

        HasAppointmentTodayPredicate predicate = new HasAppointmentTodayPredicate();

        // Test should return false because the person does not have an appointment today
        assertFalse(predicate.test(personWithoutTodayAppointment));
    }

    @Test
    public void toStringMethod() {
        HasAppointmentTodayPredicate predicate = new HasAppointmentTodayPredicate();

        String expected = HasAppointmentTodayPredicate.class.getCanonicalName() + "{}";
        assertEquals(expected, predicate.toString());
    }
}
