package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

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

        assertEquals(today, dateOfCreation.getDateOfCreation());
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfCreation(null));
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
    public void isAfter_sameDate_returnsTrue() {
        LocalDate creationDate = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(creationDate);

        // isAfter should return true if the same date is passed
        assertTrue(dateOfCreation.isAfter(creationDate));
    }

    @Test
    public void of_validStringDate_success() throws IllegalValueException {
        String validDateString = "2024-01-01";
        DateOfCreation dateOfCreation = DateOfCreation.of(validDateString);

        assertEquals(LocalDate.of(2024, 1, 1), dateOfCreation.getDateOfCreation());
    }

    @Test
    public void of_invalidStringDate_throwsIllegalValueException() {
        String invalidDateString = "01/01/2024"; // Invalid format

        // Ensure that an IllegalValueException is thrown
        assertThrows(IllegalValueException.class, () -> DateOfCreation.of(invalidDateString));
    }

    @Test
    public void of_emptyString_throwsIllegalValueException() {
        String emptyDateString = ""; // Empty date string

        // Ensure that an IllegalValueException is thrown
        assertThrows(IllegalValueException.class, () -> DateOfCreation.of(emptyDateString));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(date);

        assertEquals(dateOfCreation, dateOfCreation);
    }

    @Test
    public void equals_differentObjectSameDate_returnsTrue() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation1 = new DateOfCreation(date);
        DateOfCreation dateOfCreation2 = new DateOfCreation(date);

        assertEquals(dateOfCreation1, dateOfCreation2);
    }

    @Test
    public void equals_differentObjectDifferentDate_returnsFalse() {
        DateOfCreation dateOfCreation1 = new DateOfCreation(LocalDate.of(2024, 1, 1));
        DateOfCreation dateOfCreation2 = new DateOfCreation(LocalDate.of(2024, 2, 1));

        assertNotEquals(dateOfCreation1, dateOfCreation2);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        DateOfCreation dateOfCreation = new DateOfCreation(LocalDate.of(2024, 1, 1));

        // Null should return false
        assertNotEquals(dateOfCreation, null);
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        DateOfCreation dateOfCreation = new DateOfCreation(LocalDate.of(2024, 1, 1));

        // A different class should return false
        assertNotEquals(dateOfCreation, "Some String");
    }

    @Test
    public void toString_validDate_success() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        DateOfCreation dateOfCreation = new DateOfCreation(date);

        assertEquals("2024-01-01", dateOfCreation.toString());
    }

    @Test
    public void toString_currentDate_success() {
        DateOfCreation dateOfCreation = new DateOfCreation();
        LocalDate today = LocalDate.now();

        assertEquals(today.toString(), dateOfCreation.toString());
    }
}
