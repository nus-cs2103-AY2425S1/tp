package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@code DateOfCreation}.
 */
public class DateOfCreationTest {

    @Test
    public void constructor_withValidDate_success() {
        LocalDate validDate = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(validDate);

        assertEquals(validDate, dateOfCreation.getDateOfCreation());
    }

    @Test
    public void constructor_withCurrentDate_success() {
        DateOfCreation dateOfCreation = new DateOfCreation();
        LocalDate today = LocalDate.now();

        // The date of creation should match today's date
        assertEquals(today, dateOfCreation.getDateOfCreation());
    }

    @Test
    public void isAfter_dateAfterCreation_returnsTrue() {
        LocalDate creationDate = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(creationDate);
        LocalDate afterDate = LocalDate.of(2024, 2, 1);

        assertTrue(dateOfCreation.isAfter(afterDate));
    }

    @Test
    public void isAfter_dateBeforeCreation_returnsFalse() {
        LocalDate creationDate = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(creationDate);
        LocalDate beforeDate = LocalDate.of(2023, 12, 31);

        assertFalse(dateOfCreation.isAfter(beforeDate));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(date);

        // Same object should return true
        assertEquals(dateOfCreation, dateOfCreation);
    }

    @Test
    public void equals_differentObjectSameDate_returnsTrue() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation1 = new DateOfCreation(date);
        DateOfCreation dateOfCreation2 = new DateOfCreation(date);

        // Different objects but same date should return true
        assertEquals(dateOfCreation1, dateOfCreation2);
    }

    @Test
    public void equals_differentObjectDifferentDate_returnsFalse() {
        DateOfCreation dateOfCreation1 = new DateOfCreation(LocalDate.of(2024, 1, 1));
        DateOfCreation dateOfCreation2 = new DateOfCreation(LocalDate.of(2024, 2, 1));

        // Different dates should return false
        assertNotEquals(dateOfCreation1, dateOfCreation2);
    }

    @Test
    public void toString_validDate_success() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(date);

        assertEquals("2024-01-01", dateOfCreation.toString());
    }
}
