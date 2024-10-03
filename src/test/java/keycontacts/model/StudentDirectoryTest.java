package keycontacts.model;

import static keycontacts.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import keycontacts.model.student.Student;
import keycontacts.model.student.exceptions.DuplicateStudentException;
import keycontacts.testutil.StudentBuilder;

public class StudentDirectoryTest {

    private final StudentDirectory studentDirectory = new StudentDirectory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), studentDirectory.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentDirectory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyStudentDirectory_replacesData() {
        StudentDirectory newData = getTypicalStudentDirectory();
        studentDirectory.resetData(newData);
        assertEquals(newData, studentDirectory);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        StudentDirectoryStub newData = new StudentDirectoryStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> studentDirectory.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentDirectory.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInStudentDirectory_returnsFalse() {
        assertFalse(studentDirectory.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInStudentDirectory_returnsTrue() {
        studentDirectory.addStudent(ALICE);
        assertTrue(studentDirectory.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInStudentDirectory_returnsTrue() {
        studentDirectory.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(studentDirectory.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> studentDirectory.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = StudentDirectory.class.getCanonicalName() + "{students=" + studentDirectory.getStudentList()
                + "}";
        assertEquals(expected, studentDirectory.toString());
    }

    /**
     * A stub ReadOnlyStudentDirectory whose students list can violate interface constraints.
     */
    private static class StudentDirectoryStub implements ReadOnlyStudentDirectory {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        StudentDirectoryStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
