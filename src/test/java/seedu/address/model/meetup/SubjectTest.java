package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid name
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("^")); // only non-alphanumeric characters
        assertFalse(Subject.isValidSubject("meeting*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Subject.isValidSubject("meeting with jamie")); // alphabets only
        assertTrue(Subject.isValidSubject("12345")); // numbers only
        assertTrue(Subject.isValidSubject("3rd meeting with jamie")); // alphanumeric characters
        assertTrue(Subject.isValidSubject("Sales Meeting")); // with capital letters
        assertTrue(Subject.isValidSubject("Sales meeting with 10 developers from Malaysia")); // long names
    }

    @Test
    public void equals() {
        Subject name = new Subject("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Subject("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Subject("Other Valid Name")));
    }
}
