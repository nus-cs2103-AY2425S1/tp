package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null subject
        assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subjects
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("invalid")); // invalid subject

        // valid subjects
        assertTrue(Subject.isValidSubject("economics"));
        assertTrue(Subject.isValidSubject("Biology"));
        assertTrue(Subject.isValidSubject("eNglish"));
        assertTrue(Subject.isValidSubject("math"));
    }

    @Test
    public void equals() {
        Subject subject = new Subject("economics");

        // same values -> returns true
        assertTrue(subject.equals(new Subject("economics")));

        // same object -> returns true
        assertTrue(subject.equals(subject));

        // null -> returns false
        assertFalse(subject.equals(null));

        // different types -> returns false
        assertFalse(subject.equals(5.0f));

        // different values -> returns false
        assertFalse(subject.equals(new Subject("biology")));
    }

    @Test
    public void hashCodeTest() {
        Subject subject = new Subject("economics");
        Subject otherSubject = new Subject("economics");

        Subject differentSubject = new Subject("biology");
        assertEquals(subject.hashCode(), subject.hashCode());

        assertEquals(subject.hashCode(), otherSubject.hashCode());

        assertNotEquals(subject.hashCode(), differentSubject.hashCode());
    }
}
