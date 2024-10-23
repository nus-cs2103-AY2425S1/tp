package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;

public class JsonAdaptedEventTest {

    private static final String VALID_DESCRIPTION = "Team meeting";
    private static final String VALID_FROM = "2023-10-01";
    private static final String VALID_TO = "2023-10-02";
    private static final boolean IS_DONE = false;

    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_FROM = "invalid-date";
    private static final String INVALID_TO = "invalid-date";

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(VALID_DESCRIPTION, IS_DONE, VALID_FROM, VALID_TO);
        Event expectedEvent = new Event(VALID_DESCRIPTION, VALID_FROM, VALID_TO, IS_DONE);
        assertEquals(expectedEvent, jsonAdaptedEvent.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(INVALID_DESCRIPTION, IS_DONE, VALID_FROM, VALID_TO);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedEvent::toModelType);
    }

    @Test
    public void toModelType_invalidFromDate_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(VALID_DESCRIPTION, IS_DONE, INVALID_FROM, VALID_TO);
        String expectedMessage = seedu.address.model.task.Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedEvent::toModelType);
    }

    @Test
    public void toModelType_invalidToDate_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(VALID_DESCRIPTION, IS_DONE, VALID_FROM, INVALID_TO);
        String expectedMessage = seedu.address.model.task.Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedEvent::toModelType);
    }

    @Test
    public void toModelType_nullFromDate_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(VALID_DESCRIPTION, IS_DONE, null, VALID_TO);
        String expectedMessage = String.format(JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT, "Event dates");
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedEvent::toModelType);
    }

    @Test
    public void toModelType_nullToDate_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(VALID_DESCRIPTION, IS_DONE, VALID_FROM, null);
        String expectedMessage = String.format(JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT, "Event dates");
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedEvent::toModelType);
    }

    @Test
    public void toModelType_validEventWithDone_returnsCorrectly() throws Exception {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(VALID_DESCRIPTION, true, VALID_FROM, VALID_TO);
        Event expectedEvent = new Event(VALID_DESCRIPTION, VALID_FROM, VALID_TO, true);
        assertEquals(expectedEvent, jsonAdaptedEvent.toModelType());
    }
}
