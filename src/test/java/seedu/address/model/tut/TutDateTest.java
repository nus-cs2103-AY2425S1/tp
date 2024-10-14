package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalStudents;

public class TutDateTest {

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutDate(null));
    }

    @Test
    public void constructor_validDate_success() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        assertEquals(date, tutDate.getDate());
        assertTrue(tutDate.getStudents().isEmpty());
    }

    @Test
    public void add_validStudent_success() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        Student student = new StudentBuilder(TypicalStudents.ALICE).build();
        tutDate.add(student);
        assertTrue(tutDate.getStudents().contains(student));
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
        tutDate.add(student);
        tutDate.add(student); // Attempt to add duplicate
        assertEquals(1, tutDate.getStudents().size());
    }

    @Test
    public void getStudents_returnsUnmodifiableSet() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        Set<Student> students = tutDate.getStudents();
        assertThrows(UnsupportedOperationException.class, () -> students.add(new StudentBuilder().build()));
    }

    @Test
    public void getDate_returnsCorrectDate() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        assertEquals(date, tutDate.getDate());
    }

    @Test
    public void toString_returnsCorrectString() {
        Date date = new Date();
        TutDate tutDate = new TutDate(date);
        assertEquals(date.toString(), tutDate.toString());
    }
}

