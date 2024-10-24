package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.HOON;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Days;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.testutil.StudentBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInAddressBook_returnsTrue() {
        addressBook.addStudent(ALICE);
        assertTrue(addressBook.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(addressBook.hasStudent(editedAlice));
    }

    @Test
    public void countClashes_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.countClashes(null));
    }

    @Test
    public void countClashes_noClashes_returnsZero() {
        addressBook.addStudent(ALICE);
        assertEquals(addressBook.countClashes(BOB), 0);
    }

    @Test
    public void countClashes_someClashes_returnsCorrectCount() {
        addressBook.addStudent(ALICE);
        assertEquals(addressBook.countClashes(HOON), 1);
    }

    @Test
    public void getClashingStudents_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.getClashingStudents(null));
    }

    @Test
    public void getClashingStudents_noClashes_returnsEmptyList() {
        addressBook.addStudent(ALICE);
        assertEquals(addressBook.getClashingStudents(BOB), new ArrayList<>());
    }

    @Test
    public void getClashingStudents_someClashes_returnsCorrectList() {
        addressBook.addStudent(ALICE);
        ArrayList<Student> testList = new ArrayList<>();
        testList.add(ALICE);
        assertEquals(addressBook.getClashingStudents(HOON), testList);
    }

    @Test
    public void getScheduledStudents_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.getScheduledStudents(null));
    }

    @Test
    public void getScheduledStudents_noLessonScheduled_returnsEmptyList() {
        addressBook.addStudent(ALICE);
        assertEquals(addressBook.getScheduledStudents(Days.MONDAY), new ArrayList<>());
    }

    @Test
    public void getScheduledStudents_someScheduled_returnsCorrectList() {
        addressBook.addStudent(ALICE);
        ArrayList<Student> testList = new ArrayList<>();
        testList.add(ALICE);
        assertEquals(addressBook.getScheduledStudents(Days.SUNDAY), testList);
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{students=" + addressBook.getStudentList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose students list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        AddressBookStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
