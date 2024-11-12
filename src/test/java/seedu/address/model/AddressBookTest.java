package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.TEACHER_ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Teacher;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TeacherBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
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
        Teacher editedAlice = new TeacherBuilder(TEACHER_ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(TEACHER_ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(TEACHER_ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(TEACHER_ALICE);
        assertTrue(addressBook.hasPerson(TEACHER_ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(TEACHER_ALICE);
        Person editedAlice = new PersonBuilder(TEACHER_ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

    @Test
    public void markAttendance_marksAllStudentsAttendance_success() {
        // Arrange
        Student student1 = new StudentBuilder().withName("Student One")
            .withEmail("StudentOne@example.com").withPhone("12345678").build();
        Student student2 = new StudentBuilder().withName("Student Two")
            .withEmail("StudentTwo@example.com").withPhone("22345678").build();
        addressBook.addPerson(student1);
        addressBook.addPerson(student2);

        // Act
        addressBook.markAttendance();

        // Assert
        List<Person> persons = addressBook.getPersonList();
        persons.stream()
                .filter(person -> person instanceof Student)
                .forEach(person -> assertEquals(1, ((Student) person).getDaysAttended().getValue()));
    }

    @Test
    public void unmarkAttendance_unmarksStudentAttendance_success() throws CommandException {
        // Arrange
        Person student = new StudentBuilder().withName("Student One").build();
        student = student.withIncrementedAttendance();
        addressBook.addPerson(student);

        // Act
        addressBook.unmarkAttendance(student);

        // Assert
        assertEquals(0, addressBook.getPersonList().get(0).getDaysAttended().getValue());
    }

    @Test
    public void resetAttendance_resetsAllStudentsAttendance_success() {
        // Arrange
        Student student1 = new StudentBuilder().withName("Student One")
            .withEmail("StudentOne@example.com").withPhone("12345678").build();
        Student student2 = new StudentBuilder().withName("Student Two")
            .withEmail("StudentTwo@example.com").withPhone("22345678").build();
        addressBook.addPerson(student1);
        addressBook.addPerson(student2);

        // Mark attendance to set their attendance to 1
        addressBook.markAttendance();

        // Act
        addressBook.resetAttendance();

        // Assert
        List<Person> persons = addressBook.getPersonList();
        persons.stream()
                .filter(person -> person instanceof Student)
                .forEach(person -> assertEquals(0, ((Student) person).getDaysAttended().getValue()));
    }

}
