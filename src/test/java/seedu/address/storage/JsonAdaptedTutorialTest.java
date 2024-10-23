package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTutorial.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.MATH;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedTutorialTest {
    @Test
    public void toModelType_validTutorialDetails_returnsTutorial() throws Exception {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(MATH);
        assertEquals(MATH, tutorial.toModelType());
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

}
