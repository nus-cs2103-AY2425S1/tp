package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_REGISTER_NUMBER = "41";
    private static final String INVALID_SEX = "H";
    private static final String INVALID_STUDENT_CLASS = "A1";
    private static final String INVALID_ECNAME = "---";
    private static final String INVALID_ECNUMBER = "123";
    private static final String INVALID_EXAM_NAME = "Midterm%";
    private static final String INVALID_EXAM_SCORE = "101";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REGISTER_NUMBER = BENSON.getRegisterNumber().toString();
    private static final String VALID_SEX = BENSON.getSex().toString();
    private static final String VALID_STUDENT_CLASS = BENSON.getStudentClass().toString();
    private static final String VALID_ECNAME = BENSON.getEcName().toString();
    private static final String VALID_ECNUMBER = BENSON.getEcNumber().toString();
    private static final List<JsonAdaptedExam> VALID_EXAMS = BENSON.getExams().stream()
            .map(JsonAdaptedExam::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REGISTER_NUMBER,
                        VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REGISTER_NUMBER,
                        VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_REGISTER_NUMBER,
                        VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRegisterNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = RegisterNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRegisterNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RegisterNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSex_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REGISTER_NUMBER,
                        INVALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS, VALID_TAGS);
        String expectedMessage = Sex.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSex_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, null, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStudentClass_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, INVALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = StudentClass.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStudentClass_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, null, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentClass.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEcName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, INVALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = EcName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEcName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, null, VALID_ECNUMBER, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                EcName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEcNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REGISTER_NUMBER,
                        VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, INVALID_ECNUMBER, VALID_EXAMS, VALID_TAGS);
        String expectedMessage = EcNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEcNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REGISTER_NUMBER, VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, null, VALID_EXAMS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EcNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidExams_throwsIllegalValueException() {
        List<JsonAdaptedExam> invalidExams = new ArrayList<>(VALID_EXAMS);
        invalidExams.add(new JsonAdaptedExam(INVALID_EXAM_NAME, INVALID_EXAM_SCORE));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REGISTER_NUMBER,
                        VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, invalidExams, VALID_TAGS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REGISTER_NUMBER,
                        VALID_SEX, VALID_STUDENT_CLASS, VALID_ECNAME, VALID_ECNUMBER, VALID_EXAMS, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
