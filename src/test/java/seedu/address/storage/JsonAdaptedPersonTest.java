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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_STATUS = "-recovering";
    private static final String INVALID_TAG = "#friend";
    private static final int INVALID_PERSON_ID = -1;

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(new Person(0, BENSON));
        // EP: Ensures object equality with valid inputs
        assertEquals(BENSON, person.toModelType().getPersonDescriptor());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_PERSON_ID, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_STATUS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        // EP: Boundary test (invalid characters)
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_PERSON_ID, null,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        // EP: Null check
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_STATUS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        // EP: Boundary test (phone length)
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        // EP: Null check for phone
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                    VALID_STATUS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        // EP: Missing '@' symbol boundary check
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        // EP: Null check for email
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                    VALID_STATUS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        // EP: Address cannot be blank
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
            null, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        // EP: Null check for address
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_STATUS, invalidTags);
        // EP: Boundary test (tag format)
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    INVALID_STATUS, VALID_TAGS);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        // EP: Boundary test (status format)
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_PERSON_ID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        // EP: Null check for status
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
