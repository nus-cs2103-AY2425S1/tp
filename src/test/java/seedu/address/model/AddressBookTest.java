package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS2103T;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalConsultations.CONSULT_1;
import static seedu.address.testutil.TypicalConsultations.CONSULT_2;
import static seedu.address.testutil.TypicalStudents.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.exceptions.ConsultationNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.testutil.ConsultationBuilder;
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
        Student editedAlice = new StudentBuilder(ALICE).withCourses(VALID_COURSE_CS2103T)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newStudents, List.of());

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
        Student editedAlice = new StudentBuilder(ALICE).withCourses(VALID_COURSE_CS2103T)
                .build();
        assertTrue(addressBook.hasStudent(editedAlice));
    }

    @Test
    public void hasConsult_nullConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasConsult(null));
    }

    @Test
    public void hasConsult_consultNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasConsult(new ConsultationBuilder().build()));
    }

    @Test
    public void hasConsult_consultInAddressBook_returnsTrue() {
        Consultation consult = new ConsultationBuilder().build();
        addressBook.addConsult(consult);
        assertTrue(addressBook.hasConsult(consult));
    }

    @Test
    public void hasConsult_consultWithSameDetailsInAddressBook_returnsTrue() {
        Consultation consult = new ConsultationBuilder().build();
        Consultation copy = new ConsultationBuilder(consult).build();
        addressBook.addConsult(consult);
        assertTrue(addressBook.hasConsult(copy));
    }

    @Test
    public void setConsult_allValidArguments_success() {
        addressBook.addConsult(CONSULT_1);
        assertTrue(addressBook.hasConsult(CONSULT_1));
        assertFalse(addressBook.hasConsult(CONSULT_2));
        addressBook.setConsult(CONSULT_1, CONSULT_2);
        assertFalse(addressBook.hasConsult(CONSULT_1));
        assertTrue(addressBook.hasConsult(CONSULT_2));
    }

    @Test
    public void setConsult_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setConsult(null, null));
        assertThrows(NullPointerException.class, () -> addressBook.setConsult(null, CONSULT_1));
        assertThrows(NullPointerException.class, () -> addressBook.setConsult(CONSULT_1, null));
    }

    @Test
    public void setConsult_consultNotFound_throwsConsultationNotFoundException() {
        // Ensure consult does not exist in the address book
        assertFalse(addressBook.hasConsult(CONSULT_1));
        assertThrows(ConsultationNotFoundException.class, () -> addressBook.setConsult(CONSULT_1, CONSULT_2));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{students=" + addressBook.getStudentList()
                + ", consults=" + addressBook.getConsultList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose students list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Consultation> consults = FXCollections.observableArrayList();

        AddressBookStub(Collection<Student> students, Collection<Consultation> consults) {
            this.students.setAll(students);
            this.consults.setAll(consults);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Consultation> getConsultList() {
            return consults;
        }
    }

}
