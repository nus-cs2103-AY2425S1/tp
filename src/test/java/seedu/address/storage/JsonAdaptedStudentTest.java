package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Subject;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SCHEDULE = "Anyday 7pm-9pm";
    private static final String INVALID_SUBJECT = "PHYSICAL EDUCATION";
    private static final String INVALID_RATE = "123.23/h";
    private static final String INVALID_PAID_AMOUNT = " ";
    private static final String INVALID_OWED_AMOUNT = " ";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SUBJECT = BENSON.getSubject().toString();
    private static final String VALID_SCHEDULE = BENSON.getSchedule().value;
    private static final String VALID_RATE = BENSON.getRate().toString();
    private static final String VALID_PAID_AMOUNT = BENSON.getPaidAmount().toString();
    private static final String VALID_OWED_AMOUNT = BENSON.getOwedAmount().toString();

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        // EP: valid name with valid parameters
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        // EP: invalid name with non-alphabetical characters
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(
                        INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE,
                        VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
                );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        // EP: invalid name with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE, VALID_SUBJECT, VALID_RATE,
                VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        // EP: invalid phone number with non-numerical characters
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(
                        VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE,
                        VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
                );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        // EP: invalid phone number with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE, VALID_SUBJECT,
                VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        // EP: invalid email without @
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(
                        VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE,
                        VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
                );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        // EP: invalid email with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_SCHEDULE, VALID_SUBJECT,
                VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        // EP: invalid address with empty space
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_SCHEDULE,
                        VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
                );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        // EP: invalid address with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_SCHEDULE, VALID_SUBJECT,
                VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidSchedule_throwsIllegalValueException() {
        // EP: invalid schedule that does not correspond to enum values
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_SCHEDULE,
                        VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
                );
        String expectedMessage = Schedule.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullSchedule_throwsIllegalValueException() {
        // EP: invalid schedule with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        // EP: invalid subject that does not correspond to enum values
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE,
                        INVALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
                );
        String expectedMessage = Subject.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        // EP: invalid subject with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE,
                null, VALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidRate_throwsIllegalValueException() {
        // EP: invalid rate with alphabets
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE, VALID_SUBJECT, INVALID_RATE, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT
        );
        String expectedMessage = Rate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullRate_throwsIllegalValueException() {
        // EP: invalid rate with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE, VALID_SUBJECT, null, VALID_PAID_AMOUNT, VALID_OWED_AMOUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidOwedAmount_throwsIllegalValueException() {
        // EP: invalid OwedAmount with empty space
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE, VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, INVALID_OWED_AMOUNT);
        String expectedMessage = OwedAmount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullOwedAmount_throwsIllegalValueException() {
        // EP: invalid OwedAmount with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE, VALID_SUBJECT, VALID_RATE, VALID_PAID_AMOUNT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OwedAmount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPaidAmount_throwsIllegalValueException() {
        // EP: invalid PaidAmount with empty space
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE, VALID_SUBJECT, VALID_RATE, INVALID_PAID_AMOUNT, VALID_OWED_AMOUNT);
        String expectedMessage = PaidAmount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPaidAmount_throwsIllegalValueException() {
        // EP: invalid PaidAmount with null
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE, VALID_SUBJECT, VALID_RATE, null, VALID_OWED_AMOUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PaidAmount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
}

