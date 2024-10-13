package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES;
import static seedu.address.storage.JsonAdaptedContactRecord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.testutil.ContactRecordBuilder;

public class JsonAdaptedContactRecordTest {
    private static final String INVALID_DATE = "2024-13-01";
    private static final String VALID_DATE = "2024-01-01";

    @Test
    public void toModelType_validContactRecordDetails_returnsContactRecord() throws Exception {
        ContactRecord contactRecord = new ContactRecordBuilder().build();
        JsonAdaptedContactRecord jsonContactRecord = new JsonAdaptedContactRecord(contactRecord);
        assertEquals(contactRecord, jsonContactRecord.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedContactRecord jsonContactRecord = new JsonAdaptedContactRecord(INVALID_DATE, VALID_NOTES);
        String expectedMessage = ContactRecord.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonContactRecord::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedContactRecord jsonContactRecord = new JsonAdaptedContactRecord(null, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date");
        assertThrows(IllegalValueException.class, expectedMessage, jsonContactRecord::toModelType);
    }

    @Test
    public void toModelType_nullNotes_throwsIllegalValueException() {
        JsonAdaptedContactRecord jsonContactRecord = new JsonAdaptedContactRecord(VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "notes");
        assertThrows(IllegalValueException.class, expectedMessage, jsonContactRecord::toModelType);
    }
}
