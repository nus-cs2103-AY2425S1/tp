package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.Date;

public class JsonAdaptedCancelledLessonTest {

    @Test
    public void toModelType_invalidCancelledLesson_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedCancelledLesson invalidCancelledLesson = new JsonAdaptedCancelledLesson("Test");
        assertThrows(IllegalValueException.class, Date.MESSAGE_CONSTRAINTS, invalidCancelledLesson::toModelType);
    }
}
