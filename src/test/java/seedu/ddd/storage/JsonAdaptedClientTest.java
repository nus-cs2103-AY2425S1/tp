package seedu.ddd.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_TAG;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;

public class JsonAdaptedClientTest {

    private static final List<JsonAdaptedTag> VALID_TAGS = List.of(VALID_TAG_1, VALID_TAG_2)
            .stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedId> EMPTY_EVENT_IDS = new ArrayList<>();

    @Test
    public void toModelType_validClientDetails_returnsClient() throws Exception {
        JsonAdaptedClient client = new JsonAdaptedClient(VALID_CLIENT);
        assertEquals(VALID_CLIENT, client.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            INVALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            null,
            VALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            INVALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            null,
            VALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            INVALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            null,
            VALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            INVALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            null,
            VALID_TAGS,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS,
            invalidTags,
            Integer.parseInt(VALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        assertThrows(IllegalValueException.class, client::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(
            VALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS,
            VALID_TAGS,
            Integer.parseInt(INVALID_CLIENT_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }
}
