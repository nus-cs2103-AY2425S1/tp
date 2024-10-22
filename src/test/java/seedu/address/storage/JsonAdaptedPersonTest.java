package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.MARADONA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Note;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StarredStatus;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_AGE = "1011";
    private static final String INVALID_SEX = "Fem@le";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_APPOINTMENT = "99/99/9999 9999";
    private static final JsonAdaptedNote INVALID_NOTE_APPOINTMENT =
            new JsonAdaptedNote(null, List.of(INVALID_APPOINTMENT), null);
    private static final JsonAdaptedNote INVALID_NOTE_MEDICATION =
            new JsonAdaptedNote(List.of("INVALID_MEDICATION"), null, null);
    private static final JsonAdaptedNote INVALID_NOTE_REMARK =
            new JsonAdaptedNote(null, null, List.of("INVALID_REMARK"));
    private static final String INVALID_STARRED_STATUS = "starred";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_AGE = BENSON.getAge().toString();
    private static final String VALID_SEX = BENSON.getSex().toString();
    private static final List<JsonAdaptedAppointment> VALID_APPOINTMENT = BENSON.getAppointment().stream()
            .map(JsonAdaptedAppointment::new)
            .toList();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final JsonAdaptedNote VALID_NOTE = new JsonAdaptedNote(BENSON.getNote());
    private static final String VALID_STARRED_STATUS = "false";

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(MARADONA);
        assertEquals(MARADONA, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSex_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_AGE, INVALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = Sex.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSex_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_AGE, null, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAppointments_throwsIllegalValueException() {
        List<JsonAdaptedAppointment> invalidAppointments = new ArrayList<>(VALID_APPOINTMENT);
        invalidAppointments.add(new JsonAdaptedAppointment(INVALID_APPOINTMENT));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_AGE, VALID_SEX, invalidAppointments,
                        VALID_TAGS, VALID_NOTE, VALID_STARRED_STATUS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_AGE, VALID_SEX, VALID_APPOINTMENT, invalidTags, VALID_NOTE, VALID_STARRED_STATUS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedPerson person1 =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS,
                        INVALID_NOTE_APPOINTMENT, VALID_STARRED_STATUS);
        assertThrows(IllegalValueException.class, person1::toModelType);

        JsonAdaptedPerson person2 =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, INVALID_NOTE_MEDICATION, VALID_STARRED_STATUS);
        assertThrows(IllegalValueException.class, person2::toModelType);

        JsonAdaptedPerson person3 =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, INVALID_NOTE_REMARK, VALID_STARRED_STATUS);
        assertThrows(IllegalValueException.class, person3::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, null, VALID_STARRED_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStarredStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, INVALID_STARRED_STATUS);
        String expectedMessage = StarredStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStarredStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_AGE, VALID_SEX, VALID_APPOINTMENT, VALID_TAGS, VALID_NOTE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StarredStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
