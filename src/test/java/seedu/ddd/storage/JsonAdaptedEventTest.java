package seedu.ddd.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalEvents.WEDDING_A;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.EventId;
public class JsonAdaptedEventTest {
    private static final String INVALID_DESCRIPTION = "  ";
    private static final int INVALID_EVENT_ID = -1;

    private static final String VALID_DESCRIPTION = WEDDING_A.getDescription().toString();
    private static final List<JsonAdaptedClient> VALID_CLIENTS = WEDDING_A.getClients().stream()
            .map(JsonAdaptedClient::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedVendor> VALID_VENDORS = WEDDING_A.getVendors().stream()
            .map(JsonAdaptedVendor::new)
            .collect(Collectors.toList());
    private static final int VALID_EVENT_ID = WEDDING_A.getEventId().eventId;

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(WEDDING_A);
        assertEquals(WEDDING_A, event.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(INVALID_DESCRIPTION, VALID_CLIENTS,
                VALID_VENDORS, VALID_EVENT_ID);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, VALID_CLIENTS,
                VALID_VENDORS, VALID_EVENT_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventId_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_CLIENTS,
                VALID_VENDORS, INVALID_EVENT_ID);
        String expectedMessage = EventId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
    @Test
    public void toModelType_emptyClientList_throwsIllegalValueException() {
        List<JsonAdaptedClient> emptyClientList = new ArrayList<>();
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_DESCRIPTION, emptyClientList,
                VALID_VENDORS, VALID_EVENT_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Client.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_emptyVendorList_throwsIllegalValueException() {
        List<JsonAdaptedVendor> emptyVendorList = new ArrayList<>();
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_CLIENTS,
                emptyVendorList, VALID_EVENT_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Vendor.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
