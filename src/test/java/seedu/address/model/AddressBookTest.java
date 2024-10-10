package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUT_01;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TutUtil.TUT_SAMPLE;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tut.Tut;
import seedu.address.model.tut.exceptions.TutNoFoundException;
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
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
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
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasStudent(editedAlice));
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

    @Test
    public void getTutorialList_add() {
        List<Tut> expectedTutorial = new ArrayList<>();
        expectedTutorial.add(TUT_SAMPLE);
        addressBook.addTutorial(TUT_SAMPLE);
        List<Tut> actualTutorial = addressBook.getTutorials();
        assertTrue(actualTutorial.equals(expectedTutorial));
    }

    @Test
    public void getAddressBook_addTutorial() {
        addressBook.resetData(getTypicalAddressBook());
        AddressBook addressBookSample = getTypicalAddressBook();
        addressBookSample.addTutorial(TUT_SAMPLE);
        addressBook.addTutorial(TUT_SAMPLE);
        assertTrue(addressBook.equals(addressBookSample));
    }
    @Test
    public void getTutorialList_addStudent() {
        List<Tut> expectedTutorial = new ArrayList<>();
        Tut TutSample2 = new Tut(TUT_NAME, TUT_01);
        TUT_SAMPLE.add(ALICE);
        TutSample2.add(ALICE);
        expectedTutorial.add(TUT_SAMPLE);
        addressBook.addTutorial(TutSample2);
        List<Tut> actualTutorial = addressBook.getTutorials();
        assertTrue(actualTutorial.equals(expectedTutorial));
    }
    @Test
    public void getAddressBook_addStudent() {
        addressBook.resetData(getTypicalAddressBook());
        AddressBook addressBookSample = getTypicalAddressBook();
        List<Tut> expectedTutorial = new ArrayList<>();
        Tut TutSample2 = new Tut(TUT_NAME, TUT_01);
        TUT_SAMPLE.add(ALICE);
        TutSample2.add(ALICE);
        expectedTutorial.add(TUT_SAMPLE);
        addressBook.addTutorial(TutSample2);
        addressBookSample.addTutorial(TutSample2);
        assertTrue(addressBook.equals(addressBookSample));
    }

    @Test
    public void assignStudent_validStudentAndValidTutorial_success() {
        addressBook.addStudent(ALICE);
        addressBook.addTutorial(TUT_SAMPLE);
        addressBook.assignStudent(ALICE, TUT_SAMPLE);

        // Check if ALICE has been successfully assigned to TUT_SAMPLE
        assertTrue(TUT_SAMPLE.getStudents().contains(ALICE), "ALICE should be assigned to the tutorial TUT_SAMPLE.");
    }

    @Test
    public void assignStudent_studentNotInAddressBook_throwsStudentNotFoundException() {
        // Add TUT_SAMPLE to AddressBook but not ALICE
        addressBook.addTutorial(TUT_SAMPLE);

        // Expect StudentNotFoundException when trying to assign a non-existent student
        assertThrows(StudentNotFoundException.class, () -> addressBook.assignStudent(ALICE, TUT_SAMPLE));
    }

    @Test
    public void assignStudent_tutorialNotInAddressBook_throwsTutNoFoundException() {
        // Add ALICE to AddressBook but not TUT_SAMPLE
        addressBook.addStudent(ALICE);

        // Create a new tutorial not in the address book
        Tut nonExistentTutorial = new Tut(TUT_NAME, TUT_01);

        // Expect TutNoFoundException when trying to assign ALICE to a non-existent tutorial
        assertThrows(TutNoFoundException.class, () -> addressBook.assignStudent(ALICE, nonExistentTutorial));
    }

    @Test
    public void assignStudent_studentAlreadyInTutorial_success() {
        addressBook.addStudent(ALICE);
        addressBook.addTutorial(TUT_SAMPLE);
        addressBook.assignStudent(ALICE, TUT_SAMPLE); // First assignment

        // Assign ALICE again to TUT_SAMPLE
        addressBook.assignStudent(ALICE, TUT_SAMPLE);

        // Check if ALICE is still in the tutorial and no duplicates exist
        long count = TUT_SAMPLE.getStudents().stream().filter(student -> student.equals(ALICE)).count();
        assertEquals(1, count, "ALICE should only be assigned once to the tutorial TUT_SAMPLE.");
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
