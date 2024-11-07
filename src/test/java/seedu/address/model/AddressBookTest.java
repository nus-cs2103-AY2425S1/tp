package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.CHARLIE;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @BeforeEach
    public void setUp() {
        addressBook.addStudent(BOB);
    }

    @Test
    public void constructor() {
        // Default constructor
        AddressBook emptyAddressBook = new AddressBook();

        // Copy constructor
        AddressBook copyAddressBook = new AddressBook(addressBook);
        assertEquals(Collections.singletonList(BOB), copyAddressBook.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getStudentList().remove(0));
    }

    @Test
    public void setStudent_targetStudentInList_success() {
        addressBook.setStudent(BOB, DIDDY);
        assertEquals(DIDDY, addressBook.getStudentList().get(0));
    }

    @Test
    public void removeStudent_studentInList_success() {
        assertEquals(0, addressBook.removeStudent(BOB));
        assertEquals(Collections.emptyList(), addressBook.getStudentList());
    }

    @Test
    public void hasStudent() {
        assertTrue(addressBook.hasStudent(BOB));
        assertFalse(addressBook.hasStudent(DIDDY));
    }


    @Test
    public void addStudent() {
        // addStudent with one parameter Student
        addressBook.addStudent(DIDDY);
        assertTrue(addressBook.hasStudent(DIDDY));

        // addStudent with two parameters index and Student
        addressBook.addStudent(0, HUGH);
        assertEquals(HUGH, addressBook.getStudentList().get(0));
        assertThrows(DuplicateStudentException.class, () -> addressBook.addStudent(0, HUGH));
        assertThrows(IndexOutOfBoundsException.class, () -> addressBook.addStudent(10, CHARLIE));
    }

    @Test
    public void setStudent() {
        addressBook.setStudent(BOB, DIDDY);
        assertEquals(DIDDY, addressBook.getStudentList().get(0));

        assertThrows(StudentNotFoundException.class, () -> addressBook.setStudent(BOB, HUGH));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentsNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> addressBook.setStudent(HUGH, DIDDY));
    }


    @Test
    public void removeStudent_studentNotInList_throwsStudentNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> addressBook.removeStudent(HUGH));
    }

    @Test
    public void removeAllStudents() {
        ObservableList<Student> deletedStudents = addressBook.removeAllStudents();
        assertEquals(0, addressBook.getStudentList().size());
        assertEquals(1, deletedStudents.size());
    }

    @Test
    public void replaceStudentList() {
        ObservableList<Student> newStudentList = FXCollections.observableArrayList();
        newStudentList.add(DIDDY);
        newStudentList.add(HUGH);
        addressBook.replaceStudentList(newStudentList);
        assertEquals(newStudentList, addressBook.getStudentList());
    }

    @Test
    public void toStringMethod() {
        // Empty AddressBook
        AddressBook emptyAddressBook = new AddressBook();
        String expected = AddressBook.class.getCanonicalName() + "{students=[]}";
        assertEquals(expected, emptyAddressBook.toString());

        // AddressBook with students only
        AddressBook addressBookWithStudents = new AddressBook();
        addressBookWithStudents.addStudent(BOB);
        expected = AddressBook.class.getCanonicalName() + "{students=" + addressBookWithStudents.getStudentList() + "}";
        assertEquals(expected, addressBookWithStudents.toString());

    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(addressBook.equals(addressBook));

        // null -> returns false
        assertFalse(addressBook.equals(null));

        // different types -> returns false
        assertFalse(addressBook.equals(5));

        // different student -> returns false
        AddressBook differentStudentAddressBook = new AddressBook();
        assertFalse(addressBook.equals(differentStudentAddressBook));

        // different student -> returns false
        AddressBook differentPersonAddressBook = new AddressBook();
        differentPersonAddressBook.addStudent(HUGH);
        assertFalse(addressBook.equals(differentPersonAddressBook));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
