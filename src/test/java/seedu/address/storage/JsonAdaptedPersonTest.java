package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.BENSON; // A Student
import static seedu.address.testutil.TypicalContacts.IDA; // A Company

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.company.Industry;
import seedu.address.model.person.student.StudentId;

public class JsonAdaptedPersonTest {

    // Invalid values for fields
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_STUDENT_ID = "S@123";
    private static final String INVALID_INDUSTRY = "@Finance";

    // Valid values for Student and Company
    private static final String VALID_NAME_STUDENT = BENSON.getName().toString();
    private static final String VALID_STUDENT_ID = BENSON.getStudentId().toString();
    private static final String VALID_PHONE_STUDENT = BENSON.getPhone().toString();
    private static final String VALID_EMAIL_STUDENT = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS_STUDENT = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS_STUDENT = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_NAME_COMPANY = IDA.getName().toString();
    private static final String VALID_INDUSTRY = IDA.getIndustry().toString();
    private static final String VALID_PHONE_COMPANY = IDA.getPhone().toString();
    private static final String VALID_EMAIL_COMPANY = IDA.getEmail().toString();
    private static final String VALID_ADDRESS_COMPANY = IDA.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS_COMPANY = IDA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    // Test for valid Student
    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedPerson student = new JsonAdaptedPerson(BENSON); // BENSON is a Student
        assertEquals(BENSON, student.toModelType());
    }

    // Test for valid Company
    @Test
    public void toModelType_validCompanyDetails_returnsCompany() throws Exception {
        JsonAdaptedPerson company = new JsonAdaptedPerson(IDA); // IDA is a Company
        assertEquals(IDA, company.toModelType());
    }

    // Invalid Name (common to both)
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, "Student", VALID_PHONE_STUDENT,
                        VALID_EMAIL_STUDENT, VALID_ADDRESS_STUDENT, VALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Null Name (common to both)
    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, "Student",
                VALID_PHONE_STUDENT, VALID_EMAIL_STUDENT, VALID_ADDRESS_STUDENT, VALID_STUDENT_ID,
                null, VALID_TAGS_STUDENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Invalid Phone (common to both)
    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student", INVALID_PHONE,
                        VALID_EMAIL_STUDENT, VALID_ADDRESS_STUDENT, VALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Null Phone (common to both)
    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student",
                null, VALID_EMAIL_STUDENT, VALID_ADDRESS_STUDENT,
                VALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Invalid Email (common to both)
    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student", VALID_PHONE_STUDENT,
                        INVALID_EMAIL, VALID_ADDRESS_STUDENT, VALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Null Email (common to both)
    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student",
                VALID_PHONE_STUDENT, null, VALID_ADDRESS_STUDENT,
                VALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Invalid Address (common to both)
    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student", VALID_PHONE_STUDENT,
                        VALID_EMAIL_STUDENT, INVALID_ADDRESS, VALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Null Address (common to both)
    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student",
                VALID_PHONE_STUDENT, VALID_EMAIL_STUDENT, null,
                VALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Invalid StudentID (specific to Student)
    @Test
    public void toModelType_invalidStudentID_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student", VALID_PHONE_STUDENT,
                VALID_EMAIL_STUDENT, VALID_ADDRESS_STUDENT, INVALID_STUDENT_ID, null, VALID_TAGS_STUDENT);
        String expectedMessage = StudentId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Null StudentID (specific to Student)
    @Test
    public void toModelType_nullStudentID_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student", VALID_PHONE_STUDENT,
                VALID_EMAIL_STUDENT, VALID_ADDRESS_STUDENT, null, null, VALID_TAGS_STUDENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Invalid Industry (specific to Company)
    @Test
    public void toModelType_invalidIndustry_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_COMPANY, "Company", VALID_PHONE_COMPANY,
                VALID_EMAIL_COMPANY, VALID_ADDRESS_COMPANY, null, INVALID_INDUSTRY, VALID_TAGS_COMPANY);
        String expectedMessage = Industry.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Null Industry (specific to Company)
    @Test
    public void toModelType_nullIndustry_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_COMPANY, "Company", VALID_PHONE_COMPANY,
                VALID_EMAIL_COMPANY, VALID_ADDRESS_COMPANY, null, null, VALID_TAGS_COMPANY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Industry.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // Invalid Tags (common to both)
    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS_STUDENT);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_STUDENT, "Student", VALID_PHONE_STUDENT,
                        VALID_EMAIL_STUDENT, VALID_ADDRESS_STUDENT, VALID_STUDENT_ID, null, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
