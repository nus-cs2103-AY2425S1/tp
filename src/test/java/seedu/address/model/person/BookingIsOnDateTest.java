package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BookingsCommand;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class BookingIsOnDateTest {

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BookingIsOnDate(null));
    }

    @Test
    public void equals() {
        LocalDate date1 = LocalDate.of(2024, 10, 5);
        LocalDate date2 = LocalDate.of(2024, 11, 5);


        BookingIsOnDate firstPredicate = new BookingIsOnDate(date1);
        BookingIsOnDate secondPredicate = new BookingIsOnDate(date2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookingIsOnDate firstPredicateCopy = new BookingIsOnDate(date1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_appointmentOnDate_returnsTrue() {
        BookingIsOnDate predicate = new BookingIsOnDate(LocalDate.of(2024, 5, 1));
        assertTrue(predicate.test(new PersonBuilder().withAppointment("01/05/2024 00:01").build()));

        // far future
        predicate = new BookingIsOnDate(LocalDate.of(9999, 5, 1));
        assertTrue(predicate.test(new PersonBuilder().withAppointment("01/05/9999 00:01").build()));
    }

    @Test
    public void test_appointmentNotOnDate_returnsFalse() {
        // Non matching date
        BookingIsOnDate predicate = new BookingIsOnDate(LocalDate.of(2024, 5, 2));
        assertFalse(predicate.test(new PersonBuilder().withAppointment("01/05/2024 00:01").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new BookingIsOnDate(LocalDate.of(2024, 5, 2));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").withAppointment("01/05/2024 00:01").build()));
    }

    @Test
    public void toStringMethod() {
        LocalDate date = LocalDate.of(2024, 10, 3);
        BookingIsOnDate predicate = new BookingIsOnDate(date);

        String expected = BookingIsOnDate.class.getCanonicalName() + "{date=" + date + "}";
        assertEquals(expected, predicate.toString());
    }
}
