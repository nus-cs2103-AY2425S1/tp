package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIALID_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        Student editedAlice = new StudentBuilder(ALICE)
                .withTutorialId(VALID_TUTORIALID_BOB)
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
    public void hasStudent_studentWithSameIdentityFieldsInAddressBook_returnsFalse() {
        addressBook.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(BOB)
                .withStudentId(VALID_STUDENTID_BOB).withTutorialId(VALID_TUTORIALID_BOB)
                .build();
        assertFalse(addressBook.hasStudent(editedAlice));
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

    /*
    @Test
    public void getTutorialList_add() {
        List<Tutorial> expectedTutorial = new ArrayList<>();
        expectedTutorial.add(TUTORIAL_SAMPLE);
        addressBook.addTutorial(TUTORIAL_SAMPLE);
        List<Tutorial> actualTutorial = addressBook.getTutorials();
        assertTrue(actualTutorial.equals(expectedTutorial));
    }

    @Test
    public void getAddressBook_addTutorial() {
        addressBook.resetData(getTypicalAddressBook());
        AddressBook addressBookSample = getTypicalAddressBook();
        addressBookSample.addTutorial(TUTORIAL_SAMPLE);
        addressBook.addTutorial(TUTORIAL_SAMPLE);
        assertTrue(addressBook.equals(addressBookSample));
    }
    @Test
    public void getTutorialList_addStudent() {
<<<<<<< HEAD
        List<Tutorial> expectedTutorial = new ArrayList<>();
        Tutorial tutorialSample2 = new Tutorial(TUT_NAME, TUTORIAL_CLASS);
        TUTORIAL_SAMPLE.add(ALICE);
        tutorialSample2.add(ALICE);
        expectedTutorial.add(TUTORIAL_SAMPLE);
        addressBook.addTutorial(tutorialSample2);
        List<Tutorial> actualTutorial = addressBook.getTutorials();
=======
        List<Tut> expectedTutorial = new ArrayList<>();
        Tut tutSample2 = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        TUT_SAMPLE.add(ALICE);
        tutSample2.add(ALICE);
        expectedTutorial.add(TUT_SAMPLE);
        addressBook.addTutorial(tutSample2);
        List<Tut> actualTutorial = addressBook.getTutorials();
>>>>>>> 04c7c729727982716375d9db0e0bbe8847aebe3f
        assertTrue(actualTutorial.equals(expectedTutorial));
    }
    @Test
    public void getAddressBook_addStudent() {
        addressBook.resetData(getTypicalAddressBook());
        AddressBook addressBookSample = getTypicalAddressBook();
<<<<<<< HEAD
        List<Tutorial> expectedTutorial = new ArrayList<>();
        Tutorial tutorialSample2 = new Tutorial(TUT_NAME, TUTORIAL_CLASS);
        TUTORIAL_SAMPLE.add(ALICE);
        tutorialSample2.add(ALICE);
        expectedTutorial.add(TUTORIAL_SAMPLE);
        addressBook.addTutorial(tutorialSample2);
        addressBookSample.addTutorial(tutorialSample2);
=======
        List<Tut> expectedTutorial = new ArrayList<>();
        Tut tutSample2 = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        TUT_SAMPLE.add(ALICE);
        tutSample2.add(ALICE);
        expectedTutorial.add(TUT_SAMPLE);
        addressBook.addTutorial(tutSample2);
        addressBookSample.addTutorial(tutSample2);
>>>>>>> 04c7c729727982716375d9db0e0bbe8847aebe3f
        assertTrue(addressBook.equals(addressBookSample));
    }

    @Test
    public void assignStudent_validStudentAndValidTutorial_success() {
        addressBook.addStudent(ALICE);
        addressBook.addTutorial(TUTORIAL_SAMPLE);
        addressBook.assignStudent(ALICE, TUTORIAL_SAMPLE);

        // Check if ALICE has been successfully assigned to TUT_SAMPLE
        assertTrue(TUTORIAL_SAMPLE.getStudents().contains(ALICE),
        "ALICE should be assigned to the tutorial TUT_SAMPLE.");
    }

    @Test
    public void assignStudent_studentNotInAddressBook_throwsStudentNotFoundException() {
        // Add TUT_SAMPLE to AddressBook but not ALICE
        addressBook.addTutorial(TUTORIAL_SAMPLE);

        // Expect StudentNotFoundException when trying to assign a non-existent student
        assertThrows(StudentNotFoundException.class, () -> addressBook.assignStudent(ALICE, TUTORIAL_SAMPLE));
    }

    @Test
    public void assignStudent_tutorialNotInAddressBook_throwsTutNoFoundException() {
        // Add ALICE to AddressBook but not TUT_SAMPLE
        addressBook.addStudent(ALICE);

        // Create a new tutorial not in the address book
<<<<<<< HEAD
        Tutorial nonExistentTutorial = new Tutorial(TUT_NAME, TUTORIAL_CLASS);
=======
        Tut nonExistentTutorial = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
>>>>>>> 04c7c729727982716375d9db0e0bbe8847aebe3f

        // Expect TutNoFoundException when trying to assign ALICE to a non-existent tutorial
        assertThrows(TutNoFoundException.class, () -> addressBook.assignStudent(ALICE, nonExistentTutorial));
    }

    @Test
    public void assignStudent_studentAlreadyInTutorial_success() {
        addressBook.addStudent(ALICE);
        addressBook.addTutorial(TUTORIAL_SAMPLE);
        addressBook.assignStudent(ALICE, TUTORIAL_SAMPLE); // First assignment

        // Assign ALICE again to TUT_SAMPLE
        addressBook.assignStudent(ALICE, TUTORIAL_SAMPLE);

        // Check if ALICE is still in the tutorial and no duplicates exist
        long count = TUTORIAL_SAMPLE.getStudents().stream().filter(student -> student.equals(ALICE)).count();
        assertEquals(1, count, "ALICE should only be assigned once to the tutorial TUT_SAMPLE.");
    }
    */

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
