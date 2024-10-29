package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.model.person.Student.STUDENT_TYPE;
import static seedu.address.model.person.Teacher.TEACHER_TYPE;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedPerson.createJsonAdaptedPerson;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.STUDENT_BENSON;
import static seedu.address.testutil.TypicalPersons.TEACHER_DANIEL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonTest;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.InvalidPersonTypeException;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GENDER = "m";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final Integer INVALID_DAYS_ATTENDED = -1;

    private static final String VALID_NAME = STUDENT_BENSON.getName().toString();
    private static final String VALID_GENDER = STUDENT_BENSON.getGender().toString();
    private static final String VALID_PHONE = STUDENT_BENSON.getPhone().toString();
    private static final String VALID_EMAIL = STUDENT_BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = STUDENT_BENSON.getAddress().toString();
    private static final Integer VALID_DAYS_ATTENDED = 1;
    private static final List<JsonAdaptedTag> VALID_TAGS = STUDENT_BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedSubject> VALID_SUBJECTS = STUDENT_BENSON.getSubjects().stream()
            .map(JsonAdaptedSubject::new)
            .collect(Collectors.toList());
    private static final List<String> VALID_CLASSES = STUDENT_BENSON.getClasses().stream()
            .map(String::toString)
            .collect(Collectors.toList());


    private static final PersonTest.PersonStub personStubAmy = new PersonTest.PersonStub(new Name(VALID_NAME_AMY),
        new Gender(VALID_GENDER_AMY), new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY),
        new Address(VALID_ADDRESS_AMY), new HashSet<>(), new HashSet<>(), new HashSet<>());

    @Test
    public void toModelType_validPersonDetails_returnsTeacher() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(TEACHER_DANIEL);
        assertEquals(TEACHER_DANIEL, person.toModelType());
    }

    @Test
    public void toModelType_validPersonDetails_returnsStudent() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(STUDENT_BENSON);
        assertEquals(STUDENT_BENSON, person.toModelType());
    }

    @Test
    public void createJsonAdaptedPerson_invalidType_throwsInvalidPersonTypeException() {
        assertThrows(InvalidPersonTypeException.class, () -> createJsonAdaptedPerson(personStubAmy));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(STUDENT_TYPE, INVALID_NAME, VALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_TAGS, VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(STUDENT_TYPE, null, VALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, INVALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, VALID_GENDER, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_TAGS, VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, VALID_GENDER, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(STUDENT_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS,
                VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_TAGS, VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(STUDENT_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS,
                VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    invalidTags, VALID_SUBJECTS, VALID_CLASSES, VALID_DAYS_ATTENDED);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidSubjects_throwsIllegalValueException() {
        List<JsonAdaptedSubject> invalidSubjects = new ArrayList<>(VALID_SUBJECTS);
        invalidSubjects.add(new JsonAdaptedSubject(INVALID_TAG));
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, invalidSubjects, VALID_CLASSES, VALID_DAYS_ATTENDED);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullClasses_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(STUDENT_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_SUBJECTS, null, VALID_DAYS_ATTENDED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Class");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDaysAttended_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(TEACHER_TYPE, VALID_NAME, VALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_TAGS, VALID_SUBJECTS, VALID_CLASSES, INVALID_DAYS_ATTENDED);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
