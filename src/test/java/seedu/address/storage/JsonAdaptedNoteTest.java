package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;


public class JsonAdaptedNoteTest {
    /**
     * Ensures that a {@code IllegalValueException} is thrown when an
     * {@code JsonAdaptedNote} object is constructed with a null content.
     */
    @Test
    public void toModelType_nullContent_throwsIllegalValueException() {
        JsonAdaptedNote note = new JsonAdaptedNote((String) null);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, "Note content");
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }
}

