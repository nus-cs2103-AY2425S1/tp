package seedu.academyassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubjectName_throwsIllegalArgumentException() {
        String invalidSubjectName = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubjectName));
    }

    @Test
    public void isValidSubject() {
        // null subject name
        assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject names
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("Physics")); // not in enum
        assertFalse(Subject.isValidSubject("English123")); // alphanumeric

        // valid subject names
        assertTrue(Subject.isValidSubject("English"));
        assertTrue(Subject.isValidSubject("Math"));
        assertTrue(Subject.isValidSubject("Chinese"));
        assertTrue(Subject.isValidSubject("Science"));

        // case insensitive
        assertTrue(Subject.isValidSubject("ENGLISH"));
        assertTrue(Subject.isValidSubject("math"));
    }

    @Test
    public void equals() {
        Subject english = new Subject("English");

        // same object -> returns true
        assertTrue(english.equals(english));

        // same values -> returns true
        Subject englishCopy = new Subject("English");
        assertTrue(english.equals(englishCopy));

        // null -> returns false
        assertFalse(english.equals(null));

        // different type -> returns false
        assertFalse(english.equals(5));

        // different subject -> returns false
        Subject math = new Subject("Math");
        assertFalse(english.equals(math));
    }
}
