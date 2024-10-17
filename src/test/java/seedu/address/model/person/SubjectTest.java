package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Check that the constructor throws a NullPointerException when passed null
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        // Check that the constructor throws an IllegalArgumentException when passed an invalid subject
        String invalidSubject = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null subject
        assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("^Math")); // starts with a special character
        assertFalse(Subject.isValidSubject("Science@123")); // contains special characters
        assertFalse(Subject.isValidSubject("Math 101#")); // contains special characters at the end

        // valid subject
        assertTrue(Subject.isValidSubject("Mathematics")); // alphabets only
        assertTrue(Subject.isValidSubject("Physics 101")); // alphanumeric with spaces
        assertTrue(Subject.isValidSubject("Computer Science")); // alphabets with spaces
        assertTrue(Subject.isValidSubject("Subject123")); // alphanumeric characters
    }

    @Test
    public void equals() {
        Subject subject = new Subject("Mathematics");

        // same values -> returns true
        assertTrue(subject.equals(new Subject("Mathematics")));

        // same object -> returns true
        assertTrue(subject.equals(subject));

        // null -> returns false
        assertFalse(subject.equals(null));

        // different types -> returns false
        assertFalse(subject.equals(5.0f));

        // different values -> returns false
        assertFalse(subject.equals(new Subject("Physics")));
    }

    @Test
    public void hashCode_test() {
        Subject subject1 = new Subject("Mathematics");
        Subject subject2 = new Subject("Mathematics");
        assertTrue(subject1.hashCode() == subject2.hashCode());

        Subject subject3 = new Subject("Physics");
        assertFalse(subject1.hashCode() == subject3.hashCode());
    }
}
