package seedu.ddd.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.*;
import static seedu.ddd.testutil.contact.TypicalContacts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;

public class JsonAdaptedVendorTest {

    private static final List<JsonAdaptedTag> VALID_TAGS = List.of(VALID_TAG_1, VALID_TAG_2)
            .stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedEventId> EMPTY_EVENT_IDS = new ArrayList<>();

    @Test
    public void toModelType_validVendorDetails_returnsVendor() throws Exception {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_VENDOR);
        assertEquals(VALID_VENDOR, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            INVALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            null,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            INVALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            null,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            INVALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            null,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            INVALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            null,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidService_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            INVALID_VENDOR_SERVICE,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = Service.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullService_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            null,
            VALID_TAGS,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Service.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            invalidTags,
            Integer.parseInt(VALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        assertThrows(IllegalValueException.class, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedContact vendor = new JsonAdaptedVendor(
            VALID_VENDOR_NAME,
            VALID_VENDOR_PHONE,
            VALID_VENDOR_EMAIL,
            VALID_VENDOR_ADDRESS,
            VALID_VENDOR_SERVICE_1,
            VALID_TAGS,
            Integer.parseInt(INVALID_VENDOR_ID),
            EMPTY_EVENT_IDS
        );
        String expectedMessage = ContactId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

}
