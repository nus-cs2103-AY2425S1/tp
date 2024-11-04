package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_futureDate_throwsIllegalArgumentException() {
        LocalDate futureDate = LocalDate.of(3000, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(futureDate));
    }

    @Test
    public void constructor_validDate_shouldSucceed() {
        assertDoesNotThrow(() -> new DateOfBirth(LocalDate.of(2000, 1, 1)));
        assertDoesNotThrow(() -> new DateOfBirth(LocalDate.now()));
    }

    @Test
    public void isValidDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDate(null));
    }

    @Test
    public void isValidDate_validDate_returnsTrue() {
        LocalDate validDate1 = LocalDate.of(2000, 1, 1);
        LocalDate validDate2 = LocalDate.of(1998, 12, 31);
        LocalDate now = LocalDate.now();

        assertTrue(DateOfBirth.isValidDate(validDate1));
        assertTrue(DateOfBirth.isValidDate(validDate2));
        assertTrue(DateOfBirth.isValidDate(now));
    }

    @Test
    public void isValidDate_futureDate_returnsFalse() {
        LocalDate futureDate = LocalDate.of(3000, 1, 1);
        assertFalse(DateOfBirth.isValidDate(futureDate));
    }

    @Test
    public void equals() {
        DateOfBirth dateOfBirth1 = new DateOfBirth(LocalDate.of(2000, 1, 1));
        DateOfBirth dateOfBirth2 = new DateOfBirth(LocalDate.of(2024, 1, 1));

        // same object
        assertEquals(dateOfBirth1, dateOfBirth1);

        // same date
        assertEquals(dateOfBirth1, new DateOfBirth(LocalDate.of(2000, 1, 1)));

        // different dates
        assertNotEquals(dateOfBirth1, dateOfBirth2);

        // different types
        assertNotEquals(dateOfBirth2, dateOfBirth2.getValue());

        // null
        assertNotEquals(dateOfBirth2, null);
    }
}
