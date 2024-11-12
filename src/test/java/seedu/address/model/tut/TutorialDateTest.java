package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalStudents;

public class TutorialDateTest {
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
        tutDate.add(studentAlice.getStudentId());
        assertTrue(tutDate.getStudentIDs().contains(studentAlice.getStudentId()),
                "The student should be added to the tutorial date.");
    }

    @Test
    public void addStudent_duplicate_noAdd() {
        // Add the same student twice and verify the size remains 1
        tutDate.add(studentAlice.getStudentId());
        int initialSize = tutDate.getStudentIDs().size();

        tutDate.add(studentAlice.getStudentId());
        assertEquals(initialSize, tutDate.getStudentIDs().size(), "The student should not be added twice.");
    }

    @Test
    public void getStudents_returnsUnmodifiableList() {
        // Get the list of students and check if it's unmodifiable
        tutDate.add(studentAlice.getStudentId());
        Set<StudentId> students = tutDate.getStudentIDs();

        assertTrue(students.contains(studentAlice.getStudentId()), "The student should be in the list.");
        assertThrows(UnsupportedOperationException.class, () -> students
                                                            .add(new StudentBuilder().build().getStudentId()),
                "The students list should be unmodifiable.");
    }

    @Test
    public void isValid_nullDate_returnsFalse() {
        // Create a TutDate with a null date
        assertThrows(NullPointerException.class, () -> new TutDate(null));
    }

    @Test
    public void isValid_nonNullDate_returnsTrue() {
        assertTrue(tutDate.isValid(), "A non-null date should be considered valid.");
    }
    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutDate(null));
    }

    @Test
    public void constructor_validDate_success() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        assertEquals(date, tutDate.getDate());
        assertTrue(tutDate.getStudentIDs().isEmpty());
    }

    @Test
    public void add_validStudent_success() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        Student student = new StudentBuilder(TypicalStudents.ALICE).build();
        tutDate.add(student.getStudentId());
        assertTrue(tutDate.getStudentIDs().contains(student.getStudentId()));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        assertThrows(NullPointerException.class, () -> tutDate.add(null));
    }

    @Test
    public void add_duplicateStudent_setRemainsUnique() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        Student student = new StudentBuilder(TypicalStudents.ALICE).build();
        tutDate.add(student.getStudentId());
        tutDate.add(student.getStudentId()); // Attempt to add duplicate
        assertEquals(1, tutDate.getStudentIDs().size());
    }

    @Test
    public void getStudents_returnsUnmodifiableSet() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        Set<StudentId> students = tutDate.getStudentIDs();
        assertThrows(UnsupportedOperationException.class, () ->
                students.add(new StudentBuilder().build().getStudentId()));
    }

    @Test
    public void getDate_returnsCorrectDate() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        assertEquals(date, tutDate.getDate());
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
    @Test
    public void toString_returnsCorrectString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        assertEquals(sdf.format(date), tutDate.toString());
    }
}

