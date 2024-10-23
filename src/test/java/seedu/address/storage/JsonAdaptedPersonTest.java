package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.EMPTY_FIELD_FORMAT;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "65+1234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_NOTES = BENSON.getNotes().toString();
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
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
            VALID_ADDRESS, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                    VALID_ADDRESS, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null,
            VALID_ADDRESS, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    INVALID_ADDRESS, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
            null, EMPTY_FIELD_FORMAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, EMPTY_FIELD_FORMAT, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_emptyEmail() throws Exception {
        List<JsonAdaptedTag> validTags = new ArrayList<>();
        validTags.add(new JsonAdaptedTag("friend"));
        Person testPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
            .withEmptyEmail().withAddress(VALID_ADDRESS).withTags("friend").build();

        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME, VALID_PHONE, EMPTY_FIELD_FORMAT, VALID_ADDRESS, EMPTY_FIELD_FORMAT, validTags);
        assertEquals(testPerson, person.toModelType());
    }

    @Test
    public void toModelType_emptyAddress() throws Exception {
        List<JsonAdaptedTag> validTags = new ArrayList<>();
        validTags.add(new JsonAdaptedTag("friend"));
        Person testPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
            .withEmail(VALID_EMAIL).withEmptyAddress().withTags("friend").build();

        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME, VALID_PHONE, VALID_EMAIL, EMPTY_FIELD_FORMAT, EMPTY_FIELD_FORMAT, validTags);
        assertEquals(testPerson, person.toModelType());
    }

    @Test
    public void toModelType_emptyEmail_emptyAddress() throws Exception {
        List<JsonAdaptedTag> validTags = new ArrayList<>();
        validTags.add(new JsonAdaptedTag("friend"));
        Person testPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
            .withEmptyEmail().withEmptyAddress().withTags("friend").build();

        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME, VALID_PHONE, EMPTY_FIELD_FORMAT, EMPTY_FIELD_FORMAT, EMPTY_FIELD_FORMAT, validTags);
        assertEquals(testPerson, person.toModelType());
    }

    @Test
    public void toModelType_emptyFields_returnsPerson() throws Exception {
        Person testPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
            .withEmptyEmail().withEmptyAddress().withTags("friend").build();
        JsonAdaptedPerson person = new JsonAdaptedPerson(testPerson);
        assertEquals(testPerson, person.toModelType());
    }
    @Test
    public void toModelType_emptyNotes() throws Exception {
        List<JsonAdaptedTag> validTags = new ArrayList<>();
        validTags.add(new JsonAdaptedTag("friend"));
        Person testPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
            .withEmail(VALID_EMAIL).withAddress(VALID_ADDRESS).withEmptyNotes().withTags("friend").build();

        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, EMPTY_FIELD_FORMAT, validTags);
        assertEquals(testPerson, person.toModelType());
    }
}
