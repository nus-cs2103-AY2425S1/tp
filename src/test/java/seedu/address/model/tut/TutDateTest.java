package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalStudents;

public class TutDateTest {

    private TutDate tutDate;
    private Student studentAlice;

    @BeforeEach
    public void setUp() {
        studentAlice = new StudentBuilder(TypicalStudents.ALICE).build();
        tutDate = new TutDate(new Date()); // Initialize with the current date
    }

    @Test
    public void addStudent_success() {
        // Add a student and verify
        tutDate.add(studentAlice);
        assertTrue(tutDate.getStudents().contains(studentAlice), "The student should be added to the tutorial date.");
    }

    @Test
    public void addStudent_duplicate_noAdd() {
        // Add the same student twice and verify the size remains 1
        tutDate.add(studentAlice);
        int initialSize = tutDate.getStudents().size();

        tutDate.add(studentAlice);
        assertEquals(initialSize, tutDate.getStudents().size(), "The student should not be added twice.");
    }

    @Test
    public void getStudents_returnsUnmodifiableList() {
        // Get the list of students and check if it's unmodifiable
        tutDate.add(studentAlice);
        List<Student> students = tutDate.getStudents();

        assertTrue(students.contains(studentAlice), "The student should be in the list.");
        assertThrows(UnsupportedOperationException.class, () -> students.add(new StudentBuilder().build()),
                "The students list should be unmodifiable.");
    }

    @Test
    public void isValid_nullDate_returnsFalse() {
        // Create a TutDate with a null date
        TutDate invalidTutDate = new TutDate(null);
        assertFalse(invalidTutDate.isValid(), "A null date should be considered invalid.");
    }

    @Test
    public void isValid_nonNullDate_returnsTrue() {
        assertTrue(tutDate.isValid(), "A non-null date should be considered valid.");
    }

    @Test
    public void toString_returnsCorrectString() {
        // Ensure the toString method returns the correct string representation of the date
        String dateString = tutDate.getDate().toString();
        assertEquals(dateString, tutDate.toString(), "The string representation should match the date's string form.");
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        // Check that equals returns true for the same instance
        assertTrue(tutDate.equals(tutDate), "The same instance should be considered equal.");
    }

    @Test
    public void equals_differentInstanceSameDate_returnsTrue() {
        // Check that equals returns true for different instances with the same date
        Date date = new Date();
        TutDate anotherTutDate = new TutDate(date);
        TutDate sameDateTutDate = new TutDate(date);

        assertTrue(anotherTutDate.equals(sameDateTutDate), "Different instances with the same date should be equal.");
    }

    @Test
    public void equals_differentObjects_returnsFalse() {
        // Check that equals returns false for different objects
        Object notATutDate = new Object();
        assertFalse(tutDate.equals(notATutDate), "A different object type should not be equal to TutDate.");
    }

    @Test
    public void equals_null_returnsFalse() {
        // Check that equals returns false when compared to null
        assertFalse(tutDate.equals(null), "A TutDate should not be equal to null.");
    }

}
