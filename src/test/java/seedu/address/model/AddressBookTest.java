package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.CHARLIE;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @BeforeEach
    public void setUp() {
        addressBook.addPerson(ALICE);
        addressBook.addStudent(BOB);
    }

    @Test
    public void constructor() {
        // Default constructor
        AddressBook emptyAddressBook = new AddressBook();
        assertEquals(Collections.emptyList(), emptyAddressBook.getPersonList());

        // Copy constructor
        AddressBook copyAddressBook = new AddressBook(addressBook);
        assertEquals(Collections.singletonList(ALICE), copyAddressBook.getPersonList());
        assertEquals(Collections.singletonList(BOB), copyAddressBook.getStudentList());
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
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
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
    public void setPerson_targetPersonInList_success() {
        addressBook.setPerson(ALICE, BENSON);
        assertEquals(BENSON, addressBook.getPersonList().get(0));
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
    public void hasPerson() {
        assertTrue(addressBook.hasPerson(ALICE));
        assertFalse(addressBook.hasPerson(BENSON));
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
    public void removePerson_personInList_success() {
        addressBook.removePerson(ALICE);
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentsNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> addressBook.setStudent(HUGH, DIDDY));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonsNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.setPerson(BENSON, CARL));
    }

    @Test
    public void removeStudent_studentNotInList_throwsStudentNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> addressBook.removeStudent(HUGH));
    }

    @Test
    public void removePerson_personNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removePerson(CARL));
    }

    @Test
    public void toStringMethod() {
        // Empty AddressBook
        AddressBook emptyAddressBook = new AddressBook();
        String expected = AddressBook.class.getCanonicalName() + "{persons=[]}";
        assertEquals(expected, emptyAddressBook.toString());

        // AddressBook with persons only
        AddressBook addressBookWithPersons = new AddressBook();
        addressBookWithPersons.addPerson(ALICE);
        expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBookWithPersons.getPersonList() + "}";
        assertEquals(expected, addressBookWithPersons.toString());

        // AddressBook with students only
        AddressBook addressBookWithStudents = new AddressBook();
        addressBookWithStudents.addStudent(BOB);
        expected = AddressBook.class.getCanonicalName() + "{students=" + addressBookWithStudents.getStudentList() + "}";
        assertEquals(expected, addressBookWithStudents.toString());

        // AddressBook with both persons and students
        expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList()
                + ", students=" + addressBook.getStudentList() + "}";
        assertEquals(expected, addressBook.toString());
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
        differentStudentAddressBook.addPerson(ALICE);
        assertFalse(addressBook.equals(differentStudentAddressBook));

        // different person -> returns false
        AddressBook differentPersonAddressBook = new AddressBook();
        differentPersonAddressBook.addStudent(BOB);
        assertFalse(addressBook.equals(differentPersonAddressBook));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
