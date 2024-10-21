package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class InfoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Info(null));
    }

    @Test
    public void constructor_invalidInfo_throwsIllegalArgumentException() {
        String invalidInfo = "";
        assertThrows(IllegalArgumentException.class, () -> new Info(invalidInfo));
    }

    @Test
    public void isValidInfo() {
        // null info
        assertThrows(NullPointerException.class, () -> Info.isValidInfo(null));

        // inValid Info
        assertFalse(Info.isValidInfo("")); // empty string
        assertFalse(Info.isValidInfo(" ")); // spaces only

        // Valid Info
        assertTrue(Info.isValidInfo("meeting with jamie")); // alphabets only
        assertTrue(Info.isValidInfo("12345")); // numbers only
        assertTrue(Info.isValidInfo("3rd meeting with jamie")); // alphanumeric characters
        assertTrue(Info.isValidInfo("Sales Meeting")); // with capital letters
        assertTrue(Info.isValidInfo("Sales meeting with 10 developers from Malaysia")); // long names
    }

    @Test
    public void equals() {
        Info info = new Info("Valid Info");

        // same values -> returns true
        assertTrue(info.equals(new Info("Valid Info")));

        // same object -> returns true
        assertTrue(info.equals(info));

        // null -> returns false
        assertFalse(info.equals(null));

        // different types -> returns false
        assertFalse(info.equals(5.0f));

        // different values -> returns false
        assertFalse(info.equals(new Info("Other Valid Info")));
    }
}
