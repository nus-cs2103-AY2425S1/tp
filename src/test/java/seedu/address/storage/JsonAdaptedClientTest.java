package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CHARLIE;
import static seedu.address.testutil.TypicalPersons.DENVER;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;

public class JsonAdaptedClientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_NAME_BENSON = BENSON.getName().toString();
    private static final String VALID_PHONE_BENSON = BENSON.getPhone().toString();
    private static final String VALID_EMAIL_BENSON = BENSON.getEmail().toString();
    private static final String VALID_NAME_CHARLIE = CHARLIE.getName().toString();
    private static final String VALID_PHONE_CHARLIE = CHARLIE.getPhone().toString();
    private static final String VALID_EMAIL_CHARLIE = CHARLIE.getEmail().toString();
    private static final String VALID_NAME_DENVER = DENVER.getName().toString();
    private static final String VALID_PHONE_DENVER = DENVER.getPhone().toString();
    private static final String VALID_EMAIL_DENVER = DENVER.getEmail().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS_BENSON = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS_CHARLIE = CHARLIE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS_DENVER = DENVER.getTags().stream()
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
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE_BENSON, VALID_EMAIL_BENSON, VALID_TAGS_BENSON);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE_BENSON, VALID_EMAIL_BENSON, VALID_TAGS_BENSON);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_BENSON, INVALID_PHONE, VALID_EMAIL_BENSON, VALID_TAGS_BENSON);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_success() throws IllegalValueException {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_DENVER, null, VALID_EMAIL_DENVER, VALID_TAGS_DENVER);

        assertEquals(DENVER, person.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_BENSON, VALID_PHONE_BENSON, INVALID_EMAIL, VALID_TAGS_BENSON);

        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_success() throws IllegalValueException {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_CHARLIE, VALID_PHONE_CHARLIE, null, VALID_TAGS_CHARLIE);

        assertEquals(CHARLIE, person.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS_BENSON);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME_BENSON, VALID_PHONE_BENSON, VALID_EMAIL_BENSON, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
