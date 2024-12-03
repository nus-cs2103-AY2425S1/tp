package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RangeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Range<Integer>(null, 3));
        assertThrows(NullPointerException.class, () -> new Range<Integer>(3, null));
        assertThrows(NullPointerException.class, () -> new Range<Integer>(null, null));
    }

    @Test void toStringMethod() {
        Range<Integer> range = new Range<>(1, 2);
        String ecpected = "1 - 2";
        assertTrue(range.toString().equals(ecpected));
    }

    @Test
    public void equals() {
        Range range = new Range<Integer>(1, 2);

        // same object -> returns true
        assertTrue(range.equals(range));

        // same values -> returns true
        assertTrue(range.equals(new Range<Integer>(1, 2)));

        // null -> returns false
        assertFalse(range.equals(null));

        // different value -> returns false
        assertFalse(range.equals(new Range<>(1, 0)));

        // different types -> returns false
        assertFalse(range.equals(1));

    }
}
