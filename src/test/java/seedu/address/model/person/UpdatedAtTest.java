package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class UpdatedAtTest {

    private static final LocalDateTime VALID_DATE_TIME_1 = LocalDateTime.of(2000, 1, 1, 1, 0, 0);

    private static final LocalDateTime VALID_DATE_TIME_2 = LocalDateTime.of(2024, 1, 1, 1, 0, 0);

    private static final LocalDateTime FUTURE_DATE_TIME = LocalDateTime.of(9999, 1, 1, 1, 0, 0);
    @Test
    public void constructor_validDateTime_shouldNotThrow() {
        assertDoesNotThrow(() -> new UpdatedAt(VALID_DATE_TIME_1));
        assertDoesNotThrow(() -> new UpdatedAt(VALID_DATE_TIME_2));
    }

    @Test
    public void constructor_now_shouldNotThrow() {
        assertDoesNotThrow(() -> new UpdatedAt(LocalDateTime.now()));
    }

    @Test
    public void constructor_futureDateTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new UpdatedAt(FUTURE_DATE_TIME));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdatedAt(null));
    }

    @Test
    public void isValidUpdatedAt_validDateTime_returnsTrue() {
        assertTrue(UpdatedAt.isValidUpdatedAt(VALID_DATE_TIME_1));
        assertTrue(UpdatedAt.isValidUpdatedAt(VALID_DATE_TIME_2));
    }

    @Test
    public void isValidUpdatedAt_now_returnsTrue() {
        assertTrue(UpdatedAt.isValidUpdatedAt(LocalDateTime.now()));
    }

    @Test
    public void isValidUpdatedAt_futureDateTime_returnsFalse() {
        assertFalse(UpdatedAt.isValidUpdatedAt(FUTURE_DATE_TIME));
    }

    @Test
    public void equals() {
        assertEquals(new UpdatedAt(VALID_DATE_TIME_1), new UpdatedAt(VALID_DATE_TIME_1));
        final UpdatedAt instanceTestObject = new UpdatedAt(VALID_DATE_TIME_1);
        assertEquals(instanceTestObject, instanceTestObject);

        assertNotEquals(new UpdatedAt(VALID_DATE_TIME_1), null);
        assertNotEquals(new UpdatedAt(VALID_DATE_TIME_1), new UpdatedAt(VALID_DATE_TIME_2));
        assertNotEquals(new UpdatedAt(VALID_DATE_TIME_1), UpdatedAt.now());
    }
}
