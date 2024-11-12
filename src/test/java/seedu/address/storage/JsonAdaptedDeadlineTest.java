package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;

public class JsonAdaptedDeadlineTest {

    private static final String VALID_DESCRIPTION = "Submit assignment";
    private static final String VALID_BY = "2023-12-31";
    private static final boolean IS_DONE = false;

    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_BY = "32-12-2023";

    @Test
    public void toModelType_validDeadlineDetails_returnsDeadline() throws Exception {
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(VALID_DESCRIPTION, IS_DONE, VALID_BY);
        Deadline expectedDeadline = new Deadline(VALID_DESCRIPTION, VALID_BY, IS_DONE);
        assertEquals(expectedDeadline, jsonAdaptedDeadline.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(INVALID_DESCRIPTION, IS_DONE, VALID_BY);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedDeadline::toModelType);
    }

    @Test
    public void toModelType_invalidByDate_throwsIllegalValueException() {
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(VALID_DESCRIPTION, IS_DONE, INVALID_BY);
        String expectedMessage = seedu.address.model.task.Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedDeadline::toModelType);
    }

    @Test
    public void toModelType_nullByDate_throwsIllegalValueException() {
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(VALID_DESCRIPTION, IS_DONE, null);
        String expectedMessage = String.format(JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT, "Deadline");
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedDeadline::toModelType);
    }

    @Test
    public void toModelType_validDeadlineWithDone_returnsCorrectly() throws Exception {
        JsonAdaptedDeadline jsonAdaptedDeadline = new JsonAdaptedDeadline(VALID_DESCRIPTION, true, VALID_BY);
        Deadline expectedDeadline = new Deadline(VALID_DESCRIPTION, VALID_BY, true);
        assertEquals(expectedDeadline, jsonAdaptedDeadline.toModelType());
    }
}
