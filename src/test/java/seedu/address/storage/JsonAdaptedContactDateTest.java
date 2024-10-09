package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES;
import static seedu.address.storage.JsonAdaptedContactDate.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contactdate.ContactDate;
import seedu.address.testutil.ContactDateBuilder;

public class JsonAdaptedContactDateTest {
    private static final String INVALID_DATE = "2024-13-01";
    private static final String VALID_DATE = "2024-01-01";

    @Test
    public void toModelType_validContactDateDetails_returnsContactDate() throws Exception {
        ContactDate contactDate = new ContactDateBuilder().build();
        JsonAdaptedContactDate jsonContactDate = new JsonAdaptedContactDate(contactDate);
        assertEquals(contactDate, jsonContactDate.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedContactDate jsonContactDate = new JsonAdaptedContactDate(INVALID_DATE, VALID_NOTES);
        String expectedMessage = ContactDate.MESSAGE_CONSTRAINTS;
        // assertThrows(IllegalValueException.class, expectedMessage, jsonContactDate::toModelType);
        assertThrows(IllegalValueException.class, expectedMessage, jsonContactDate::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedContactDate jsonContactDate = new JsonAdaptedContactDate(null, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date");
        assertThrows(IllegalValueException.class, expectedMessage, jsonContactDate::toModelType);
    }

    @Test
    public void toModelType_nullNotes_throwsIllegalValueException() {
        JsonAdaptedContactDate jsonContactDate = new JsonAdaptedContactDate(VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "notes");
        assertThrows(IllegalValueException.class, expectedMessage, jsonContactDate::toModelType);
    }
}
