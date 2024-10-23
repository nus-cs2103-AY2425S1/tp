package seedu.address.model.person;

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
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "history"; // not a valid subject based on available subjects
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null subject
        //assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subjects
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("history")); // subject not in AVAILABLE_SUBJECTS
        assertFalse(Subject.isValidSubject("maths")); // misspelled subject

        // valid subjects
        assertTrue(Subject.isValidSubject("english")); // valid subject
        assertTrue(Subject.isValidSubject("math")); // valid subject
        assertTrue(Subject.isValidSubject("science")); // valid subject
        assertTrue(Subject.isValidSubject("ENGLISH")); // case insensitive
        assertTrue(Subject.isValidSubject("Math")); // case insensitive
        assertTrue(Subject.isValidSubject("sCieNCe")); // case insensitive
    }

    @Test
    public void equals() {
        Subject subject = new Subject("math");

        // same values -> returns true
        assertTrue(subject.equals(new Subject("math")));

        // same object -> returns true
        assertTrue(subject.equals(subject));

        // null -> returns false
        assertFalse(subject.equals(null));

        // different types -> returns false
        assertFalse(subject.equals(5.0f));

        // different values -> returns false
        assertFalse(subject.equals(new Subject("science")));
    }

    @Test
    public void toString_validSubject_correctStringRepresentation() {
        Subject subject = new Subject("English");
        assertTrue(subject.toString().equals("English "));
    }

    @Test
    public void hashCode_sameObjectAndValue_sameHashCode() {
        Subject subject1 = new Subject("science");
        Subject subject2 = new Subject("science");

        assertTrue(subject1.hashCode() == subject2.hashCode());
    }



}
