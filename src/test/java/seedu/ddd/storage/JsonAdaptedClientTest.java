package seedu.ddd.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalContacts.ALICE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.client.Date;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;

public class JsonAdaptedClientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE = "12-34-5678";
    private static final String INVALID_TAG = "#friend";
    private static final int INVALID_ID = -1;

    private static final String VALID_NAME = ALICE.getName().toString();
    private static final String VALID_PHONE = ALICE.getPhone().toString();
    private static final String VALID_EMAIL = ALICE.getEmail().toString();
    private static final String VALID_ADDRESS = ALICE.getAddress().toString();
    private static final String VALID_DATE = ALICE.getDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = ALICE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final int VALID_ID = ALICE.getId().contactId;
    private static final List<JsonAdaptedEventId> EMPTY_EVENT_IDS = new ArrayList<>();

    @Test
    public void toModelType_validClientDetails_returnsClient() throws Exception {
        JsonAdaptedClient client = new JsonAdaptedClient(ALICE);
        assertEquals(ALICE, client.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_DATE, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TAGS, VALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE, invalidTags, VALID_ID, EMPTY_EVENT_IDS);
        assertThrows(IllegalValueException.class, client::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedContact client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE, VALID_TAGS, INVALID_ID, EMPTY_EVENT_IDS);
        String expectedMessage = ContactId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }
}
