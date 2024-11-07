package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.AttendanceCount;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Student;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ROLE = "fakerole";
    private static final String INVALID_ATTENDANCE = "-1";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_ATTENDANCE = BENSON.getAttendanceCount().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_studentRole_returnsStudent() throws IllegalValueException {
        JsonAdaptedPerson student = new JsonAdaptedPerson(ALICE);
        assertTrue(student.toModelType() instanceof Student);
    }

    @Test
    public void toModelType_parentRole_returnsPerson() throws IllegalValueException {
        JsonAdaptedPerson student = new JsonAdaptedPerson(DANIEL);
        assertTrue(student.toModelType() instanceof Person);
        assertFalse(student.toModelType() instanceof Student);
    }

    @Test
    public void toModelTypePerson_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(DANIEL);
        assertTrue(person.toModelTypePerson() instanceof Person);
        assertEquals(DANIEL, person.toModelTypePerson());
    }

    @Test
    public void toModelTypePerson_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_invalidRole_throwsIlegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, INVALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_nullRole_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                null, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypePerson);
    }

    @Test
    public void toModelTypePerson_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, person::toModelTypePerson);
    }


    //toModelTypeStudent tests
    @Test
    public void toModelTypeStudent_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedPerson student = new JsonAdaptedPerson(BENSON);
        assertTrue(student.toModelTypeStudent() instanceof Student);
        assertEquals(BENSON, student.toModelTypeStudent());
    }

    @Test
    public void toModelTypeStudent_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson student =
                new JsonAdaptedPerson(INVALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(null, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_invalidRole_throwsIlegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(VALID_NAME, INVALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_nullRole_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson student =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypePerson);
    }

    @Test
    public void toModelTypeStudent_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson student =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypStudent_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson student =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        VALID_ATTENDANCE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                null, VALID_TAGS, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson student =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypStudent_invalidAttendanceCount_throwsIllegalValueException() {
        JsonAdaptedPerson student =
                new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        INVALID_ATTENDANCE);
        String expectedMessage = AttendanceCount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

    @Test
    public void toModelTypeStudent_nullAttendanceCount_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AttendanceCount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelTypeStudent);
    }

}
