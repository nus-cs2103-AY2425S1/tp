package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalConsultations.CONSULT_1;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.student.Student;
import seedu.address.testutil.ConsultationBuilder;
import seedu.address.testutil.StudentBuilder;

public class JsonAdaptedConsultationTest {
    private static final AddressBook testAddressBook = getTypicalAddressBook();
    private static final String VALID_DATE = "2024-10-22";
    private static final String VALID_TIME = "14:00";
    private static final List<JsonAdaptedStudent> VALID_STUDENTS = List.of();
    private static final String INVALID_DATE = "2024-13-32";
    private static final String INVALID_TIME = "25:66";

    @Test
    public void toModelType_validConsultDetails_returnsConsult() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        JsonAdaptedConsultation consult = new JsonAdaptedConsultation(CONSULT_1);
        assertEquals(CONSULT_1, consult.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedConsultation consult = new JsonAdaptedConsultation(
                INVALID_DATE, VALID_TIME, VALID_STUDENTS
        );
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> consult.toModelType(testAddressBook));
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedConsultation consult = new JsonAdaptedConsultation(
                null, VALID_TIME, VALID_STUDENTS
        );
        String expectedMessage = String.format(JsonAdaptedConsultation.MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> consult.toModelType(testAddressBook));
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedConsultation consult = new JsonAdaptedConsultation(
                VALID_DATE, INVALID_TIME, VALID_STUDENTS
        );
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> consult.toModelType(testAddressBook));
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedConsultation consult = new JsonAdaptedConsultation(
                VALID_DATE, null, VALID_STUDENTS
        );
        String expectedMessage = String.format(JsonAdaptedConsultation.MISSING_FIELD_MESSAGE_FORMAT,
                Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> consult.toModelType(testAddressBook));
    }

    @Test
    public void toModelType_nonExistentStudent_throwsIllegalValueException() {
        AddressBook emptyAddressBook = new AddressBook();
        Student student = new StudentBuilder().build();
        Consultation consult = new ConsultationBuilder().withStudent(student).build();
        JsonAdaptedConsultation jsonConsult = new JsonAdaptedConsultation(consult);
        String expectedMessage = String.format(JsonAdaptedConsultation.STUDENT_NOT_FOUND_MESSAGE,
                student.getName().fullName);
        assertThrows(IllegalValueException.class, expectedMessage, () -> jsonConsult.toModelType(emptyAddressBook));
    }

}
